package com.aemerse.countrypicker.listeners

import com.aemerse.countrypicker.Country

interface OnCountryPickerListener {
    fun onSelectCountry(country: Country?)
}