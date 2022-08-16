# How to make Certificates

## Make keystore

### self signed .jks

```
keytool -genkeypair -keyalg RSA -keysize 2048 -alias ilert -dname "CN=Development,OU=CryptoCode,O=Centurion,C=ZA" -ext "SAN:c=DNS:192.168.70.198,IP:10.0.2.2" -validity 3650 -keystore ilert.jks -storepass tomcat -keypass tomcat -deststoretype pkcs12
```

### convert .jks to PKCS12

```
keytool -importkeystore -srckeystore ilert.jks -destkeystore ilert.p12 -srcstoretype JKS -deststoretype PKCS12 -deststorepass tomcat
```

### convert .jks to PEM

```
openssl pkcs12 -in ilert.p12 -out ilert.pem
```

or

```
keytool -exportcert -alias ilert -keypass tomcat -keystore ilert.jks -storepass tomcat -rfc -file ilert.pem
```

### NOTE:

We need to have this .pem file to allow Android Client to talk to the server using a self-signed SSL certificate

# Android Config

Make sure the file `ilert.pem` is in this location app/src/main/res/raw/ilert.pem

### app/src/main/res/xml/network_security_config.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
	<domain-config cleartextTrafficPermitted="true">
		<domain includeSubdomains="true">192.168.70.0</domain> <!-- https://server.tld will work -->
		<trust-anchors>
			<certificates src="@raw/ilert"/>
		</trust-anchors>
	</domain-config>
</network-security-config>
```

### app/src/main/AndroidManifest.xml

```
<application
    android:networkSecurityConfig="@xml/network_security_config"
    ...
</application>
```

# Ktor Config

### src/main/resources/application.conf

```
ktor {
	development = true
    deployment {
        sslPort = 8443
        port = ${?PORT}
        watch = [ classes ]
    }
    application {
        modules = [ za.co.zone.ApplicationKt.module ]
    }
    security {
        ssl {
            keyStore = src/main/resources/ilert.jks
            keyAlias = ilert
            keyStorePassword = "tomcat"
            privateKeyPassword = "tomcat"
        }
    }
}
```

### src/main/kotlin/{package}/plugins/HttpsRedirect.kt

```
fun Application.httpsRedirect() {
	install(HttpsRedirect) {
		sslPort = 8443
		permanentRedirect = true
	}
}
```

# Spring-boot Config

## Generating a Keystore

### Now we’ll create a set of cryptographic keys and store it in a keystore.

1. We can use the following command to generate our PKCS12 keystore format:

```
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore tomcat.p12 -validity 3650
```

3. We can store as many numbers of key-pair in the same keystore each identified by a unique alias.

For generating our keystore in a JKS format, we can use the following command:

```
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -keystore tomcat.jks -validity 3650
```

5. It is recommended to use the PKCS12 format which is an industry standard format. So in case we already have a JKS
   keystore, we can convert it to PKCS12 format using the following command:

```
keytool -importkeystore -srckeystore tomcat.jks -destkeystore tomcat.p12 -deststoretype pkcs12
```

We’ll have to provide the source keystore password and also set a new keystore password. The alias and keystore password
will be needed later.

```
# The format used for the keystore. It could be set to JKS in case it is a JKS file
server.ssl.key-store-type=PKCS12
# The path to the keystore containing the certificate
server.ssl.key-store=classpath:keystore/tomcat.p12
# The password used to generate the certificate
server.ssl.key-store-password=password
# The alias mapped to the certificate
server.ssl.key-alias=tomcat
# Since we are using Spring Security enabled application, let’s configure it to accept only HTTPS requests
security.require-ssl=true
```

## Now we need to prepare an SSLContext with the trust store and create a customized RestTemplate

```
RestTemplate restTemplate() throws Exception {
    SSLContext sslContext = new SSLContextBuilder()
      .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
      .build();
    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
    HttpClient httpClient = HttpClients.custom()
      .setSSLSocketFactory(socketFactory)
      .build();
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    return new RestTemplate(factory);
}
```

## For the sake of the demo, let’s make sure Spring Security allows any incoming requests

```
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/**")
      .permitAll();
}
```

## Finally, we can make a call to the HTTPS endpoint

```
@Test
public void whenGETanHTTPSResource_thenCorrectResponse() throws Exception {
    ResponseEntity<String> response = restTemplate().getForEntity(WELCOME_URL, String.class, Collections.emptyMap());

    assertEquals("<h1>Welcome to Secured Site</h1>", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
}
```