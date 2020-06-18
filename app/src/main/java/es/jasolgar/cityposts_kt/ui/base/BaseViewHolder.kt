package es.jasolgar.cityposts_kt.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

   abstract  fun onBind(position : Int)

}