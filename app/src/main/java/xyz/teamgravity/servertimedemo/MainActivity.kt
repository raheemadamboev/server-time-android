package xyz.teamgravity.servertimedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.teamgravity.servertime.ServerTime
import xyz.teamgravity.servertimedemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button()
    }

    private fun button() {
        onGetTimeCallback()
        onGetTimeSuspend()
    }

    private fun onGetTimeCallback() {
        binding.apply {
            timeCallbackB.setOnClickListener {
                timeT.text = getString(R.string.retrieving_time)

                ServerTime().execute { time ->
                    when (time) {

                        ServerTime.UNKNOWN_HOST -> { // internet not working or error in host
                            timeT.text = getString(R.string.unknown_host)
                        }

                        ServerTime.IO_EXCEPTION -> { // io exception
                            timeT.text = getString(R.string.io_exception)
                        }

                        ServerTime.TIMEOUT_EXCEPTION -> { // timeout
                            timeT.text = getString(R.string.timeout_exception)
                        }

                        else -> { // we got time successfully
                            timeT.text = Date(time).toString()
                        }
                    }
                }
            }
        }
    }

    private fun onGetTimeSuspend() {
        binding.apply {
            timeSuspendB.setOnClickListener {
                lifecycleScope.launch {
                    timeT.text = getString(R.string.retrieving_time)

                    when (val time = ServerTime().execute()) {

                        ServerTime.UNKNOWN_HOST -> { // internet not working or error in host
                            timeT.text = getString(R.string.unknown_host)
                        }

                        ServerTime.IO_EXCEPTION -> { // io exception
                            timeT.text = getString(R.string.io_exception)
                        }

                        ServerTime.TIMEOUT_EXCEPTION -> { // timeout
                            timeT.text = getString(R.string.timeout_exception)
                        }

                        else -> { // we got time successfully
                            timeT.text = Date(time).toString()
                        }
                    }
                }
            }
        }
    }
}