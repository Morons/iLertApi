package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class LambPrimalCutType(val cut: Int, val name: String) {
	object Neck : LambPrimalCutType(cut = 401, name = "Neck")
	object ThickRib : LambPrimalCutType(cut = 402, name = "Thick Rib")
	object Rib : LambPrimalCutType(cut = 403, name = "Rib")
	object Loin : LambPrimalCutType(cut = 404, name = "Loin")
	object Chump : LambPrimalCutType(cut = 405, name = "Chump")
	object Flank : LambPrimalCutType(cut = 406, name = "Flank")
	object Breast : LambPrimalCutType(cut = 407, name = "Breast")
	object Shoulder : LambPrimalCutType(cut = 408, name = "Shoulder")
	object Leg : LambPrimalCutType(cut = 409, name = "Leg")
	object Shank : LambPrimalCutType(cut = 410, name = "Shank")
	object Stew : LambPrimalCutType(cut = 411, name = "Stew")
	object MeatyBones : LambPrimalCutType(cut = 412, name = "Meaty Bones")
	object LambBones : LambPrimalCutType(cut = 413, name = "Lamb Bones")
	object LambTrimmings9010 : LambPrimalCutType(cut = 414, name = "Lamb Trimmings 90:10")
	object LambTrimmings8020 : LambPrimalCutType(cut = 415, name = "Lamb Trimmings 80:20")
	object LambTrimmings7030 : LambPrimalCutType(cut = 416, name = "Lamb Trimmings 70:30")
	object LambTrimmings6040 : LambPrimalCutType(cut = 417, name = "Lamb Trimmings 60:40")
	object LambBodyFat : LambPrimalCutType(cut = 418, name = "Lamb Body Fat")
	object LambKidneyFat : LambPrimalCutType(cut = 419, name = "Lamb Kidney Fat")
	object CutTrimWaste : LambPrimalCutType(cut = 120, name = "Cutting & Trimming Waste")
	object None : LambPrimalCutType(cut = 499, name = "None")

	companion object {
		fun fromCut(cut: Int): LambPrimalCutType {
			return when (cut) {
				401 -> Neck
				402 -> ThickRib
				403 -> Rib
				404 -> Loin
				405 -> Chump
				406 -> Flank
				407 -> Breast
				408 -> Shoulder
				409 -> Leg
				410 -> Shank
				411 -> Stew
				412 -> MeatyBones
				413 -> LambBones
				414 -> LambTrimmings9010
				415 -> LambTrimmings8020
				416 -> LambTrimmings7030
				417 -> LambTrimmings6040
				418 -> LambBodyFat
				419 -> LambKidneyFat
				420 -> CutTrimWaste
				else -> None
			}
		}
	}
}