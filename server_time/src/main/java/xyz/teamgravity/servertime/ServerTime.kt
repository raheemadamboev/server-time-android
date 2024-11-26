package xyz.teamgravity.servertime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.net.ntp.NTPUDPClient
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException

class ServerTime {

    private companion object {
        const val SERVER_ADDRESS = "time.google.com"
    }

    private val scope: CoroutineScope = MainScope()

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun execute(listener: (time: Long) -> Unit) {
        scope.launch {
            listener(execute())
        }
    }

    suspend fun execute(): Long {
        return withContext(Dispatchers.IO) {
            val client = NTPUDPClient()
            try {
                val address: InetAddress = InetAddress.getByName(SERVER_ADDRESS)
                val info = client.getTime(address)
                return@withContext info.message.transmitTimeStamp.time
            } catch (e: UnknownHostException) {
                return@withContext Result.UNKNOWN_HOST
            } catch (e: IOException) {
                return@withContext Result.IO_EXCEPTION
            } finally {
                client.close()
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    object Result {
        const val UNKNOWN_HOST = -1L
        const val IO_EXCEPTION = -2L
    }
}