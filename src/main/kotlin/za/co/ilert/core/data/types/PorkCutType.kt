package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class PorkCutType(val cut: Int, val name: String) {
	object ThickRib : PorkCutType(cut = 301, name = "Thick Rib")
	object Rib : PorkCutType(cut = 302, name = "Rib")
	object Loin : PorkCutType(cut = 303, name = "Loin")
	object Leg : PorkCutType(cut = 304, name = "Leg")
	object Breast : PorkCutType(cut = 305, name = "Breast")
	object Belly : PorkCutType(cut = 306, name = "Belly")
	object Chump : PorkCutType(cut = 307, name = "Chump")
	object Shank : PorkCutType(cut = 308, name = "Shank")
	object Rinds : PorkCutType(cut = 309, name = "Rinds")
	object Trotters : PorkCutType(cut = 310, name = "Trotters")
	object Stew : PorkCutType(cut = 311, name = "Stew")
	object MeatyBones : PorkCutType(cut = 312, name = "Meaty Bones")
	object PorkBones : PorkCutType(cut = 313, name = "Pork Bones")
	object PorkTrimmings9010 : PorkCutType(cut = 314, name = "Pork Trimmings 90:10")
	object PorkTrimmings8020 : PorkCutType(cut = 315, name = "Pork Trimmings 80:20")
	object PorkTrimmings7030 : PorkCutType(cut = 316, name = "Pork Trimmings 70:30")
	object PorkTrimmings6040 : PorkCutType(cut = 317, name = "Pork Trimmings 60:40")
	object PorkTrimmings5050 : PorkCutType(cut = 318, name = "Pork Trimmings 50:50")
	object PorkSpek : PorkCutType(cut = 319, name = "Pork Spek")
	object None : PorkCutType(cut = 320, name = "None")


	companion object {
		fun fromCut(cut: Int): PorkCutType {
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