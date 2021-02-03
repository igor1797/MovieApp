package igor.kuridza.dice.movieapp.common

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity


fun showDialog(
    activity: FragmentActivity?,
    title: String,
    positiveButton: Int,
    positiveListener: () -> Unit?,
    negativeButton: Int
) {
    activity?.let {
        AlertDialog.Builder(it).setTitle(title)
            .setPositiveButton(positiveButton) { _, _ -> positiveListener() }
            .setNegativeButton(negativeButton, null)
            .show()
    }
}