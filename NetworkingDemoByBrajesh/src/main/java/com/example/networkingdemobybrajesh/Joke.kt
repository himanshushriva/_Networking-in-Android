package com.example.networkingdemobybrajesh

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Joke(
    @Expose @SerializedName("value") val value: String? = null
) : Serializable
