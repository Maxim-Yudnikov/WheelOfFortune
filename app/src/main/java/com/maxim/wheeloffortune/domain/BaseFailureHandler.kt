package com.maxim.wheeloffortune.domain

import java.lang.Exception

class BaseFailureHandler: FailureHandler {
    override fun handle(e: Exception): Failure {
        return when(e) {
            is EmptyItemListException -> EmptyItemListError()
            is EmptyItemNameException -> EmptyItemNameError(e.message.toInt())
            else -> UnknownError()
        }
    }
}