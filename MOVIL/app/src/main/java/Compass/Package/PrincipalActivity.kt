package Compass.Package

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Compass.Package.databinding.ActivityMainBinding
import Compass.Package.ui.adapter.Horizontal_RecycledView
class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView1: RecyclerView
    private lateinit var adapter: Horizontal_RecycledView
    private lateinit var adapter1: Horizontal_RecycledView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView1 = findViewById(R.id.recyclerView_1)

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

        adapter = Horizontal_RecycledView(imageResourcesForRecyclerView)
        adapter1 = Horizontal_RecycledView(imageResourcesForRecyclerView1)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        recyclerView1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView1.adapter = adapter1

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_juegos, R.id.navigation_playlists
            )
        )

        navView.setupWithNavController(navController)
    }
}
