package com.example.freegames_android.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freegames_android.R
import com.example.freegames_android.adapters.ScreenshotAdapter
import com.example.freegames_android.data.Screenshot
import com.example.freegames_android.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCREENSHOT_INDEX = "SCREENSHOT_INDEX"
        const val EXTRA_SCREENSHOTS_ARRAY = "SCREENSHOTS_ARRAY"
    }

    lateinit var binding: ActivityGalleryBinding

    lateinit var adapter: ScreenshotAdapter
    lateinit var screenshots: List<Screenshot>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        screenshots = intent.getStringArrayExtra(EXTRA_SCREENSHOTS_ARRAY)!!.map { Screenshot(it) }

        adapter = ScreenshotAdapter(screenshots) {

        }

        binding.galleryRecyclerView.adapter = adapter
        binding.galleryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}