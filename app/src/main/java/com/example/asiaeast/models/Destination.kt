package com.example.asiaeast.models

import com.google.firebase.firestore.GeoPoint

data class Destination( val Location: GeoPoint, val country: String, val city: String, val name: String, val type: String)