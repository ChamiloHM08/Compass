package Compass.Package.ui.juegos

import Compass.Package.R
import Compass.Package.databinding.FragmentJuegosBinding
import Compass.Package.ui.Api.Api_Client.tmdbApiService
import Compass.Package.ui.Api.TmdbApiService
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*


class JuegosFragment : Fragment() {

    private var _binding: FragmentJuegosBinding? = null
    private val binding get() = _binding!!



    private lateinit var context: Context
    private lateinit var parentView: RelativeLayout
    private lateinit var userDataModelArrayList: ArrayList<DataModel>

    private lateinit var btnLike: Button
    private lateinit var btnRewind: Button
    private lateinit var btnDislike: Button

    private var windowWidth = 0
    private var screenCenter = 0
    private var xCord = 0
    private var x = 0
    private var likes = 0
    private val apiKey = "8cfb3de7194927508a93970ddb761e47"
    private val page = 4
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuegosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        context = requireContext()
        parentView = root.findViewById(R.id.main_layoutview)




        val call = tmdbApiService.getPopularMovies(apiKey,page)
        val db = FirebaseFirestore.getInstance()


        call.enqueue(object : Callback<TmdbApiService.MovieResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<TmdbApiService.MovieResponse>, response: Response<TmdbApiService.MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    // Inicializar Firebase Authentication
                    auth = FirebaseAuth.getInstance()

                    getArrayData(movies ?: emptyList())
                    createCards()

                } else {
                    //proximamente
                }
            }

            override fun onFailure(call: Call<TmdbApiService.MovieResponse>, t: Throwable) {
                //proximamente
            }
        })

        btnLike = root.findViewById(R.id.btnLike)
        btnRewind = root.findViewById(R.id.btnRewind)
        btnDislike = root.findViewById(R.id.btnDislike)

        // Asignar la función onButtonClick como OnClickListener a los botones
        btnLike.setOnClickListener { onButtonClick(2) }
        btnRewind.setOnClickListener { onButtonClick(3) }
        btnDislike.setOnClickListener { onButtonClick(1) }

        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)
        windowWidth = size.x
        screenCenter = windowWidth / 2
        userDataModelArrayList = ArrayList()


        return root
    }

    private fun onButtonClick(likes: Int) {
        val currentCardIndex = parentView.childCount - 1

        if (currentCardIndex >= 0) {
            val currentCard = parentView.getChildAt(currentCardIndex)

            when (likes) {
                3 -> {



                }
                1 -> {
                    showToast("No")
                    parentView.removeView(currentCard)
                }
                2 -> {
                    showToast("Sí")
                    // Obtener el título de la película actual
                    val currentMovieTitle = userDataModelArrayList[currentCardIndex].getName()

                    // Crear una nueva colección en Firestore y guardar el título
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        val collectionRef = db.collection("usuarios").document(userId).collection("peliculas_favoritas")

                        val data = hashMapOf(
                            "titulo" to currentMovieTitle
                        )

                        collectionRef.add(data)
                            .addOnSuccessListener {
                                showToast("Película guardada en favoritos")
                            }
                            .addOnFailureListener {
                                showToast("Error al guardar la película")
                            }
                    }
                    parentView.removeView(currentCard)
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createCards() {
        for ((index, dataModel) in userDataModelArrayList.withIndex()) {
            val containerView = createCardContainer(index)
            setupTextViews(containerView, dataModel)
            setupTouchListeners(containerView)
            parentView.addView(containerView)
        }
    }


    private fun createCardContainer(index: Int): View {
        val containerView = LayoutInflater.from(context).inflate(R.layout.card_view, null)
        val layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
        )
        containerView.layoutParams = layoutParams
        containerView.tag = index
        val userIMG = containerView.findViewById<ImageView>(R.id.userIMG)

        Glide.with(context)
            .load(userDataModelArrayList[index].getPhoto())
            .into(userIMG)

        return containerView
    }

    private fun setupTextViews(containerView: View, dataModel: DataModel) {
        val tvName = containerView.findViewById<TextView>(R.id.tvName)
        val plataformaRec = containerView.findViewById<TextView>(R.id.plataforma_rec)
        val añoestreno_rec = containerView.findViewById<TextView>(R.id.añoestreno_rec)
        val clasificacionRec = containerView.findViewById<TextView>(R.id.clasificacion_rec)
        val descripcionRec = containerView.findViewById<TextView>(R.id.descripcion_rec)

        tvName.text = dataModel.getName()
        plataformaRec.text = dataModel.getPlataforma()
        añoestreno_rec.text = dataModel.getAñoEstreno()
        clasificacionRec.text = dataModel.getClasificacion() + "/10"
        descripcionRec.text = dataModel.getDescripcion()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTouchListeners(containerView: View) {
        containerView.setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.x.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    xCord = event.rawX.toInt()
                    containerView.x = xCord - x.toFloat()
                    if (xCord >= screenCenter) {
                        containerView.rotation = ((xCord - screenCenter) * (Math.PI / 32)).toFloat()
                        likes = if (xCord > windowWidth - screenCenter / 4) {
                            2
                        } else {
                            0
                        }
                    } else {
                        containerView.rotation = ((xCord - screenCenter) * (Math.PI / 32)).toFloat()
                        likes = if (xCord < screenCenter / 4) {
                            1
                        } else {
                            0
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (likes == 0) {
                        Toast.makeText(context, "Nada", Toast.LENGTH_SHORT).show()
                        containerView.x = 0f
                        containerView.rotation = 0f
                    } else if (likes == 1) {
                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                        parentView.removeView(containerView)
                    } else if (likes == 2) {
                        Toast.makeText(context, "Sí", Toast.LENGTH_SHORT).show()
                        parentView.removeView(containerView)
                    }
                }
            }
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getArrayData(movies: List<TmdbApiService.Movies>) {
        val moviesToProcess = movies.take(7)
        for (movie in moviesToProcess) {
            val model = DataModel()
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val year = YearMonth.from(LocalDate.parse(movie.release_date, inputFormat)).year

            model.setAñoEstreno(year.toString())
            model.setName(movie.title)
            model.setPhoto(getImageResourceForMovie(movie.poster_path))
            model.setPlataforma(getPlatformForMovie(movie.id))
            model.setClasificacion(String.format("%.1f", movie.vote_average))
            model.setDescripcion(movie.overview)

            userDataModelArrayList.add(model)
        }
        userDataModelArrayList.reverse()
    }

    private fun getImageResourceForMovie(posterPath: String): String {
        val baseUrl = "https://image.tmdb.org/t/p/"
        val posterSize = "w500"
        return baseUrl + posterSize + posterPath
    }
    private fun getPlatformForMovie(movieId: Int): String {
        // Lista de plataformas de streaming
        val plataformas = listOf("Netflix", "Amazon Prime Video", "Hulu", "Disney+", "HBO Max", "Apple TV+")

        // Seleccionar una plataforma al azar
        val plataformaSeleccionada = plataformas.random()

        // Puedes realizar acciones adicionales aquí, como guardar la plataforma en una base de datos

        return plataformaSeleccionada
    }

}