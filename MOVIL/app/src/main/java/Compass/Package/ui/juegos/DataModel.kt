package Compass.Package.ui.juegos

import java.io.Serializable

class DataModel : Serializable {
    private var titulo: String? = null
    private var totalLikes: String? = null
    private var photo: String? = null
    private var añoEstreno : String?=null
    private var plataforma: String? = null
    private var clasificacion: String? = null
    private var descripcion: String? = null

    fun getName(): String? {
        return titulo
    }
    fun setAñoEstreno(año: String?) {
        añoEstreno = año
    }

    fun getAñoEstreno(): String? {
        return añoEstreno
    }


    fun setName(name: String?) {
        this.titulo = name
    }

    fun getTotalLikes(): String? {
        return totalLikes
    }

    fun setTotalLikes(likes: String) {
        totalLikes = likes
    }

    fun getPhoto(): Int {
        return photo
    }

    fun setPhoto(photo: Int) {
        this.photo = photo
    }

    fun getPlataforma(): String? {
        return plataforma
    }

    fun setPlataforma(plataforma: String?) {
        this.plataforma = plataforma
    }

    fun getClasificacion(): String? {
        return clasificacion
    }

    fun setClasificacion(clasificacion: String?) {
        this.clasificacion = clasificacion
    }

    fun getDescripcion(): String? {
        return descripcion
    }

    fun setDescripcion(descripcion: String?) {
        this.descripcion = descripcion
    }
}
