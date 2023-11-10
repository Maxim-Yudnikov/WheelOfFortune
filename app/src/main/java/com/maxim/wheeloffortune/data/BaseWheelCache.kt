package com.maxim.wheeloffortune.data

class BaseWheelCache: WheelCache {
    private var id: Int? = null
    override fun cacheId(id: Int) {
        this.id = id
    }

    override fun getCachedId(): Int = id!!

    override fun clear() {
        id = null
    }

    override fun isEmpty(): Boolean = id == null
}