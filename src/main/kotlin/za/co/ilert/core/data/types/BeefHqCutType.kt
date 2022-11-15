package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class BeefHqCutType(val cut: Int, val name: String) {
	object WingRib : BeefHqCutType(cut = 201, name = "Wing Rib")
	object ThinFlank : BeefHqCutType(cut = 202, name = "Thin Flank")
	object Rump : BeefHqCutType(cut = 203, name = "Rump")
	object Aitchbone : BeefHqCutType(cut = 204, name = "Aitchbone")
	object Topside : BeefHqCutType(cut = 205, name = "Topside")
	object Silverside : BeefHqCutType(cut = 206, name = "Silverside")
	object Fillet : BeefHqCutType(cut = 207, name = "Fillet")
	object ThickFlank : BeefHqCutType(cut = 208, name = "Thick Flank")
	object Sirloin : BeefHqCutType(cut = 209, name = "Sirloin")
	object Shin : BeefHqCutType(cut = 210, name = "Shin")
	object Stew : BeefHqCutType(cut = 211, name = "Stew")
	object MeatyBones : BeefHqCutType(cut = 212, name = "Meaty Bones")
	object BeefBones : BeefHqCutType(cut = 213, name = "Beef Bones")
	object BeefTrimmings9010 : BeefHqCutType(cut = 114, name = "Beef Trimmings 90:10")
	object BeefTrimmings8020 : BeefHqCutType(cut = 115, name = "Beef Trimmings 80:20")
	object BeefTrimmings7030 : BeefHqCutType(cut = 116, name = "Beef Trimmings 70:30")
	object BeefTrimmings6040 : BeefHqCutType(cut = 117, name = "Beef Trimmings 60:40")
	object BeefBodyFat : BeefHqCutType(cut = 218, name = "Beef Body Fat")
	object BeefFat : BeefHqCutType(cut = 219, name = "Beef Fat")
	object CutTrimWaste : BeefHqCutType(cut = 220, name = "Cutting & Trimming Waste")
	object None : BeefHqCutType(cut = 299, name = "None")

	companion object {
		fun fromCut(cut: Int): BeefHqCutType {
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
				220 -> CutTrimWaste
				else -> None
			}
		}
	}
}