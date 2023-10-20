package Compass.Package.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Compass.Package.R
import Compass.Package.databinding.FragmentHomeBinding
import Compass.Package.ui.adapter.Horizontal_RecycledView
import android.widget.ImageView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        val recyclerView1 = root.findViewById<RecyclerView>(R.id.recyclerView_1)

        val imageResourcesForRecyclerView = listOf(
            R.drawable.guardianes_galaxy,
            R.drawable.jurassic_parks,
            R.drawable.westworld,
            R.drawable.conde,
            R.drawable.jhon_wick_4,
            R.drawable.elemental,
            R.drawable.titanic,
            R.drawable.dragon_heart,
            R.drawable.avatar,
            // Agrega más recursos de imágenes según sea necesario
        )

        val imageResourcesForRecyclerView1 = listOf(
            R.drawable.conde,
            R.drawable.jurassic_parks,
            R.drawable.dragon_heart,
            R.drawable.avatar,
            R.drawable.jhon_wick_4,
            // Agrega más recursos de imágenes según sea necesario
        )

        val adapter = Horizontal_RecycledView(imageResourcesForRecyclerView)
        val adapter1 = Horizontal_RecycledView(imageResourcesForRecyclerView1)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        recyclerView1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView1.adapter = adapter1

        // Agrega más contenido y lógica aquí según tus necesidades



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}