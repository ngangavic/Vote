package com.ngangavictor.vote.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.vote.R

class PositionHolder(view: View):RecyclerView.ViewHolder(view) {

    val textViewName:TextView=view.findViewById(R.id.textViewName)

}