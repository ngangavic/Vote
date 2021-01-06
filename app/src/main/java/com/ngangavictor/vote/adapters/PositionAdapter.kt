package com.ngangavictor.vote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavictor.vote.R
import com.ngangavictor.vote.holders.PositionHolder
import com.ngangavictor.vote.listeners.PositionListener
import com.ngangavictor.vote.models.PositionModel

class PositionAdapter(private val positionList:ArrayList<PositionModel>, val context: Context,
                      private val positionListener: PositionListener):RecyclerView.Adapter<PositionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionHolder {
        val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.row_position,parent,false)
        return PositionHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: PositionHolder, position: Int) {
        holder.textViewName.text=positionList[position].textViewName
        holder.textViewName.setOnClickListener {
positionListener.loadCandidates(positionList[position].id,positionList[position].textViewName)
        }
    }

    override fun getItemCount(): Int {
        return positionList.size
    }
}