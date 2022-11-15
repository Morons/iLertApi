package za.co.ilert.core.data.types

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx

sealed class BeefFqCutType(val cut: Int, val name: String) {
	object PrimeRib : BeefFqCutType(cut = 101, name = "Prime Rib")
	object Chuck : BeefFqCutType(cut = 102, name = "Chuck")
	object Blade : BeefFqCutType(cut = 103, name = "Blade")
	object ShortRib : BeefFqCutType(cut = 104, name = "Short Rib")
	object Brisket : BeefFqCutType(cut = 105, name = "Brisket")
	object Shin : BeefFqCutType(cut = 106, name = "Shin")
	object Stew : BeefFqCutType(cut = 107, name = "Stew")
	object Bolo : BeefFqCutType(cut = 108, name = "Bolo")
	object MeatyBones : BeefFqCutType(cut = 109, name = "Meaty Bones")
	object BeefBones : BeefFqCutType(cut = 110, name = "Beef Bones")
	object BeefTrimmings9010 : BeefFqCutType(cut = 111, name = "Beef Trimmings 90:10")
	object BeefTrimmings8020 : BeefFqCutType(cut = 112, name = "Beef Trimmings 80:20")
	object BeefTrimmings7030 : BeefFqCutType(cut = 113, name = "Beef Trimmings 70:30")
	object BeefTrimmings6040 : BeefFqCutType(cut = 114, name = "Beef Trimmings 60:40")
	object BeefBodyFat : BeefFqCutType(cut = 115, name = "Beef Body Fat")
	object BeefFat : BeefFqCutType(cut = 116, name = "Beef Fat")
	object CutTrimWaste : BeefFqCutType(cut = 117, name = "Cutting & Trimming Waste")
	object None : BeefFqCutType(cut = 199, name = "None")

	companion object {
		fun fromCut(cut: Int): BeefFqCutType {
			return when (cut) {
				101 -> PrimeRib
				102 -> Chuck
				103 -> Blade
				104 -> ShortRib
				105 -> Brisket
				106 -> Shin
				107 -> Stew
				108 -> Bolo
				109 -> MeatyBones
				110 -> BeefBones
				111 -> BeefTrimmings9010
				112 -> BeefTrimmings8020
				113 -> BeefTrimmings7030
				114 -> BeefTrimmings6040
				115 -> BeefBodyFat
				116 -> BeefFat
				117 -> CutTrimWaste
				else -> None
			}
		}
	}
}