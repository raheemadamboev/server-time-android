package xyz.teamgravity.servertime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.net.ntp.NTPUDPClient
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException

class ServerTime {

    companion object {
        private const val SERVER_ADDRESS = "time.google.com"

        const val UNKNOWN_HOST = -1L
        const val IO_EXCEPTION = -2L
    }


    fun execute(listener: (time: Long) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                val timeClient = NTPUDPClient()
                val netAddress: InetAddress = InetAddress.getByName(SERVER_ADDRESS)
                val timeInfo = timeClient.getTime(netAddress)
                val time = timeInfo.message.transmitTimeStamp.time

                withContext(Dispatchers.Main) { listener(time) }

            } catch (e: UnknownHostException) {
                withContext(Dispatchers.Main) { listener(UNKNOWN_HOST) }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) { listener(IO_EXCEPTION) }
            }
        }
    }
}
