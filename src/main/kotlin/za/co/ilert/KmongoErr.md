## I seem to have a compounded error
The first Error is **I do not know How to do the sum aggregation with my data classes**, I guess the serialization 
problem will go away once I have fixed it.
### I have a Data Class(es)
```kotlin
data class MyClass(
	val items: List<Item>,
	val timestamp: Long = Instant.now().atOffset(ZoneOffset.UTC).toEpochSecond(),
	@BsonId
	val id: String = ObjectId().toString()
)
```
```kotlin
data class Item(
	val name: String,
	val price: Double,
	val qty: Double
)
```
### I need to sum the price
`MyClass::id = id`
### My Attempt
```kotlin
	override suspend fun sumPrice(id: String): Double {
        data class Result(val sumPrice: Double)
		return collection.aggregate<MyClass>(
			match(MyClass::id eq id),
			group(Result::sumPrice sum listOf(Item::price))
		).first()?.sumPrice ?: 0.0
	}
```

This give me some problems

### java.io.UncheckedIOException
```
Caused by: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class com.mongodb.client.model.SimpleExpression and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.mongodb.client.model.BsonField["value"])
```
### 
Please Assist and Teach me

```kotlin
val result =  collection.aggregate<MyClass>(
	"""[ { ${match()} : { _id: $ id } }, { $project: { _id: { 
		|$ sumPrice: { $sum: '$ items.price' } } } } ]""".trimMargin()
).first()?.sumPrice ?: 0.0
```