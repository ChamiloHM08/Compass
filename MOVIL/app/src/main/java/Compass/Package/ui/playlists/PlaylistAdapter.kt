package Compass.Package.ui.playlists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import Compass.Package.R

class PlaylistAdapter(
    private val playlistList: List<Playlist>,
    private val onItemClick: (String) -> Unit,
    private val onItemLongClick: (Playlist) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private val selectedPlaylists = mutableListOf<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return PlaylistViewHolder(itemView)
    }

    override fun getItemCount(): Int = playlistList.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = playlistList[position]
        holder.bind(playlist)

        holder.itemView.setOnClickListener { onItemClick(playlist.name) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(playlist)
            true
        }

        holder.checkBox.isChecked = selectedPlaylists.contains(playlist)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedPlaylists.add(playlist)
            } else {
                selectedPlaylists.remove(playlist)
            }
        }
    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvPlaylistTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(playlist: Playlist) {
            titleTextView.text = playlist.name
            imageView.setImageResource(playlist.imageResource)
        }
    }

    fun getSelectedPlaylists(): List<Playlist> {
        return selectedPlaylists.toList()
    }
}


