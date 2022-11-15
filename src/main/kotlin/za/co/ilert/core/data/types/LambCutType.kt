package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class LambCutType(val cut: Int, val name: String) {
	object Neck : LambCutType(cut = 401, name = "Neck")
	object ThickRib : LambCutType(cut = 402, name = "Thick Rib")
	object Rib : LambCutType(cut = 403, name = "Rib")
	object Loin : LambCutType(cut = 404, name = "Loin")
	object Chump : LambCutType(cut = 405, name = "Chump")
	object Flank : LambCutType(cut = 406, name = "Flank")
	object Breast : LambCutType(cut = 407, name = "Breast")
	object Shoulder : LambCutType(cut = 408, name = "Shoulder")
	object Leg : LambCutType(cut = 409, name = "Leg")
	object Shank : LambCutType(cut = 410, name = "Shank")
	object Stew : LambCutType(cut = 411, name = "Stew")
	object MeatyBones : LambCutType(cut = 412, name = "Meaty Bones")
	object LambBones : LambCutType(cut = 413, name = "Lamb Bones")
	object LambTrimmings9010 : LambCutType(cut = 414, name = "Lamb Trimmings 90:10")
	object LambTrimmings8020 : LambCutType(cut = 415, name = "Lamb Trimmings 80:20")
	object LambTrimmings7030 : LambCutType(cut = 416, name = "Lamb Trimmings 70:30")
	object LambTrimmings6040 : LambCutType(cut = 417, name = "Lamb Trimmings 60:40")
	object LambBodyFat : LambCutType(cut = 418, name = "Lamb Body Fat")
	object LambKidneyFat : LambCutType(cut = 419, name = "Lamb Kidney Fat")
	object CutTrimWaste : LambCutType(cut = 120, name = "Cutting & Trimming Waste")
	object None : LambCutType(cut = 499, name = "None")

	companion object {
		fun fromCut(cut: Int): LambCutType {
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