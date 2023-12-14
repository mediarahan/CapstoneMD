package com.dicoding.capstonemd.data.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyResponse(

	@field:SerializedName("message")
	val message: String? = null
)
