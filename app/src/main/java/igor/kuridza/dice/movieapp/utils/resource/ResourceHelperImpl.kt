package igor.kuridza.dice.movieapp.utils.resource

import android.content.Context
import igor.kuridza.dice.movieapp.R

class ResourceHelperImpl(private val applicationContext: Context) : ResourceHelper {

    override fun topRatedString(): String =
        applicationContext.resources.getString(R.string.topRated)

    override fun popularString(): String =
        applicationContext.resources.getString(R.string.popularText)

    override fun upcomingString(): String =
        applicationContext.resources.getString(R.string.upcomingText)

    override fun onTheAirString(): String =
        applicationContext.resources.getString(R.string.onTheAir)

    override fun invalidPasswordStringId(): Int =
        R.string.invalidPasswordText

    override fun invalidUsernameStringId(): Int =
        R.string.invalidUsernameText
}