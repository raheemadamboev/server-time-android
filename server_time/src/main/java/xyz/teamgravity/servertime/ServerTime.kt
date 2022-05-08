package xyz.teamgravity.servertime

import kotlinx.coroutines.*
import org.apache.commons.net.ntp.NTPUDPClient
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException

class ServerTime {

    companion object {
        private const val SERVER_ADDRESS = "time.google.com"
        private const val TIMEOUT = 5_000L

        const val UNKNOWN_HOST = -1L
        const val IO_EXCEPTION = -2L
        const val TIMEOUT_EXCEPTION = -3L
    }


    fun execute(listener: (time: Long) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            withTimeoutOrNull(TIMEOUT) {
                withContext(Dispatchers.IO) {
                    var client: NTPUDPClient? = null
                    try {
                        client = NTPUDPClient()
                        val address: InetAddress = InetAddress.getByName(SERVER_ADDRESS)
                        val info= client.getTime(address)
                        val time = info.message.transmitTimeStamp.time

                        withContext(Dispatchers.Main) { listener(time) }
                    } catch (e: UnknownHostException) {
                        withContext(Dispatchers.Main) { listener(UNKNOWN_HOST) }
                    } catch (e: IOException) {
                        withContext(Dispatchers.Main) { listener(IO_EXCEPTION) }
                    } finally {
                        client?.close()
                    }
                }
            } ?: withContext(Dispatchers.Main) { listener(TIMEOUT_EXCEPTION) }
        }
    }

    suspend fun execute(): Long {
        return withTimeoutOrNull(TIMEOUT) {
            withContext(Dispatchers.IO) {
                var client: NTPUDPClient? = null
                try {
                    client = NTPUDPClient()
                    val address: InetAddress = InetAddress.getByName(SERVER_ADDRESS)
                    val info= client.getTime(address)
                    return@withContext info.message.transmitTimeStamp.time
                } catch (e: UnknownHostException) {
                    return@withContext UNKNOWN_HOST
                } catch (e: IOException) {
                    return@withContext IO_EXCEPTION
                } finally {
                    client?.close()
                }
            }
        } ?: TIMEOUT_EXCEPTION
    }
}
