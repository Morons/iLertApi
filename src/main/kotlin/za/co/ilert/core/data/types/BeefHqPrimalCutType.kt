package za.co.ilert.core.data.types

import kotlinx.serialization.Serializable

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
@Serializable
sealed class BeefHqPrimalCutType(val cut: Int) {
	object WingRib : BeefHqPrimalCutType(cut = 201)
	object ThinFlank : BeefHqPrimalCutType(cut = 202)
	object Rump : BeefHqPrimalCutType(cut = 203)
	object Aitchbone : BeefHqPrimalCutType(cut = 204)
	object Topside : BeefHqPrimalCutType(cut = 205)
	object Silverside : BeefHqPrimalCutType(cut = 206)
	object Fillet : BeefHqPrimalCutType(cut = 207)
	object ThickFlank : BeefHqPrimalCutType(cut = 208)
	object Sirloin : BeefHqPrimalCutType(cut = 209)
	object Shin : BeefHqPrimalCutType(cut = 210)
	object Stew : BeefHqPrimalCutType(cut = 211)
	object MeatyBones : BeefHqPrimalCutType(cut = 212)
	object BeefBones : BeefHqPrimalCutType(cut = 213)
	object BeefTrimmings9010 : BeefHqPrimalCutType(cut = 214)
	object BeefTrimmings8020 : BeefHqPrimalCutType(cut = 215)
	object BeefTrimmings7030 : BeefHqPrimalCutType(cut = 216)
	object BeefTrimmings6040 : BeefHqPrimalCutType(cut = 217)
	object BeefBodyFat : BeefHqPrimalCutType(cut = 218)
	object BeefFat : BeefHqPrimalCutType(cut = 219)
	object None : BeefHqPrimalCutType(cut = 220)

	companion object {
		fun fromCut(cut: Int): BeefHqPrimalCutType {
			return when (cut) {
				201 -> WingRib
				202 -> ThinFlank
				203 -> Rump
				204 -> Aitchbone
				205 -> Topside
				206 -> Silverside
				207 -> Fillet
				208 -> ThickFlank
				209 -> Sirloin
				210 -> Shin
				211 -> Stew
				212 -> MeatyBones
				213 -> BeefBones
				214 -> BeefTrimmings9010
				215 -> BeefTrimmings8020
				216 -> BeefTrimmings7030
				217 -> BeefTrimmings6040
				218 -> BeefBodyFat
				219 -> BeefFat
				else -> None
			}
		}
	}
}