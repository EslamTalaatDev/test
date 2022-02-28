package com.dev.test.Util

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:Any) : ApiState()
    object Empty : ApiState()
}
