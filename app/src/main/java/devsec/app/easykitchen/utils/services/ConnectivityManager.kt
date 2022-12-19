package devsec.app.easykitchen.utils.services

import android.content.Context
import kotlinx.coroutines.flow.Flow


class ConnectivityManager(private val context: Context)  {





interface IConnectivityManager {
    fun isNetworkAvailable(): Flow<Status>

    enum class Status {
        AVAILABLE, UNAVAILABLE
    }
}
}