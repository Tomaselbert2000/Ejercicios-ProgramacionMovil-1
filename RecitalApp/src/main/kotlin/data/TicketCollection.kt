package main.kotlin.data

data class TicketCollection(
    val id: Long,
    val userId: Long,
    val ticketCollection: MutableList<Long>
)
