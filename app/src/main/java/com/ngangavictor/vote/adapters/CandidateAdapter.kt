package com.ngangavictor.vote.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ngangavictor.vote.R
import com.ngangavictor.vote.holders.CandidateHolder
import com.ngangavictor.vote.listeners.PositionListener
import com.ngangavictor.vote.models.CandidateModel
import com.squareup.picasso.Picasso

class CandidateAdapter(val candidateList: ArrayList<CandidateModel>, val context: Context,val positionListener: PositionListener) :
    RecyclerView.Adapter<CandidateHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.row_candidate, parent, false)
        return CandidateHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: CandidateHolder, position: Int) {
        val ac = context as Activity
        holder.textViewFirstName.text = candidateList[position].fname+" "+candidateList[position].lname
        holder.textViewLastName.text = "for "+candidateList[position].name+" position"
        holder.buttonVote.setOnClickListener {
            positionListener.vote(candidateList[position].name,candidateList[position].id,candidateList[position].name,candidateList[position].position)
        }
        Picasso.get()
            .load("http://192.168.0.101/votesystem/images/" + candidateList[position].photo)
            .into(holder.imageViewProfile)

    }

    override fun getItemCount(): Int {
        return candidateList.size
    }
}