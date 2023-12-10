package day10

import eu.jameshamilton.day10.parseInput
import eu.jameshamilton.day10.part1
import eu.jameshamilton.day10.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    private val testInput = """
.....
.S-7.
.|.|.
.L-J.
.....
    """.trimIndent()
    private val parseInput = parseInput(testInput.lines())

    private val testInput2 = """
..F7.
.FJ|.
SJ.L7
|F--J
LJ...
    """.trimIndent()
    private val parseInput2 = parseInput(testInput2.lines())

    @Test
    fun part1Example() {
        assertEquals(4, part1(parseInput))
    }

    @Test
    fun part1Example2() {
        assertEquals(8, part1(parseInput2))
    }


    private val testInput3 = """
    ...........
    .S-------7.
    .|F-----7|.
    .||.....||.
    .||.....||.
    .|L-7.F-J|.
    .|..|.|..|.
    .L--J.L--J.
    ...........
    """.trimIndent()
    private val parseInput3 = parseInput(testInput3.lines())


    private val testInput4 = """
.F----7F7F7F7F-7....
.|F--7||||||||FJ....
.||.FJ||||||||L7....
FJL7L7LJLJ||LJ.L-7..
L--J.L7...LJS7F-7L7.
....F-J..F7FJ|L7L7L7
....L7.F7||L7|.L7L7|
.....|FJLJ|FJ|F7|.LJ
....FJL-7.||.||||...
....L---J.LJ.LJLJ...
    """.trimIndent()
    private val parseInput4 = parseInput(testInput4.lines())

    private val testInput5 = """
FF7FSF7F7F7F7F7F---7
L|LJ||||||||||||F--J
FL-7LJLJ||||||LJL-77
F--JF--7||LJLJ7F7FJ-
L---JF-JLJ.||-FJLJJ7
|F|F-JF---7F7-L7L|7|
|FFJF7L7F-JF7|JL---7
7-L-JL7||F7|L7F-7F7|
L.L7LFJ|||||FJL7||LJ
L7JLJL-JLJLJL--JLJ.L
    """.trimIndent()
    private val parseInput5 = parseInput(testInput5.lines())

    @Test
    fun part2Example2() {
        assertEquals(4, part2(parseInput3))
    }

    @Test
    fun part2Example3() {
        assertEquals(8, part2(parseInput4))
    }

    @Test
    fun part2Example4() {
        assertEquals(10, part2(parseInput5))
    }
}
