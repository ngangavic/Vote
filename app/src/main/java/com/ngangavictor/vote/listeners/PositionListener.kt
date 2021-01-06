package com.ngangavictor.vote.listeners

interface PositionListener {

    fun loadCandidates(position:String,name:String)

    fun vote(candidateName: String,candidateId: String,postName:String,positionId: String)

}