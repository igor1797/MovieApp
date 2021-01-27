package igor.kuridza.dice.movieapp.common

fun convertToHoursAndMinutes(duration: Int): String {
    val hours = duration / 60
    val remainingMinutes = duration - hours * 60
    return when {
        hours == 0 -> "${remainingMinutes}m"
        remainingMinutes == 0 -> "${hours}h"
        else -> "${hours}h ${remainingMinutes}m"
    }
}