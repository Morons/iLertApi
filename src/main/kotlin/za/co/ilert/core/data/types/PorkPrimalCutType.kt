package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class PorkPrimalCutType(val cut: Int, val name: String) {
	object ThickRib : PorkPrimalCutType(cut = 301, name = "Thick Rib")
	object Rib : PorkPrimalCutType(cut = 302, name = "Rib")
	object Loin : PorkPrimalCutType(cut = 303, name = "Loin")
	object Leg : PorkPrimalCutType(cut = 304, name = "Leg")
	object Breast : PorkPrimalCutType(cut = 305, name = "Breast")
	object Belly : PorkPrimalCutType(cut = 306, name = "Belly")
	object Chump : PorkPrimalCutType(cut = 307, name = "Chump")
	object Shank : PorkPrimalCutType(cut = 308, name = "Shank")
	object Rinds : PorkPrimalCutType(cut = 309, name = "Rinds")
	object Trotters : PorkPrimalCutType(cut = 310, name = "Trotters")
	object Stew : PorkPrimalCutType(cut = 311, name = "Stew")
	object MeatyBones : PorkPrimalCutType(cut = 312, name = "Meaty Bones")
	object PorkBones : PorkPrimalCutType(cut = 313, name = "Pork Bones")
	object PorkTrimmings9010 : PorkPrimalCutType(cut = 314, name = "Pork Trimmings 90:10")
	object PorkTrimmings8020 : PorkPrimalCutType(cut = 315, name = "Pork Trimmings 80:20")
	object PorkTrimmings7030 : PorkPrimalCutType(cut = 316, name = "Pork Trimmings 70:30")
	object PorkTrimmings6040 : PorkPrimalCutType(cut = 317, name = "Pork Trimmings 60:40")
	object PorkTrimmings5050 : PorkPrimalCutType(cut = 318, name = "Pork Trimmings 50:50")
	object PorkSpek : PorkPrimalCutType(cut = 319, name = "Pork Spek")
	object None : PorkPrimalCutType(cut = 320, name = "None")


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
				313 -> PorkBones
				314 -> PorkTrimmings9010
				315 -> PorkTrimmings8020
				316 -> PorkTrimmings7030
				317 -> PorkTrimmings6040
				318 -> PorkTrimmings5050
				319 -> PorkSpek
				else -> None
			}
		}
	}
}