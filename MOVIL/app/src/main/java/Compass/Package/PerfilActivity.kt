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

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Obtener referencias a los elementos de la interfaz de usuario
        val imageViewPerfil = findViewById<ImageView>(R.id.imageViewPerfil)
        val textViewNombre = findViewById<TextView>(R.id.textViewNombre)
        val textViewApellido = findViewById<TextView>(R.id.textViewApellido)
        val textViewCorreo = findViewById<TextView>(R.id.textViewCorreo)
        val buttonEditarPerfil = findViewById<Button>(R.id.buttonEditarPerfil)
        val buttonVolverInicio = findViewById<Button>(R.id.buttonVolverInicio)
        val buttonCambiarFotoPerfil = findViewById<Button>(R.id.buttonCambiarFotoPerfil)

        // Cargar la foto de perfil predeterminada
        imageViewPerfil.setImageResource(R.drawable.default_profile_image)

        // Obtener datos del usuario desde Firebase
        val user: FirebaseUser? = auth.currentUser
        val nombreCompleto = user?.displayName
        val correo = user?.email

        // Dividir el nombre completo en nombre y apellido
        val partesNombre = nombreCompleto?.split(" ")
        val primerNombre = partesNombre?.get(0) ?: "Añadir Nombre"
        val primerApellido = partesNombre?.get(1) ?: "Añadir Apellido"

        // Actualizar los campos de texto con los datos del usuario
        textViewNombre.text = "Nombre: $primerNombre"
        textViewApellido.text = "Apellido: $primerApellido"
        textViewCorreo.text = "Correo Electrónico: " + (correo ?: "")

        // Configurar evento de clic para el botón "Editar Perfil"
        buttonEditarPerfil.setOnClickListener {
            val intent = Intent(this, EditPerfilActivity::class.java)
            startActivity(intent)
        }

        // Configurar evento de clic para el botón "Volver al Inicio"
        buttonVolverInicio.setOnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la actividad actual
        }

        // Configurar evento de clic para el botón "Cambiar Foto de Perfil"
        buttonCambiarFotoPerfil.setOnClickListener {
            // Agregar la lógica para cambiar la foto de perfil aquí
        }
    }
}
