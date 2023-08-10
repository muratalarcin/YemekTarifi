package com.muratalarcin.yemektarifi.adapter

import android.view.View
import com.muratalarcin.yemektarifi.model.Specification

interface SpecificationClickListener {
    fun onSpecificationClicked(v: View, specification: Specification)
}