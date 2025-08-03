package xyz.teamgravity.servertimedemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.teamgravity.servertime.ServerTime
import xyz.teamgravity.servertimedemo.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val time: ServerTime = ServerTime()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ui()
        button()
    }

    private fun ui() {
        edgeToEdge()
    }

    private fun button() {
        onGetTimeCallback()
        onGetTimeSuspend()
    }

    private fun edgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val paddings =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime())
            view.setPadding(paddings.left, paddings.top, paddings.right, paddings.bottom)
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun onGetTimeCallback() {
        binding.apply {
            timeCallbackB.setOnClickListener {
                timeT.text = getString(R.string.retrieving_time)

                time.execute { time ->
                    when (time) {
                        ServerTime.Result.UNKNOWN_HOST -> { // internet not working or error in host
                            timeT.text = getString(R.string.unknown_host)
                        }

                        ServerTime.Result.IO_EXCEPTION -> { // io exception
                            timeT.text = getString(R.string.io_exception)
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

                    when (val time = time.execute()) {
                        ServerTime.Result.UNKNOWN_HOST -> { // internet not working or error in host
                            timeT.text = getString(R.string.unknown_host)
                        }

                        ServerTime.Result.IO_EXCEPTION -> { // io exception
                            timeT.text = getString(R.string.io_exception)
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