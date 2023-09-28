package Compass.Package

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        // Inicializar Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Inicializar vistas
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        val buttonRegister: Button = findViewById(R.id.buttonRegister)

        // Configurar evento de clic para el botón de inicio de sesión
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Verificar si los campos de entrada están vacíos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, rellene el formulario", Toast.LENGTH_SHORT).show()
            } else {
                // Iniciar sesión con Firebase Authentication
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Inicio de sesión exitoso, mostrar mensaje de bienvenida
                            val user: FirebaseUser? = auth.currentUser
                            val welcomeMessage = "Sesión iniciada, bienvenido ${user?.email}"
                            Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show()

                            // Redirigir al usuario a InicioActivity
                            val intent = Intent(this, PrincipalActivity::class.java)
                            startActivity(intent)
                            finish() // Cerrar la actividad actual
                        } else {
                            // Error en el inicio de sesión, mostrar mensaje de error
                            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                            Log.e("PrincipalActivity", "Error en el inicio de sesión: ${task.exception?.message}")
                        }
                    }
            }
        }

        // Configurar evento de clic para el botón de registro
        buttonRegister.setOnClickListener {
            // Redirigir al usuario a la pantalla de registro (RegisterActivity)
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}




