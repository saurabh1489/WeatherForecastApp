package com.sample.weather.internal

import com.google.gson.annotations.SerializedName

enum class UnitSystem {
    @SerializedName("m")
    METRIC,
    @SerializedName("f")
    IMPERIAL,
    @SerializedName("s")
    SCIENTIFIC
}