package com.airtel.demo.search.data.model

data class Data(
	val addressList: ArrayList<AddressListItem> = ArrayList(),
	val autoCompleteRequestString: String? = null
)
