package es.jasolgar.cityposts_kt.data.local.db.converter

import androidx.room.TypeConverter
import es.jasolgar.cityposts_kt.data.model.others.Geo

class GeoValueModelConverter {

    @TypeConverter
    fun fromGeo(geo : Geo) : String{
        return geo.lat + "/" + geo.lng
    }

    @TypeConverter
    fun toGeo(strGeo : String) : Geo{
        return Geo(strGeo.split("/")[0],strGeo.split("/")[1])
    }

}