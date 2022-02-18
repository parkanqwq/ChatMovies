package com.metelev.bos.chatmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.metelev.bos.chatmovies.ui.SwitchFragment
import com.metelev.bos.chatmovies.ui.movies.MoviesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main, SwitchFragment())
                .commit()
    }
}