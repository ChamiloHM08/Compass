package Compass.Package

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EditPerfilActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        // Inicializar Firebase Authentication
        auth = Firebase.auth

        // Obtener referencias a los elementos de la interfaz de usuario
        val buttonActualizarPerfil = findViewById<Button>(R.id.buttonActualizarPerfil)
        val buttonCancelar = findViewById<Button>(R.id.buttonCancelar) // Botón "Cancelar"
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)

        // Configurar evento de clic para el botón "Actualizar Perfil"
        buttonActualizarPerfil.setOnClickListener {
            // Obtener los nuevos valores de nombre y apellido
            val nuevoNombre = editTextNombre.text.toString()
            val nuevoApellido = editTextApellido.text.toString()

            // Actualizar el perfil del usuario en Firebase
            val user: FirebaseUser? = auth.currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName("$nuevoNombre $nuevoApellido") // Incluir el apellido en el nuevo nombre
                .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Los datos del perfil se actualizaron con éxito
                        val intent = Intent(this, PerfilActivity::class.java)
                        startActivity(intent)
                        finish() // Cerrar la actividad actual
                    } else {
                        // Ocurrió un error al actualizar el perfil
                        // Maneja el error de acuerdo a tus necesidades
                    }
                }
        }

        // Configurar evento de clic para el botón "Cancelar"
        buttonCancelar.setOnClickListener {
            // Redirige al usuario a la actividad PerfilActivity
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual (EditarPerfilActivity)
        }
    }
}
