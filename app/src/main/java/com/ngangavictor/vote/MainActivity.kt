package com.ngangavictor.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.ngangavictor.vote.databinding.ActivityMainBinding
import org.json.JSONObject
import java.lang.StringBuilder
import java.lang.reflect.Method
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()

        queue= Volley.newRequestQueue(this)

        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val voterId=binding.editTextVoteId.text.toString()
        val password=binding.editTextPassword.text.toString()

        if (TextUtils.isEmpty(voterId)){
            binding.editTextVoteId.requestFocus()
            binding.editTextVoteId.error="Required"
        }else if (TextUtils.isEmpty(password)){
            binding.editTextPassword.requestFocus()
            binding.editTextPassword.error="Required"
        }else{
            //login request
            val loginRequest=object : StringRequest(Method.POST,Utils.login,
                {response->
                    val jsonResponse=JSONObject(response)

                    when(jsonResponse.getString("report")){
                        "0"->{
                            Snackbar.make(findViewById(android.R.id.content),jsonResponse.getString("message"),Snackbar.LENGTH_LONG).show()
                        }
                        "1"->{
                            Snackbar.make(findViewById(android.R.id.content),jsonResponse.getString("message"),Snackbar.LENGTH_LONG).show()
                        }
                    }
                },
                {
                    Snackbar.make(findViewById(android.R.id.content),"Error!, Try again",Snackbar.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val param = HashMap<String, String>()
                    param["voter_id"] = voterId
                    param["password"] = password
                    return param
                }
            }
            queue.add(loginRequest)

        }

    }
}