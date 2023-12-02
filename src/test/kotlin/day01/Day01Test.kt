package day01

import eu.jameshamilton.day01.part1
import eu.jameshamilton.day01.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    @Test
    fun part1Example() {
        val testInput = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
            """.trimIndent()

        assertEquals(142, part1(testInput.lines()))
    }

    @Test
    fun part2Example() {
        val testInput = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()

        assertEquals(281, part2(testInput.lines()))
    }
}
