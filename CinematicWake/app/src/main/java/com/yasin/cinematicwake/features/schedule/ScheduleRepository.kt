package com.yasin.cinematicwake.features.schedule

// import java.util.Calendar
import android.content.Context

data class ScheduledAlarm(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean = true
)

interface ScheduleRepository {
    fun getAll(): List<ScheduledAlarm>
}

class InMemoryScheduleRepository(
    private val context: Context
) : ScheduleRepository {

    override fun getAll(): List<ScheduledAlarm> {
        val (hour, minute) = ScheduleSelectionStore.getCurrentTime(context)

        return listOf(
            ScheduledAlarm(
                id = 1,
                hour = hour,
                minute = minute,
                enabled = true
            )
        )
    }
}

//class InMemoryScheduleRepository : ScheduleRepository {
//
//    override fun getAll(): List<ScheduledAlarm> {
//        // One alarm set to 1 minute in the future from now
//        val cal = Calendar.getInstance().apply {
//            add(Calendar.MINUTE, 1)
//        }
//
//        val hour = cal.get(Calendar.HOUR_OF_DAY)
//        val minute = cal.get(Calendar.MINUTE)
//
//        return listOf(
//            ScheduledAlarm(
//                id = 1,
//                hour = hour,
//                minute = minute,
//                enabled = true
//            )
//        )
//    }
//}
