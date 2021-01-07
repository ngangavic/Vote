package com.ngangavictor.vote.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ngangavictor.vote.R
import com.ngangavictor.vote.models.PositionModel

class SpinnerAdapter(var context: Context,var positionList:ArrayList<PositionModel>): BaseAdapter() {

    override fun getCount(): Int {
        return positionList.size
    }

    override fun getItem(position: Int): Any? {
        return  null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater=LayoutInflater.from(parent?.context).inflate(R.layout.spinner_layout,null)
        val textViewName:TextView=layoutInflater.findViewById(R.id.textViewName)
        textViewName.text=positionList[position].textViewName

        return layoutInflater
    }

}