package Compass.Package.ui.juegos

import Compass.Package.R
import Compass.Package.databinding.FragmentJuegosBinding
import Compass.Package.ui.Api.Api_Client.tmdbApiService
import Compass.Package.ui.Api.TmdbApiService
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private var imageViewEnCentro: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuegosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        context = requireContext()
        parentView = root.findViewById(R.id.main_layoutview)



        val apiKey = "8cfb3de7194927508a93970ddb761e47"
        val page = 2
        val call = tmdbApiService.getPopularMovies(apiKey,page)

        call.enqueue(object : Callback<TmdbApiService.MovieResponse> {
            override fun onResponse(call: Call<TmdbApiService.MovieResponse>, response: Response<TmdbApiService.MovieResponse>) {
                if (response.isSuccessful) {

                    val movies = response.body()?.results
                    getArrayData(movies ?: emptyList())
                    createCards()

                    // Hacer algo con la lista de películas
                } else {
                    // Manejar el error
                }
            }

            override fun onFailure(call: Call<TmdbApiService.MovieResponse>, t: Throwable) {
                // Manejar el fallo
            }
        })

        btnLike = root.findViewById(R.id.btnLike)
        btnRewind = root.findViewById(R.id.btnRewind)
        btnDislike = root.findViewById(R.id.btnDislike)


        setupButtonListeners()

        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)
        windowWidth = size.x
        screenCenter = windowWidth / 2
        userDataModelArrayList = ArrayList()


        return root
    }

    private fun setupButtonListeners() {
        btnLike.setOnClickListener {
            showToast("Me gusta")
        }

        btnRewind.setOnClickListener {
            showToast("Retroceder")
        }

        btnDislike.setOnClickListener {
            showToast("No me gusta")
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
        for (i in userDataModelArrayList.indices) {
            val containerView = createCardContainer(i)
            val dataModel = userDataModelArrayList[i]
            setupCardLayout(containerView, i)
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
        userIMG.setBackgroundResource(userDataModelArrayList[index].getPhoto())
        return containerView
    }

    private fun setupCardLayout(containerView: View, index: Int) {
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
                    imageViewEnCentro?.visibility = View.INVISIBLE
                }
            }
            true
        }
    }

    private fun getArrayData(movies: List<TmdbApiService.Movies>) {
        for (movie in movies) {
            val model = DataModel()
            model.setName(movie.title)
            model.setAñoEstreno(movie.release_date)
            model.setPhoto(getImageResourceForMovie(movie.poster_path))
            model.setPlataforma(getPlatformForMovie(movie.title))
            model.setClasificacion(movie.vote_average.toString())
            model.setDescripcion(movie.overview)

            // Otros campos que desees asignar al modelo de datos

            userDataModelArrayList.add(model)
        }
        userDataModelArrayList.reverse()
    }
        // Implementa tu lógica para obtener el recurso de imagen según el nombre de la película
        // Puedes utilizar un when o un mapa que asocie nombres de películas con recursos de imagen
        // Retorna la misma imagen para todas las películas en este caso
        return R.drawable.spiderman
    }

    private fun getPlatformForMovie(movieName: String): String {
        // Implementa tu lógica para obtener la plataforma según el nombre de la película
        // Retorna la misma plataforma para todas las películas en este caso
        return movieName
    }

}