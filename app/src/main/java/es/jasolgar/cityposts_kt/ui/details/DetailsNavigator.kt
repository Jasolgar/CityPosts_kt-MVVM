package es.jasolgar.cityposts_kt.ui.details

interface DetailsNavigator {

    fun launchMail(mail: String?)

    fun launchGeoMaps(lat: String?, lng: String?)

    fun launchPhoneCall(phone: String?)

    fun loadWebUrl(webUrl: String?)


}