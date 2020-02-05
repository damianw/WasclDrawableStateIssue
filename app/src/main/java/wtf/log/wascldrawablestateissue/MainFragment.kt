package wtf.log.wascldrawablestateissue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view
            .findViewById<MaterialButton>(R.id.button)
            .addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(view: View) {
                    // ISSUE: Calling getDrawableState has the effect of computing a new drawable
                    // state without issuing a call to drawableStateChanged. The result is that the
                    // MaterialButtonHelper is not updated with the new drawable state until the
                    // next time it changes (e.g. by touching the button, etc).
                    view.drawableState
                    // Although you might not typically call getDrawableState directly within an
                    // OnAttachStateChangeListener, the effect is reproducible by doing something as
                    // innocent as updating the text color or other similar actions, which call
                    // getDrawableState under the hood:
                    // (view as Button).setTextColor(Color.BLACK)

                    // Workaround: Adding a call to refreshDrawableState within any
                    // OnAttachStateListener works around this problem by ensuring a call to
                    // drawableStateChanged. Uncomment this to see:
                    // view.refreshDrawableState()
                }

                override fun onViewDetachedFromWindow(view: View) {
                }
            })
        return view
    }
}
