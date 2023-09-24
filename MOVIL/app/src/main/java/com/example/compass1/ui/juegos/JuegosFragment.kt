package com.example.compass1.ui.juegos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.compass1.databinding.FragmentJuegosBinding

class JuegosFragment : Fragment() {

    private var _binding: FragmentJuegosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val juegosViewModel =
            ViewModelProvider(this).get(JuegosViewModel::class.java)

        _binding = FragmentJuegosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textJuegos
        juegosViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
