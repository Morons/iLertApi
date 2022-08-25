package za.co.ilert.core.data.types

import kotlinx.serialization.Serializable

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
@Serializable
sealed class ChickenPrimalCutType(val cut: Int) {
	object DrumSticks : ChickenPrimalCutType(cut = 501)
	object Thighs : ChickenPrimalCutType(cut = 502)
	object BreastsFillet : ChickenPrimalCutType(cut = 503)
	object Wings : ChickenPrimalCutType(cut = 504)
	object LegQuarters : ChickenPrimalCutType(cut = 505)
	object Bones : ChickenPrimalCutType(cut = 506)
	object Heads : ChickenPrimalCutType(cut = 507)
	object Feet : ChickenPrimalCutType(cut = 508)
	object ChickenTrimmingsType : ChickenPrimalCutType(cut = 509)
	object ChickenFatType : ChickenPrimalCutType(cut = 510)
	object ChickenSkinFatType : ChickenPrimalCutType(cut = 511)
	object None : ChickenPrimalCutType(cut = 512)

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
				509 -> ChickenTrimmingsType
				510 -> ChickenFatType
				511 -> ChickenSkinFatType
				512 -> Bones
				else -> None
			}
		}
	}
}