package za.co.ilert.presentation.validation


sealed class ValidationEvent {
	object ErrorFieldEmpty : ValidationEvent()
	object Success : ValidationEvent()
}