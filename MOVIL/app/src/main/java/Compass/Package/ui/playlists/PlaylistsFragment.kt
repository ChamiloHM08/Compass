    package Compass.Package.ui.playlists

    import Compass.Package.R
    import Compass.Package.databinding.FragmentPlaylistsBinding
    import android.os.Bundle
    import android.view.*
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView

    class PlaylistsFragment : Fragment() {

        private var _binding: FragmentPlaylistsBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
            val root: View = binding.root

            val playlistList = listOf(
                "Halloween",
                "Acción",
                "Comedias",
                "Familia",
                "Pareja"
            )

            val imageResourcesForRecyclerView1 = listOf(
                R.drawable.halloween,
                R.drawable.accion,
                R.drawable.comedia,
                R.drawable.familias,
                R.drawable.parejas,
            )

            val recyclerView: RecyclerView = root.findViewById(R.id.rvPlaylists)
            recyclerView.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)

            val adapter = PlaylistAdapter(playlistList, imageResourcesForRecyclerView1) { playlistTitle ->
                Toast.makeText(context, "Esto Redireccionará a películas en: $playlistTitle", Toast.LENGTH_SHORT).show()
            }

            recyclerView.adapter = adapter

            return root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }