package com.gurbanivideo.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.gurbanivideo.HukumnamaWeb
import com.gurbanivideo.MainActivity
import com.gurbanivideo.R
import com.gurbanivideo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        binding.cardKatha.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("video", "Katha")
            startActivity(intent)
        }
        binding.cardKirtan.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("video", "Kirtan")
            startActivity(intent)
        }
        binding.hukumnama.setOnClickListener { startActivity(Intent(this, HukumnamaWeb::class.java)) }
        binding.cardMoreApps.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Manpreet+Singh+Bhatia")
                )
            ) }
        binding.cardViewShare.setOnClickListener{
            val i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
            i.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=" + this@HomeActivity.packageName
            )
            startActivity(Intent.createChooser(i, "Share URL"))}
    }
}


