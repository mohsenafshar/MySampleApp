package ir.mohsenafshar.mysampleapp.data.model

data class MyResponse(
    val command: Command,
    val data: Map<String, Any>,
    val status: String
)