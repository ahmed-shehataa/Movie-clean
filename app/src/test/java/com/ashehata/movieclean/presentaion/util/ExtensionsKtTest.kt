package com.ashehata.movieclean.presentaion.util

import androidx.core.util.toRange
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.math.RoundingMode
import java.text.DecimalFormat


class ExtensionsKtTest {

    @Test
    fun `toRate() pass vote average less than 5 output LOW`() {
        var startNumber = 0.0
        while (true) {
            startNumber += 0.2
            println(startNumber)
            if (startNumber >= 5.0) break
            val output = startNumber.toRate()
            assertThat(output).isEqualTo(RateType.LOW)
        }
    }


    @Test
    fun `toRate() pass vote average between (5 and 7_4) output MEDIUM`() {
        var startNumber = 5.0
        while (true) {
            startNumber += 0.2
            println(startNumber)
            if (startNumber >= 7.4) break
            val output = startNumber.toRate()
            assertThat(output).isEqualTo(RateType.MEDIUM)
        }
    }

    @Test
    fun `toRate() pass vote average between (7_5 and 10) output HIGH`() {
        var startNumber = 7.5
        while (true) {
            startNumber += 0.2
            println(startNumber)
            if (startNumber > 10) break
            val output = startNumber.toRate()
            assertThat(output).isEqualTo(RateType.HIGH)
        }
    }

    @Test
    fun `toRate() pass vote average more than 10 output UNDEFINED`() {
        var startNumber = 10.0
        while (true) {
            startNumber += 0.2
            println(startNumber)
            if (startNumber > 20) break
            val output = startNumber.toRate()
            assertThat(output).isEqualTo(RateType.UNDEFINED)
        }
    }

    @Test
    fun `toRate() pass vote average less than 0 output UNDEFINED`() {
        var startNumber = 0.0
        while (true) {
            startNumber -= 0.2
            println(startNumber)
            if (startNumber > -20) break
            val output = startNumber.toRate()
            assertThat(output).isEqualTo(RateType.UNDEFINED)
        }
    }

    @Test
    fun `roundOffDecimal() pass double value x_xxxx to be x_X`() {
        var startNumber = 1.0
        while (true) {
            startNumber += 0.2
            println(startNumber)
            if (startNumber > 50) break
            val output = startNumber.roundOffDecimal()
            val expected = String.format("%.1f", startNumber).toDouble()
            assertThat(output).isEqualTo(expected)
        }
    }


    @Test
    fun `roundFirstDigit() pass 5_26 expected 5_3`() {
        val startNumber = 5.26
        val output = startNumber.roundFirstDigit()
        assertThat(output).isEqualTo(5.3)
    }

    @Test
    fun `roundFirstDigit() pass 5_24 expected 5_2`() {
        val startNumber = 5.24
        val output = startNumber.roundFirstDigit()
        assertThat(output).isEqualTo(5.2)
    }

    @Test
    fun `roundFirstDigit() pass 5_8 expected 5_8`() {
        val startNumber = 5.8
        val output = startNumber.roundFirstDigit()
        assertThat(output).isEqualTo(5.8)
    }

    @Test
    fun `roundFirstDigit() pass 6_98 expected 7_0`() {
        val startNumber = 6.98
        val output = startNumber.roundFirstDigit()
        assertThat(output).isEqualTo(7.0)
    }

}


private fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this).toDouble()
}