package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class ChickenPrimalCutType(val cut: Int, val name: String) {
	object DrumSticks : ChickenPrimalCutType(cut = 501, name = "Drum Sticks")
	object Thighs : ChickenPrimalCutType(cut = 502, name = "Thighs")
	object BreastsFillet : ChickenPrimalCutType(cut = 503, name = "Breasts Fillet")
	object Wings : ChickenPrimalCutType(cut = 504, name = "Wings")
	object LegQuarters : ChickenPrimalCutType(cut = 505, name = "Leg Quarters")
	object Bones : ChickenPrimalCutType(cut = 506, name = "Bones")
	object Heads : ChickenPrimalCutType(cut = 507, name = "Heads")
	object Feet : ChickenPrimalCutType(cut = 508, name = "Feet")
	object ChickenTrimmings : ChickenPrimalCutType(cut = 509, name = "Chicken Trimmings")
	object ChickenFat : ChickenPrimalCutType(cut = 510, name = "Chicken Fat")
	object ChickenSkinFat : ChickenPrimalCutType(cut = 511, name = "Chicken Skin Fat")
	object CutTrimWaste : ChickenPrimalCutType(cut = 512, name = "Cutting & Trimming Waste")
	object None : ChickenPrimalCutType(cut = 599, name = "")

	companion object {
		fun fromCut(cut: Int): ChickenPrimalCutType {
			return when (cut) {
				501 -> DrumSticks
				502 -> Thighs
				503 -> BreastsFillet
				504 -> Wings
				505 -> LegQuarters
				506 -> Bones
				507 -> Heads
				508 -> Feet
				509 -> ChickenTrimmings
				510 -> ChickenFat
				511 -> ChickenSkinFat
				512 -> CutTrimWaste
				else -> None
			}
		}
	}
}