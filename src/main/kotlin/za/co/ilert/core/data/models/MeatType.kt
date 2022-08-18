package za.co.ilert.core.data.models

sealed class MeatType(val type: Int) { // Beef, Pork, Lamb
	object Beef : MeatType(type = 0)
	object Pork : MeatType(type = 1)
	object Lamb : MeatType(type = 2)
	object None : MeatType(type = 3)

	companion object {
		fun fromType(type: Int): MeatType {
			return when (type) {
				0 -> Beef
				1 -> Pork
				2 -> Lamb
				else -> None
			}
		}
	}
}
