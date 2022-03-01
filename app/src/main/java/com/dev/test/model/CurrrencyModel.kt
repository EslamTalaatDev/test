package com.dev.test.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




  data class CurrrencyModel (

    @SerializedName("result")
    @Expose
    var result: String? = null,

    @SerializedName("documentation")
    @Expose
    var documentation: String? = null,

    @SerializedName("terms_of_use")
    @Expose
    var termsOfUse: String? = null,

    @SerializedName("time_last_update_unix")
    @Expose
    var timeLastUpdateUnix: Int? = null,

    @SerializedName("time_last_update_utc")
    @Expose
    var timeLastUpdateUtc: String? = null,

    @SerializedName("time_next_update_unix")
    @Expose
    var timeNextUpdateUnix: Int? = null,

    @SerializedName("time_next_update_utc")
    @Expose
    var timeNextUpdateUtc: String? = null,

    @SerializedName("base_code")
    @Expose
    var baseCode: String? = null,

    @SerializedName("conversion_rates")
    @Expose
    var conversionRates: Any? = null
)
