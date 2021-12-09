package com.aemerse.countrypicker.listeners

import com.aemerse.countrypicker.Country

interface OnItemClickListener {
    fun onItemClicked(country: Country?)
}