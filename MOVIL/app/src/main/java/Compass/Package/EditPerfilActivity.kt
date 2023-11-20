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

        auth = Firebase.auth

        val buttonActualizarPerfil = findViewById<Button>(R.id.buttonActualizarPerfil)
        val buttonCancelar = findViewById<Button>(R.id.buttonCancelar)
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)

        buttonActualizarPerfil.setOnClickListener {
            val nuevoNombre = editTextNombre.text.toString()
            val nuevoApellido = editTextApellido.text.toString()

            val user: FirebaseUser? = auth.currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName("$nuevoNombre $nuevoApellido")
                .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, PerfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                    }
                }
        }

        buttonCancelar.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
