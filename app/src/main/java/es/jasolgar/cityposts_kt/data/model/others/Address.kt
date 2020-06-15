package es.jasolgar.cityposts_kt.data.model.others

data class Address (
    var street : String?,
    var suite : String?,
    var city : String?,
    var zipCode : String?,
    var geo : Geo?
)