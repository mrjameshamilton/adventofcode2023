package day09

import eu.jameshamilton.day09.parseInput
import eu.jameshamilton.day09.part1
import eu.jameshamilton.day09.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    private val testInput = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
    """.trimIndent()
    private val parseInput = parseInput(testInput.lines())

    @Test
    fun part1Example() {
        assertEquals(114, part1(parseInput))
    }


    @Test
    fun part2Example1() {
        assertEquals(5, part2(parseInput.subList(2, 3)))
    }

    @Test
    fun part2Example2() {
        assertEquals(2, part2(parseInput))
    }
}
