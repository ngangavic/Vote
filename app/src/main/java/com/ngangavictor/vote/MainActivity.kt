package com.ngangavictor.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.android.volley.toolbox.StringRequest
import com.ngangavictor.vote.databinding.ActivityMainBinding
import java.lang.StringBuilder
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

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
            val loginRequest=object : StringRequest(Method.POST,"",{response->},{error->}){
                override fun getParams(): MutableMap<String, String> {
                    return super.getParams()
                }
            }

        }

    }
}