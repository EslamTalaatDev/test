package com.dev.test.Util

import java.math.BigDecimal
import java.math.RoundingMode

object Utils {

    fun ConvertToDecimal(number:Double)= BigDecimal(number.toString()).setScale(3, RoundingMode.HALF_EVEN).toString()

}