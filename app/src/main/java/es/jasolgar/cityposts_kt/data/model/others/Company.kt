package es.jasolgar.cityposts_kt.data.model.others

data class Company (
    var companyName : String?,
    var catchPhrase : String?,
    var bs : String?
){
    constructor() : this("","","")
}