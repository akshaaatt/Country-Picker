package com.aemerse.picker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aemerse.picker.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
        showData()
    }

    private fun showData() {
        val bundle = intent.extras
        if (bundle != null) {
            binding.selectedCountryNameTextView.text = bundle.getString(BUNDLE_KEY_COUNTRY_NAME)
            binding.selectedCountryIsoTextView.text = bundle.getString(BUNDLE_KEY_COUNTRY_ISO)
            binding.selectedCountryDialCodeTextView.text = bundle.getString(BUNDLE_KEY_COUNTRY_DIAL_CODE)
            binding.selectedCountryCurrency.text = bundle.getString(BUNDLE_KEY_COUNTRY_CURRENCY)
            binding.selectedCountryFlagImageView.setImageResource(bundle.getInt(BUNDLE_KEY_COUNTRY_FLAG_IMAGE))
        }
    }

    private fun initialize() {
        binding.backButton.setOnClickListener { finish() }
    }

    companion object {
        const val BUNDLE_KEY_COUNTRY_NAME = "country_name"
        const val BUNDLE_KEY_COUNTRY_ISO = "country_iso"
        const val BUNDLE_KEY_COUNTRY_DIAL_CODE = "dial_code"
        const val BUNDLE_KEY_COUNTRY_CURRENCY = "currency"
        const val BUNDLE_KEY_COUNTRY_FLAG_IMAGE = "flag_image"
    }
}