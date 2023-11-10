package com.maxim.wheeloffortune.data

interface WheelCache {
    fun cacheId(id: Int)
    fun getCachedId(): Int
    fun clear()
    fun isEmpty(): Boolean
}