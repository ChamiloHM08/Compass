package Compass.Package

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController: NavController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        val btnVerPerfil: Button = findViewById(R.id.btnVerPerfil)
        btnVerPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            Log.d("PrincipalActivity", "Abrir PerfilActivity") // Registro para depuración
        }

        val btnCerrarSesion: Button = findViewById(R.id.btnIniciarSesion)
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Log.d("PrincipalActivity", "Cerrar Sesión") // Registro para depuración
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_view_profile -> {
                val intent = Intent(this, PerfilActivity::class.java)
                startActivity(intent)
                Log.d("PrincipalActivity", "Abrir PerfilActivity") // Registro para depuración
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
