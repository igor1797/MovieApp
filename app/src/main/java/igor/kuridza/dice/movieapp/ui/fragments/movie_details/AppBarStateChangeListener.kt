package igor.kuridza.dice.movieapp.ui.fragments.movie_details

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs


abstract class AppBarStateChangeListener : OnOffsetChangedListener {
    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        mCurrentState = when {
            i == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(State.EXPANDED)
                }
                State.EXPANDED
            }
            abs(i) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(State.COLLAPSED)
                }
                State.COLLAPSED
            }
            else -> {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(State.IDLE)
                }
                State.IDLE
            }
        }
    }

    abstract fun onStateChanged(state: State)
}