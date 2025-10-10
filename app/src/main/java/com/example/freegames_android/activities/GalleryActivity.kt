package com.example.freegames_android.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.freegames_android.R
import com.example.freegames_android.adapters.GalleryPagerAdapter
import com.example.freegames_android.adapters.ScreenshotAdapter
import com.example.freegames_android.data.Screenshot
import com.example.freegames_android.databinding.ActivityGalleryBinding
import com.squareup.picasso.Picasso

class GalleryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCREENSHOT_INDEX = "SCREENSHOT_INDEX"
        const val EXTRA_SCREENSHOTS_ARRAY = "SCREENSHOTS_ARRAY"
    }

    lateinit var binding: ActivityGalleryBinding

    lateinit var adapter: ScreenshotAdapter
    lateinit var pagerAdapter: GalleryPagerAdapter
    lateinit var screenshots: List<Screenshot>
    var selectedPosition = 0

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

        // Get extras
        selectedPosition = intent.getIntExtra(EXTRA_SCREENSHOT_INDEX, 0)
        screenshots = intent.getStringArrayExtra(EXTRA_SCREENSHOTS_ARRAY)!!.map { Screenshot(it) }

        // ViewPager
        pagerAdapter = GalleryPagerAdapter(screenshots, selectedPosition)
        binding.galleryViewPager.adapter = pagerAdapter

        //binding.galleryViewPager.setCurrentItem(selectedPosition)

        binding.galleryViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                selectedPosition = position
                adapter.changeSelectedPosition(selectedPosition)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        //binding.galleryViewPager.setCurrentItem(selectedPosition)

        // RecyclerView
        adapter = ScreenshotAdapter(screenshots, selectedPosition) { position ->
            selectedPosition = position
            binding.galleryViewPager.setCurrentItem(selectedPosition)
            //adapter.changeSelectedPosition(selectedPosition)
        }

        binding.galleryRecyclerView.adapter = adapter
        binding.galleryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.galleryRecyclerView.scrollToPosition(selectedPosition)
    }

    fun loadImage() {
        binding.galleryViewPager.setCurrentItem(selectedPosition)
        //Picasso.get().load(screenshots[selectedPosition].image).into(binding.galleryImageView)
    }
}