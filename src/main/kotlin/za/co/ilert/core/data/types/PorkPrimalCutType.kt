package za.co.ilert.core.data.types

import kotlinx.serialization.Serializable

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
@Serializable
sealed class PorkPrimalCutType(val cut: Int) {
	object ThickRib : PorkPrimalCutType(cut = 301)
	object Rib : PorkPrimalCutType(cut = 302)
	object Loin : PorkPrimalCutType(cut = 303)
	object Leg : PorkPrimalCutType(cut = 304)
	object Breast : PorkPrimalCutType(cut = 305)
	object Belly : PorkPrimalCutType(cut = 306)
	object Chump : PorkPrimalCutType(cut = 307)
	object Shank : PorkPrimalCutType(cut = 308)
	object Rinds : PorkPrimalCutType(cut = 309)
	object Trotters : PorkPrimalCutType(cut = 310)
	object Stew : PorkPrimalCutType(cut = 311)
	object MeatyBones : PorkPrimalCutType(cut = 312)
	object PorkBonesType : PorkPrimalCutType(cut = 313)
	object PorkTrimmings9010Type : PorkPrimalCutType(cut = 314)
	object PorkTrimmings8020Type : PorkPrimalCutType(cut = 315)
	object PorkTrimmings7030Type : PorkPrimalCutType(cut = 316)
	object PorkTrimmings6040Type : PorkPrimalCutType(cut = 317)
	object PorkTrimmings5050Type : PorkPrimalCutType(cut = 318)
	object PorkSpekType : PorkPrimalCutType(cut = 319)
	object None : PorkPrimalCutType(cut = 320)


	companion object {
		fun fromCut(cut: Int): PorkPrimalCutType {
			return when (cut) {
				301 -> ThickRib
				302 -> Rib
				303 -> Loin
				304 -> Leg
				305 -> Breast
				306 -> Belly
				307 -> Chump
				308 -> Shank
				309 -> Rinds
				310 -> Trotters
				311 -> Stew
				312 -> MeatyBones
				313 -> PorkBonesType
				314 -> PorkTrimmings9010Type
				315 -> PorkTrimmings8020Type
				316 -> PorkTrimmings7030Type
				317 -> PorkTrimmings6040Type
				318 -> PorkTrimmings5050Type
				319 -> PorkSpekType
				else -> None
			}
		}
	}
}