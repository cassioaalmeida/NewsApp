package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgView = findViewById<ImageView>(R.id.image)
        Glide
            .with(this)
            .load("https://cdn.vox-cdn.com/thumbor/7YChMVyltceCz6Kv5UXXSoTpXBY=/0x146:2040x1214/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/15971132/elon_musk_tesla_3225.jpg")
            .placeholder(R.drawable.ic_no_image)
            .into(imgView);

    }

    fun OpenUrl(view: View) {
        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://seekingalpha.com/article/4413421-gsah-rumors-create-opportunity")
        )
        startActivity(i)
    }
}