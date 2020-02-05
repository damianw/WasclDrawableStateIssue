package wtf.log.wascldrawablestateissue

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val handler = Handler()
    private val addFragmentRunnable = Runnable {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, MainFragment())
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // The postDelayed ensures that the window is focused when the fragment is added.
            // (This simulates doing a fragment transaction at at time after initialization)
            handler.postDelayed(addFragmentRunnable, 1000L)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(addFragmentRunnable)
    }
}
