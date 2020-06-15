package es.jasolgar.cityposts_kt.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import es.jasolgar.cityposts_kt.di.PreferenceInfo
import javax.inject.Inject


class AppPreferencesHelper @Inject constructor( context: Context,  @PreferenceInfo prefFileName: String): PreferencesHelper {

    private var mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

}