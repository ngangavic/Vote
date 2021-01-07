package com.ngangavictor.vote

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.ngangavictor.vote.adapters.CandidateAdapter
import com.ngangavictor.vote.adapters.PositionAdapter
import com.ngangavictor.vote.adapters.SpinnerAdapter
import com.ngangavictor.vote.databinding.ActivityVoteBinding
import com.ngangavictor.vote.listeners.PositionListener
import com.ngangavictor.vote.models.CandidateModel
import com.ngangavictor.vote.models.PositionModel
import org.json.JSONObject

class VoteActivity : AppCompatActivity(), PositionListener {

    lateinit var binding: ActivityVoteBinding

    lateinit var queue: RequestQueue

    lateinit var alert: AlertDialog

    lateinit var positionList: MutableList<PositionModel>

    lateinit var positionAdapter: PositionAdapter

    lateinit var candidateList: MutableList<CandidateModel>

    lateinit var candidateAdapter: CandidateAdapter

    lateinit var sharedPrefs: SharedPrefs

    lateinit var spinnerPositionList: MutableList<PositionModel>

    lateinit var spinnerPositionAdapter: SpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vote)

        queue = Volley.newRequestQueue(this)
        positionList = ArrayList()
        candidateList = ArrayList()
        spinnerPositionList = ArrayList()

        sharedPrefs = SharedPrefs(this)

        binding.recyclerViewPositions.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPositions.setHasFixedSize(true)

        binding.recyclerViewVote.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewVote.setHasFixedSize(true)

        loadPositions()
    }

    private fun positionApplication() {
        val layout = layoutInflater.inflate(R.layout.dialog_application, null)
        val spinnerPosition = layout.findViewById<Spinner>(R.id.spinnerPosition)
        val populateSpinnerRequest = StringRequest(
            Request.Method.POST, Utils.getPositions,
            { response ->
                spinnerPositionList.clear()
                val jsonResponse = JSONObject(response)
                when (jsonResponse.getString("report")) {
                    "0" -> {
                        val jsonArray = jsonResponse.getJSONArray("message")

                        for (i in 0 until jsonArray.length()) {
                            spinnerPositionList.add(
                                PositionModel(
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getString("position")
                                )
                            )
                        }
                        spinnerPositionAdapter =
                            SpinnerAdapter(this, spinnerPositionList as ArrayList<PositionModel>)
                        spinnerPosition.adapter = spinnerPositionAdapter

                    }
                    "1" -> {
                        alert.cancel()
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            jsonResponse.getString("message"),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            },
            {
                alert.cancel()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error! Failed to get positions",
                    Snackbar.LENGTH_LONG
                ).show()
            })

        queue.add(populateSpinnerRequest)

        val alertPositionApplication = AlertDialog.Builder(this)
        alertPositionApplication.setCancelable(false)
        alertPositionApplication.setView(layout)
        layout.findViewById<Button>(R.id.buttonCancel).setOnClickListener { alert.cancel() }
        layout.findViewById<Button>(R.id.buttonApply).setOnClickListener {
            val applicationRequest=object :StringRequest(
                Method.POST, Utils.apply,
                { response ->
                    alert.cancel()
                    val jsonResponse = JSONObject(response)
                    when (jsonResponse.getString("report")) {
                        "0" -> {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                jsonResponse.getString("message"),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        "1" -> {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                jsonResponse.getString("message"),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                {
                    alert.cancel()
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Error! Failed to get positions",
                        Snackbar.LENGTH_LONG
                    ).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val param = HashMap<String, String>()
                    param["position_id"] = spinnerPositionList[spinnerPosition.selectedItemPosition].id
                    param["voter_id"] = sharedPrefs.readPref("voter_id")
                    return param
                }
            }

            queue.add(applicationRequest)
        }

        alert = alertPositionApplication.create()
        alert.show()
    }

    override fun vote(
        candidateName: String,
        candidateId: String,
        postName: String,
        positionId: String
    ) {

        val alertVote = AlertDialog.Builder(this)
        alertVote.setCancelable(false)
        alertVote.setTitle("Confirmation")
        alertVote.setMessage("Do you want to vote " + candidateName + " for " + postName + "?")
        alertVote.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        alertVote.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->
            val voteRequest = object : StringRequest(Method.POST, Utils.vote,
                { response ->
                    alert.cancel()
                    val jsonResponse = JSONObject(response)
                    when (jsonResponse.getString("report")) {
                        "0" -> {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                jsonResponse.getString("message"),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        "1" -> {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                jsonResponse.getString("message"),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                {
                    alert.cancel()
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Error!, Try again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val param = HashMap<String, String>()
                    param["candidate_id"] = candidateId
                    param["position_id"] = positionId
                    param["voter_id"] = sharedPrefs.readPref("voter_id")
                    return param
                }
            }

            queue.add(voteRequest)
        })

        alert = alertVote.create()
        alertVote.show()
    }

    override fun loadCandidates(position: String, name: String) {
        loadAlert()
        val loadCandidatesRequest = object : StringRequest(Method.POST, Utils.loadCandidates,
            { response ->
                alert.cancel()
                val jsonResponse = JSONObject(response)
                when (jsonResponse.getString("report")) {
                    "0" -> {
                        val jsonArray = jsonResponse.getJSONArray("message")
                        for (i in 0 until jsonArray.length()) {
                            candidateList.add(
                                CandidateModel(
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getString("position"),
                                    jsonArray.getJSONObject(i).getString("fname"),
                                    jsonArray.getJSONObject(i).getString("lname"),
                                    jsonArray.getJSONObject(i).getString("photo"),
                                    name
                                )
                            )
                        }

                        candidateAdapter =
                            CandidateAdapter(candidateList as ArrayList<CandidateModel>, this, this)
                        binding.recyclerViewVote.adapter = candidateAdapter
                        binding.recyclerViewVote.visibility = View.VISIBLE
                        binding.recyclerViewPositions.visibility = View.GONE

                    }
                    "1" -> {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            jsonResponse.getString("message"),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            },
            {
                alert.cancel()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error!, Try again",
                    Snackbar.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val param = HashMap<String, String>()
                param["position"] = position
                return param
            }
        }
        queue.add(loadCandidatesRequest)
    }

    private fun loadPositions() {
        loadAlert()
        val loadPositionRequest = StringRequest(Request.Method.POST, Utils.loadPositions,
            { response ->
                alert.cancel()
                val jsonResponse = JSONObject(response)
                when (jsonResponse.getString("report")) {
                    "1" -> {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            jsonResponse.getString("message"),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    "0" -> {
                        val jsonArray = jsonResponse.getJSONArray("message")

                        for (i in 0 until jsonArray.length()) {
                            positionList.add(
                                PositionModel(
                                    jsonArray.getJSONObject(i).getString("id"),
                                    jsonArray.getJSONObject(i).getString("name")
                                )
                            )
                        }

                        positionAdapter =
                            PositionAdapter(positionList as ArrayList<PositionModel>, this, this)
                        binding.recyclerViewPositions.adapter = positionAdapter
                    }
                }
            },
            {
                alert.cancel()
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error!, Try again",
                    Snackbar.LENGTH_LONG
                ).show()
            })

        queue.add(loadPositionRequest)
    }


    private fun loadAlert() {
        val layout = layoutInflater.inflate(R.layout.alert_loading, null)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(layout)
        alert = alertDialog.create()
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_application -> {
                positionApplication()
            }
            R.id.action_vote -> {
                startActivity(Intent(this, VoteActivity::class.java))
                finish()
            }
            R.id.action_view_results -> {

            }
            R.id.action_logout -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return false
    }
}