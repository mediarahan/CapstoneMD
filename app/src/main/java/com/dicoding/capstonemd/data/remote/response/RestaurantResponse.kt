package com.dicoding.capstonemd.data.remote.response

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem?>? = null
)

data class PlusCode(

	@field:SerializedName("compound_code")
	val compoundCode: String? = null,

	@field:SerializedName("global_code")
	val globalCode: String? = null
)

data class Southwest(

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class Location(

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class OpeningHours(

	@field:SerializedName("open_now")
	val openNow: Boolean? = null
)

data class Geometry(

	@field:SerializedName("viewport")
	val viewport: Viewport? = null,

	@field:SerializedName("location")
	val location: Location? = null
)

data class PhotosItem(

	@field:SerializedName("photo_reference")
	val photoReference: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("html_attributions")
	val htmlAttributions: List<String?>? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Viewport(

	@field:SerializedName("southwest")
	val southwest: Southwest? = null,

	@field:SerializedName("northeast")
	val northeast: Northeast? = null
)

data class Northeast(

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class RestaurantsItem(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("business_status")
	val businessStatus: String? = null,

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("icon_background_color")
	val iconBackgroundColor: String? = null,

	@field:SerializedName("reference")
	val reference: String? = null,

	@field:SerializedName("user_ratings_total")
	val userRatingsTotal: Int? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("icon_mask_base_uri")
	val iconMaskBaseUri: String? = null,

	@field:SerializedName("vicinity")
	val vicinity: String? = null,

	@field:SerializedName("hidden_gem")
	val hiddenGem: Boolean? = null,

	@field:SerializedName("plus_code")
	val plusCode: PlusCode? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null,

	@field:SerializedName("photos")
	val photos: List<PhotosItem?>? = null,

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null
)
