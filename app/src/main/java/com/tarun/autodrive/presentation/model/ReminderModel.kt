package com.tarun.autodrive.presentation.model

data class ReminderItemModel(
    val id: String,
    val title: String,
    val vehicleName: String,
    val dueDate: String,
    val status: String,
)

object ReminderRepository {
    fun getReminders(): List<ReminderItemModel> {
        return listOf(
            ReminderItemModel("1", "General Service", "Hyundai Creta (KA 01 AB 1234)", "21 May 2024", "Due in 5 days"),
            ReminderItemModel("2", "Insurance Expiry", "Honda City (KA 02 CD 5678)", "10 June 2024", "Due in 25 days"),
            ReminderItemModel("3", "PUC Certificate Expiry", "Hyundai Creta (KA 01 AB 1234)", "05 May 2024", "Expired 3 days ago"),
            ReminderItemModel("4", "Battery Check", "Kia Seltos (KA 03 EF 9012)", "30 June 2024", "Upcoming"),
            ReminderItemModel("5", "General Service", "Honda City (KA 02 CD 5678)", "15 July 2024", "Upcoming"),
        )
    }
}
