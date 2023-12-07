package day07

import eu.jameshamilton.day07.parseInput
import eu.jameshamilton.day07.part1
import eu.jameshamilton.day07.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {

    private val testInput = """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
    """.trimIndent()
    private val parseInput = parseInput(testInput.lines())

    @Test
    fun part1Example() {
        assertEquals(6440, part1(parseInput))
    }

    @Test
    fun part2Example() {
        assertEquals(5905, part2(parseInput))
    }
}
