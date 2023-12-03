package day03

import eu.jameshamilton.day03.parseInput
import eu.jameshamilton.day03.part1
import eu.jameshamilton.day03.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    private val testInput = """
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
    """.trimIndent()
    private val parseInput = parseInput(testInput.lines())
    @Test
    fun part1Example() {
       assertEquals(4361, part1(parseInput))
    }

    @Test
    fun part2Example() {
        assertEquals(467835, part2(parseInput))
    }
}
