package com.muratalarcin.yemektarifi.adapter

import android.view.View

interface SpecificationClickListener {

    abstract val specification: Any

    fun onSpecificationClicked(v: View)

}