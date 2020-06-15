package es.jasolgar.cityposts_kt.ui.main

interface MainNavigator {

    fun loadPostFragment()

    fun handleError(throwable: Throwable)
}