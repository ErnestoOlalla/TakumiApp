package com.efor18.takumi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform