package za.co.ilert.core.data.models


sealed class CarcassType(val type: Int) {
	object FrontQuarter : CarcassType(type = 0)
	object HindQuarter : CarcassType(type = 1)
	object Pork : CarcassType(type = 2)
	object Lamb : CarcassType(type = 3)
	object None : CarcassType(type = 4)

	companion object {
		fun fromType(type: Int): CarcassType {
			return when (type) {
				0 -> FrontQuarter
				1 -> HindQuarter
				2 -> Pork
				3 -> Lamb
				else -> None
			}
		}
	}
}
