package com.maxim.wheeloffortune.domain

import java.lang.Exception

interface FailureHandler {
    fun handle(e: Exception): Failure
}