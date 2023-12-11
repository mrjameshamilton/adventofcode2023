package day11

import eu.jameshamilton.day11.parseInput
import eu.jameshamilton.day11.part1
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    private val testInput = """
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
    """.trimIndent()
    private val parseInput = parseInput(testInput.lines())

    @Test
    fun part1Example() {
        assertEquals(374, part1(parseInput))
    }
}
