package za.co.ilert.core.util

import io.ktor.util.*
import java.io.File

// FIXME: 2022/06/21 Get this from Ktor
fun getByteArray(filePathName: String): String {
	val byteArray = File(filePathName).readBytes()
	return byteArray.encodeBase64()
}

val String.containsLatinLetter: Boolean
	get() = matches(Regex(".*[A-Za-z].*"))

val String.containsDigit: Boolean
	get() = matches(Regex(".*\\d.*"))  // ".*[0-9].*"

val String.isAlphanumeric: Boolean
	get() = matches(Regex("[A-Za-z\\d]*"))

val String.hasSpecialCharacters: Boolean
	get() = matches(Regex(".*[.~!@#%^*_+=-].*"))

val String.isSafeForSearch: Boolean
	get() = hasSpecialCharacters || isAlphanumeric || isBlank() || isEmpty()

val String.hasLettersAndDigits: Boolean
	get() = containsLatinLetter && containsDigit

val String.isIntegerNumber: Boolean
	get() = toIntOrNull() != null

val String.toDecimalNumber: Boolean
	get() = toDoubleOrNull() != null


///**
// * Returns a string describing 'time' as a time relative to 'now'.
// * <p>
// * Time spans in the past are formatted like "42 minutes ago".
// * Time spans in the future are formatted like "In 42 minutes".
// * @see getRelativeTimeSpanString
// * @sample 1653989297297.relativeTimeSpan()
// **/
//fun Long.relativeTimeSpan(): String {
//	return getRelativeTimeSpanString(
//		time = this,
//		now = Instant.now().toEpochMilli(),
//		minResolution = AppDateUtils.SECOND_IN_MILLIS
//	).toString()
//}
//
///**
// * Returns a string describing 'time' as a time relative to 'now'.
// * <p>
// * Time spans in the past are formatted like "42 minutes ago".
// * Time spans in the future are formatted like "In 42 minutes".
// *
// * @param time, supplied via Extension, the time to describe, in milliseconds
// * @param now the current time in milliseconds
// * @param minResolution the minimum timespan to report. For example, a time 3 seconds in the
// *     past will be reported as "0 minutes ago" if this is set to MINUTE_IN_MILLIS. Pass one of
// *     0, MINUTE_IN_MILLIS, HOUR_IN_MILLIS, DAY_IN_MILLIS, WEEK_IN_MILLIS
// *
// **/
//fun getRelativeTimeSpanString(time: Long, now: Long, minResolution: Long): CharSequence? {
//	val flags = AppDateUtils.FORMAT_SHOW_DATE or AppDateUtils.FORMAT_SHOW_YEAR or AppDateUtils.FORMAT_ABBREV_MONTH
//	return AppDateUtils.getRelativeTimeSpanString(time, now, minResolution, flags)
//}

//fun isOk(string: String): Boolean {
//	println("Testing String : $string")
//	println("it in 'A'..'Z' = ${string.filter { it in 'A'..'Z' }.length == string.length}")
//	println("it in 'a'..'z' = ${string.filter { it in 'a'..'z' }.length == string.length}")
//	println("it in '.0123456789-+*/^#@ ~' = ${string.filter { it in ".0123456789-+*/^#@ ~" }.length == string.length}")
//	return string.filter { it in 'A'..'Z' || it in 'a'..'z' || it in ".0123456789-+*/^#@ ~" }.length == string
//		.length
//}
