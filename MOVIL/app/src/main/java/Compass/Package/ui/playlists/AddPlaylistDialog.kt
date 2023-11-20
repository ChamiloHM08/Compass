package Compass.Package.ui.playlists

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import Compass.Package.R

class AddPlaylistDialog(private val listener: AddPlaylistDialogListener) : AppCompatDialogFragment() {

    interface AddPlaylistDialogListener {
        fun onAddButtonClick(playlistName: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_playlist, null)

        val playlistNameEditText: EditText = view.findViewById(R.id.editTextPlaylistName)
        val addButton: Button = view.findViewById(R.id.btnAdd)

        builder.setView(view)
            .setTitle("Añadir Playlist")
            .setNegativeButton("Cancelar") { _, _ ->
                // No hacer nada en caso de cancelar
            }

        val dialog = builder.create()

        addButton.setOnClickListener {
            val playlistName = playlistNameEditText.text.toString()
            listener.onAddButtonClick(playlistName)
            dialog.dismiss()
        }

        return dialog
    }
}

