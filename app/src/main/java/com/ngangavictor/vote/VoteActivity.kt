package com.ngangavictor.vote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ngangavictor.vote.databinding.ActivityMainBinding

class VoteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_application->{

            }
            R.id.action_vote->{
                startActivity(Intent(this,VoteActivity::class.java))
                finish()
            }
            R.id.action_view_results->{

            }
            R.id.action_logout->{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        return false
    }
}