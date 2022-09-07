package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
sealed class CarcassType(val type: Int, val name: String) {
	object FrontQuarter : CarcassType(type = 100, name = "Front Quarter")
	object HindQuarter : CarcassType(type = 200, name = "Hind Quarter")
	object Pork : CarcassType(type = 300, name = "Pork")
	object Lamb : CarcassType(type = 400, name = "Lamb")
	object Chicken : CarcassType(type = 500, name = "Chicken")
	object None : CarcassType(type = 600, name = "None")

	companion object {
		fun fromType(type: Int): CarcassType {
			return when (type) {
				100 -> FrontQuarter
				200 -> HindQuarter
				300 -> Pork
				400 -> Lamb
				500 -> Chicken
				else -> None
			}
		}
	}
}
