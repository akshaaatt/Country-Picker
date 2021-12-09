package com.aemerse.picker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aemerse.countrypicker.Country
import com.aemerse.countrypicker.CountryPicker
import com.aemerse.countrypicker.listeners.OnCountryPickerListener
import com.aemerse.picker.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), OnCountryPickerListener {
    private lateinit var countryPicker: CountryPicker
    private var sortBy = CountryPicker.SORT_BY_NONE
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

    private fun setListener() {
        binding.byNameButton.setOnClickListener { findByName() }
        binding.bySimButton.setOnClickListener { findBySim() }
        binding.byLocalButton.setOnClickListener { findByLocale() }
        binding.byIsoButton.setOnClickListener { findByIson() }
        binding.sortByRadioGroup.setOnCheckedChangeListener { radioGroup, id ->
            sortBy = when (id) {
                R.id.none_radio_button -> CountryPicker.SORT_BY_NONE
                R.id.name_radio_button -> CountryPicker.SORT_BY_NAME
                R.id.dial_code_radio_button -> CountryPicker.SORT_BY_DIAL_CODE
                R.id.iso_radio_button -> CountryPicker.SORT_BY_ISO
                else -> CountryPicker.SORT_BY_NONE
            }
        }
        binding.countryPickerButton.setOnClickListener { showPicker() }
    }

    private fun findByIson() {
        countryPicker = CountryPicker.Builder().with(this@MainActivity)
                .listener(this@MainActivity).build()
        val builder = AlertDialog.Builder(this, R.style.Base_Theme_MaterialComponents_Dialog_Alert)
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setTitle("Country Code")
        builder.setView(input)
        builder.setCancelable(false)
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            val country = countryPicker.getCountryByISO(input.text.toString())
            if (country == null) {
                Toast.makeText(this@MainActivity, "Country not found", Toast.LENGTH_SHORT).show()
            } else {
                showResultActivity(country)
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun findByLocale() {
        countryPicker = CountryPicker.Builder().with(this@MainActivity)
                .listener(this@MainActivity).build()
        val country = countryPicker.getCountryByLocale(Locale.getDefault())
        if (country == null) {
            Toast.makeText(this@MainActivity, "Country not found", Toast.LENGTH_SHORT).show()
        } else {
            showResultActivity(country)
        }
    }

    private fun findBySim() {
        countryPicker = CountryPicker.Builder().with(this@MainActivity)
                .listener(this@MainActivity).build()
        val country = countryPicker.countryFromSIM
        if (country == null) {
            Toast.makeText(this@MainActivity, "Country not found", Toast.LENGTH_SHORT).show()
        } else {
            showResultActivity(country)
        }
    }

    private fun findByName() {
        countryPicker = CountryPicker.Builder().with(this@MainActivity)
                .listener(this@MainActivity).build()
        val builder = AlertDialog.Builder(this, R.style.Base_Theme_MaterialComponents_Dialog_Alert)
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setTitle("Country Name")
        builder.setView(input)
        builder.setCancelable(false)
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            val country = countryPicker.getCountryByName(input.text.toString())
            if (country == null) {
                Toast.makeText(this@MainActivity, "Country not found", Toast.LENGTH_SHORT).show()
            } else {
                showResultActivity(country)
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun showResultActivity(country: Country?) {
        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra(ResultActivity.BUNDLE_KEY_COUNTRY_NAME, country!!.name)
        intent.putExtra(ResultActivity.BUNDLE_KEY_COUNTRY_CURRENCY, country.currency)
        intent.putExtra(ResultActivity.BUNDLE_KEY_COUNTRY_DIAL_CODE, country.dialCode)
        intent.putExtra(ResultActivity.BUNDLE_KEY_COUNTRY_ISO, country.getCode())
        intent.putExtra(ResultActivity.BUNDLE_KEY_COUNTRY_FLAG_IMAGE, country.flag)
        startActivity(intent)
    }

    private fun showPicker() {
        val builder = CountryPicker.Builder().with(this@MainActivity)
                .listener(this@MainActivity)
        if (binding.customStyleToggleSwitch.isChecked) {
            builder.style(R.style.CountryPickerStyle)
        }
        builder.theme(if (binding.themeToggleSwitch.isChecked) CountryPicker.THEME_NEW else CountryPicker.THEME_OLD)
        builder.canSearch(binding.searchSwitch.isChecked)
        builder.sortBy(sortBy)
        countryPicker = builder.build()
        if (binding.bottomSheetSwitch.isChecked) {
            countryPicker.showBottomSheet(this@MainActivity)
        } else {
            countryPicker.showDialog(this@MainActivity)
        }
    }

    override fun onSelectCountry(country: Country?) {
        showResultActivity(country)
    }
}