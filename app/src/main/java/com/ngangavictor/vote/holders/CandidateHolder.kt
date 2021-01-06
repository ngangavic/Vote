package com.ngangavictor.vote.holders

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.vote.R

class CandidateHolder(view: View):RecyclerView.ViewHolder(view) {

    val imageViewProfile:ImageView=view.findViewById(R.id.imageViewProfile)
    val textViewFirstName:TextView=view.findViewById(R.id.textViewFirstName)
    val textViewLastName:TextView=view.findViewById(R.id.textViewLastName)
    val buttonVote:Button=view.findViewById(R.id.buttonVote)

}