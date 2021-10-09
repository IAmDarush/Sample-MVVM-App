package com.simpleapp.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
  val address: UserAddress,
  val company: UserCompany,
  val id: String,
  val email: String,
  val name: String,
  val phone: String,
  val username: String,
  val website: String,
)

@Serializable
data class UserAddress(
  val street: String,
  val suite: String,
  val city: String,
  val zipcode: String,
  val geo: UserGeo
)

@Serializable
data class UserGeo(
  val lat: String,
  val lng: String
)

@Serializable
data class UserCompany(
  val name: String,
  val catchPhrase: String,
  val bs: String
)