package za.co.ilert.core.data.responses


data class CalculationsResponse(
	val valueA: Double,
	val valueB: Double
) {
	val valueC: Double get() = valueA * valueB

	val valueD: Double = if (valueB > 0) valueA / valueB else 0.0

	val valueE: () -> Double = { if (valueB > 0) valueA / valueB else 0.0 }
}

//val calculation: CalculationsResponse? = calculateService.getCalculation()
//call.respond(status = OK, message = BasicApiResponse(successful = true, data = calculation))
