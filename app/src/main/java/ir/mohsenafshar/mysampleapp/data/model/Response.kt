package ir.mohsenafshar.mysampleapp.data.model

data class Response(
    val command: Command,
    val data: Map<String, Any>,
    val status: String
)