package Compass.Package.ui.juegos

import Compass.Package.R
import Compass.Package.databinding.FragmentJuegosBinding
import Compass.Package.databinding.FragmentPlaylistsBinding
import Compass.Package.ui.home.HomeFragment
import Compass.Package.ui.juegos.Data
import Compass.Package.ui.juegos.DataModel
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.util.*


class JuegosFragment : Fragment() {

    private var _binding: FragmentJuegosBinding? = null
    private val binding get() = _binding!!


    private var windowWidth = 0
    private var screenCenter = 0
    private var xCord = 0
    private var yCord = 0
    private var x = 0
    private var y = 0
    private var likes = 0
    private var alphaValue = 0f
    private lateinit var context: Context
    private lateinit var parentView: RelativeLayout
    private lateinit var userDataModelArrayList: ArrayList<DataModel>

    private var btnLike: Button? = null
    private var btnRewind: Button? = null
    private var btnDislike: Button? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJuegosBinding.inflate(inflater, container, false)
        val root: View = binding.root
        context = requireContext()
        parentView = root.findViewById(R.id.main_layoutview)

        // Encuentra los botones por su ID
        //val btnLike = root.findViewById<Button>(R.id.btnLike)
        //val btnRewind = root.findViewById<Button>(R.id.btnRewind)
        //val btnDislike = root.findViewById<Button>(R.id.btnDislike)


        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)
        windowWidth = size.x
        screenCenter = windowWidth / 2
        userDataModelArrayList = ArrayList()

        getArrayData()
        createCards()

        return root
    }
    private fun setupLikeButton() {
        btnLike?.setOnClickListener {
            showToast("Me gusta")
        }
    }

    private fun setupRewindButton() {
        btnRewind?.setOnClickListener {
            showToast("Rewind")
        }
    }

    private fun setupDislikeButton() {
        btnDislike?.setOnClickListener {
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
            val dataModel = userDataModelArrayList[i] // Obtener el objeto DataModel correspondiente
            setupCardLayout(containerView, i)
            setupTextViews(containerView, dataModel) // Pasar el objeto DataModel a la función
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
        // Configura el diseño de la tarjeta
        // ...
    }

    private fun setupTextViews(containerView: View, dataModel: DataModel) {
        val tvName = containerView.findViewById<TextView>(R.id.tvName)
        val plataformaRec = containerView.findViewById<TextView>(R.id.plataforma_rec)
        val añoestreno_rec = containerView.findViewById<TextView>(R.id.añoestreno_rec)
        val clasificacionRec = containerView.findViewById<TextView>(R.id.clasificacion_rec)
        val descripcionRec = containerView.findViewById<TextView>(R.id.descripcion_rec)

        // Asigna los valores de la película a los TextViews
        tvName.text = dataModel.getName()
        plataformaRec.text = dataModel.getPlataforma()
        añoestreno_rec.text = dataModel.getAñoEstreno()
        clasificacionRec.text = dataModel.getClasificacion() + "/10"
        descripcionRec.text = dataModel.getDescripcion()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupTouchListeners(containerView: View) {

        containerView.setOnTouchListener { _, event ->
            xCord = event.rawX.toInt()
            yCord = event.rawY.toInt()
            containerView.x = 0f
            containerView.y = 0f

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.x.toInt()
                    y = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    xCord = event.rawX.toInt()
                    yCord = event.rawY.toInt()
                    containerView.x = xCord - x.toFloat()
                    containerView.y = yCord - y.toFloat()

                    if (xCord >= screenCenter) {
                        containerView.rotation = ((xCord - screenCenter) * (Math.PI / 32)).toFloat()
                        if (xCord > screenCenter + screenCenter / 2) {
                            likes = if (xCord > windowWidth - screenCenter / 4) {
                                2
                            } else {
                                0
                            }
                        } else {
                            likes = 0
                        }
                    } else {
                        containerView.rotation = ((xCord - screenCenter) * (Math.PI / 32)).toFloat()
                        if (xCord < screenCenter / 2) {
                            likes = if (xCord < screenCenter / 4) {
                                1
                            } else {
                                0
                            }
                        } else {
                            likes = 0
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    xCord = event.rawX.toInt()
                    yCord = event.rawY.toInt()
                    Log.e("X Point", "$xCord , Y $yCord")

                    if (likes == 0) {
                        Toast.makeText(context, "Nada", Toast.LENGTH_SHORT).show()
                        containerView.x = 0f
                        containerView.y = 0f
                        containerView.rotation = 0f
                    } else if (likes == 1) {

                        Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()
                        Log.e("Action: ", "No")

                        parentView.removeView(containerView)
                    } else if (likes == 2) {

                        Toast.makeText(context, "Si", Toast.LENGTH_SHORT).show()
                        Log.e("Action: ", "Si")

                        parentView.removeView(containerView)
                    }
                }
            }
            true
        }
    }

    private fun getArrayData() {
        val data = listOf(
            Data("Covenant","2017","5 M", R.drawable.covenant, "Prime Video", "6.5", "En esta secuela de 'Prometheus', la tripulación de la nave Covenant se dirige a un planeta remoto que creen que es un paraíso. Sin embargo, pronto descubren que el planeta está habitado por una raza alienígena hostil."),
            Data("Conde", "2023","6 M", R.drawable.conde, "Netflix", "2", "Una joven mujer se enamora de un apuesto conde, pero su amor está prohibido."),
            Data("Titanic", "1997","7 M", R.drawable.titanic, "Disney+", "3", "Un joven artista de clase baja y una rica heredera se enamoran a bordo del transatlántico Titanic, pero su amor se ve truncado por un trágico accidente."),
            Data("Dragonheart","1994", "5 M", R.drawable.dragon_heart, "HBO MAX", "4", "Un caballero medieval se une a un dragón para luchar contra un malvado rey."),
            Data("Elemental","2023", "3 M", R.drawable.elemental, "Disney+", "5", "Dos personas de dos mundos muy diferentes se unen para salvar a la ciudad de la destrucción."),
            Data("John Wick: Chapter 4", "2023","7.7 M", R.drawable.jhon_wick_4, "Peacock", "6", "John Wick se enfrenta a un nuevo enemigo en su búsqueda de venganza."),
            Data("Spider-Man: Across the Spider-Verse","2023", "9 M", R.drawable.spiderman, "Disney+", "7", "Miles Morales y Gwen Stacy viajan a través del multiverso para enfrentarse a un nuevo enemigo.")

        )

        for (item in data) {
            val model = DataModel()
            model.setName(item.titulo)
            model.setTotalLikes(item.likes)
            model.setAñoEstreno(item.añoEstreno)
            model.setPhoto(item.photo)
            model.setPlataforma(item.plataforma)
            model.setClasificacion(item.clasificacion)
            model.setDescripcion(item.descripcion)
            userDataModelArrayList.add(model)
        }

        userDataModelArrayList.reverse()
    }
}