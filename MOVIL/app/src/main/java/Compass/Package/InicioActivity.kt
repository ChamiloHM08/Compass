package Compass.Package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class InicioActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Configurar evento de clic para el botón de Cerrar Sesión
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            // Cerrar la sesión del usuario
            auth.signOut()

            // Redirigir al usuario a PrincipalActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la actividad actual
        }
    }
}


