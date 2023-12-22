package com.dicoding.capstonemd.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserPrefResponse(

	@field:SerializedName("message")
	val message: String? = null
)
