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
	object BeefBonesType : BeefHqPrimalCutType(cut = 213)
	object BeefTrimmings9010Type : BeefHqPrimalCutType(cut = 214)
	object BeefTrimmings8020Type : BeefHqPrimalCutType(cut = 215)
	object BeefTrimmings7030Type : BeefHqPrimalCutType(cut = 216)
	object BeefTrimmings6040Type : BeefHqPrimalCutType(cut = 217)
	object BeefBodyFatType : BeefHqPrimalCutType(cut = 218)
	object BeefFatType : BeefHqPrimalCutType(cut = 219)
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
				213 -> BeefBonesType
				214 -> BeefTrimmings9010Type
				215 -> BeefTrimmings8020Type
				216 -> BeefTrimmings7030Type
				217 -> BeefTrimmings6040Type
				218 -> BeefBodyFatType
				219 -> BeefFatType
				else -> None
			}
		}
	}
}