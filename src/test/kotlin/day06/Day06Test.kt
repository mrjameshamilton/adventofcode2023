package day06

import eu.jameshamilton.day06.Race
import eu.jameshamilton.day06.part1
import eu.jameshamilton.day06.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    private val input = listOf(
        Race(7, 9),
        Race(15, 40),
        Race(30, 200)
    )
    @Test
    fun part1Example() {
        assertEquals(288, part1(input))
    }

    @Test
    fun part2Example() {
        assertEquals(71503, part2(Race(7_15_30, 9_40_200)))
    }
}
