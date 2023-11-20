package Compass.Package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PerfilActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        auth = FirebaseAuth.getInstance()

        val imageViewPerfil = findViewById<ImageView>(R.id.imageViewPerfil)
        val textViewNombre = findViewById<TextView>(R.id.textViewNombre)
        val textViewApellido = findViewById<TextView>(R.id.textViewApellido)
        val textViewCorreo = findViewById<TextView>(R.id.textViewCorreo)
        val buttonEditarPerfil = findViewById<Button>(R.id.buttonEditarPerfil)
        val buttonVolverInicio = findViewById<Button>(R.id.buttonVolverInicio)
        val buttonCambiarFotoPerfil = findViewById<Button>(R.id.buttonCambiarFotoPerfil)

        imageViewPerfil.setImageResource(R.drawable.default_profile_image)

        val user: FirebaseUser? = auth.currentUser
        val nombreCompleto = user?.displayName
        val correo = user?.email

        val partesNombre = nombreCompleto?.split(" ")
        val primerNombre = partesNombre?.get(0) ?: "Añadir Nombre"
        val primerApellido = partesNombre?.get(1) ?: "Añadir Apellido"

        textViewNombre.text = "Nombre: $primerNombre"
        textViewApellido.text = "Apellido: $primerApellido"
        textViewCorreo.text = "Correo Electrónico: " + (correo ?: "")

        buttonEditarPerfil.setOnClickListener {
            val intent = Intent(this, EditPerfilActivity::class.java)
            startActivity(intent)
        }

        buttonVolverInicio.setOnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonCambiarFotoPerfil.setOnClickListener {
        }
    }
}
