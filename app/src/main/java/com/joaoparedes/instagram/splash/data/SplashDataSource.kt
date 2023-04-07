package com.joaoparedes.instagram.splash.data

interface SplashDataSource {
    fun session(callback: SplashCallback)
}