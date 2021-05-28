package com.limerse.countrypicker.listeners

import com.limerse.countrypicker.Country

interface OnItemClickListener {
    fun onItemClicked(country: Country?)
}