package za.co.ilert.core.data.types

import kotlinx.serialization.Serializable

// Beef Front Quarter 1xx, Beef Hind Quarter 2xx, Pork 3xx, Lamb 4xx, Chicken 5xx
@Serializable
sealed class LambPrimalCutType(val cut: Int) {
	object Neck : LambPrimalCutType(cut = 401)
	object ThickRib : LambPrimalCutType(cut = 402)
	object Rib : LambPrimalCutType(cut = 403)
	object Loin : LambPrimalCutType(cut = 404)
	object Chump : LambPrimalCutType(cut = 405)
	object Flank : LambPrimalCutType(cut = 406)
	object Breast : LambPrimalCutType(cut = 407)
	object Shoulder : LambPrimalCutType(cut = 408)
	object Leg : LambPrimalCutType(cut = 409)
	object Shank : LambPrimalCutType(cut = 410)
	object Stew : LambPrimalCutType(cut = 411)
	object MeatyBones : LambPrimalCutType(cut = 412)
	object LambBonesType : LambPrimalCutType(cut = 413)
	object LambTrimmings9010Type : LambPrimalCutType(cut = 414)
	object LambTrimmings8020Type : LambPrimalCutType(cut = 415)
	object LambTrimmings7030Type : LambPrimalCutType(cut = 416)
	object LambTrimmings6040Type : LambPrimalCutType(cut = 417)
	object LambBodyFatType : LambPrimalCutType(cut = 418)
	object LambKidneyFatType : LambPrimalCutType(cut = 419)
	object None : LambPrimalCutType(cut = 420)

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
				413 -> LambBonesType
				414 -> LambTrimmings9010Type
				415 -> LambTrimmings8020Type
				416 -> LambTrimmings7030Type
				417 -> LambTrimmings6040Type
				418 -> LambBodyFatType
				419 -> LambKidneyFatType
				else -> None
			}
		}
	}
}