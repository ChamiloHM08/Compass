package Compass.Package.ui.playlists

import Compass.Package.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaylistAdapter (
    private val playlistList: List<String>,
    private val imageResources: List<Int>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return PlaylistViewHolder(itemView)
    }

    override fun getItemCount(): Int = playlistList.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlistTitle = playlistList[position]
        val imageResource = imageResources[position]

        holder.bind(playlistTitle, imageResource)
    }

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvPlaylistTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(playlistTitle: String, imageResource: Int) {
            titleTextView.text = playlistTitle
            imageView.setImageResource(imageResource)

            itemView.setOnClickListener { onItemClick(playlistTitle) }
        }
    }
}