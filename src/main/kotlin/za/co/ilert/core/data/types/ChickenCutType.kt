package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class ChickenCutType(val cut: Int, val name: String) {
	object DrumSticks : ChickenCutType(cut = 501, name = "Drum Sticks")
	object Thighs : ChickenCutType(cut = 502, name = "Thighs")
	object BreastsFillet : ChickenCutType(cut = 503, name = "Breasts Fillet")
	object Wings : ChickenCutType(cut = 504, name = "Wings")
	object LegQuarters : ChickenCutType(cut = 505, name = "Leg Quarters")
	object Bones : ChickenCutType(cut = 506, name = "Bones")
	object Heads : ChickenCutType(cut = 507, name = "Heads")
	object Feet : ChickenCutType(cut = 508, name = "Feet")
	object ChickenTrimmings : ChickenCutType(cut = 509, name = "Chicken Trimmings")
	object ChickenFat : ChickenCutType(cut = 510, name = "Chicken Fat")
	object ChickenSkinFat : ChickenCutType(cut = 511, name = "Chicken Skin Fat")
	object CutTrimWaste : ChickenCutType(cut = 512, name = "Cutting & Trimming Waste")
	object None : ChickenCutType(cut = 599, name = "")

	companion object {
		fun fromCut(cut: Int): ChickenCutType {
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