package devsec.app.easykitchen.utils.services

import android.content.Context
import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status {
        AVAILABLE, UNAVAILABLE
    }

}