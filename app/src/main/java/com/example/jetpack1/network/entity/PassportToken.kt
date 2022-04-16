package com.example.jetpack1.network.entity

data class PassportToken(
    val tokenType: String,
    val expiresIn: String,
    val accessToken: String,
    val refreshToken: String
)
