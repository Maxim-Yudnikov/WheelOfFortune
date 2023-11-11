package com.maxim.wheeloffortune.domain

import java.io.IOException

class EmptyItemListException: IOException()
data class EmptyItemNameException(override val message: String): IOException()