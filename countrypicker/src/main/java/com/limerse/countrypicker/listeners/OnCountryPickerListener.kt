package com.limerse.countrypicker.listeners

import com.limerse.countrypicker.Country

interface OnCountryPickerListener {
    fun onSelectCountry(country: Country?)
}