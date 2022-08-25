package za.co.ilert.core.data.types

import kotlinx.serialization.Serializable

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
@Serializable
sealed class BeefFqPrimalCutType(val cut: Int) {
	object PrimeRib : BeefFqPrimalCutType(cut = 101)
	object Chuck : BeefFqPrimalCutType(cut = 102)
	object Blade : BeefFqPrimalCutType(cut = 103)
	object ShortRib : BeefFqPrimalCutType(cut = 104)
	object Brisket : BeefFqPrimalCutType(cut = 105)
	object Shin : BeefFqPrimalCutType(cut = 106)
	object Stew : BeefFqPrimalCutType(cut = 107)
	object MeatyBones : BeefFqPrimalCutType(cut = 108)
	object BeefBonesType : BeefFqPrimalCutType(cut = 109)
	object BeefTrimmings9010Type : BeefFqPrimalCutType(cut = 110)
	object BeefTrimmings8020Type : BeefFqPrimalCutType(cut = 111)
	object BeefTrimmings7030Type : BeefFqPrimalCutType(cut = 112)
	object BeefTrimmings6040Type : BeefFqPrimalCutType(cut = 113)
	object BeefBodyFatType : BeefFqPrimalCutType(cut = 114)
	object BeefFatType : BeefFqPrimalCutType(cut = 115)
	object None : BeefFqPrimalCutType(cut = 116)

	companion object {
		fun fromCut(cut: Int): BeefFqPrimalCutType {
			return when (cut) {
				101 -> PrimeRib
				102 -> Chuck
				103 -> Blade
				104 -> ShortRib
				105 -> Brisket
				106 -> Shin
				107 -> Stew
				108 -> MeatyBones
				109 -> BeefBonesType
				110 -> BeefTrimmings9010Type
				111 -> BeefTrimmings8020Type
				112 -> BeefTrimmings7030Type
				113 -> BeefTrimmings6040Type
				114 -> BeefBodyFatType
				115 -> BeefFatType
				else -> None
			}
		}
	}
}