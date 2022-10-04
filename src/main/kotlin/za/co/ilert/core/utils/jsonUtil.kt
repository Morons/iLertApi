package za.co.ilert.core.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

fun <T> Gson.fromJsonOrNull(json: String, clazz: Class<T>): T? {
	if (!json.isValidJson()) return null
	return try {
		fromJson(json, clazz)
	} catch (e: JsonSyntaxException) {
		null
	}
}

fun String.isValidJson(): Boolean {
//	val builder = GsonBuilder()
//	builder.serializeSpecialFloatingPointValues()
	try {
		JsonParser.parseString(this)
	} catch (e: JsonSyntaxException) {
		return false
	}
	return true
}