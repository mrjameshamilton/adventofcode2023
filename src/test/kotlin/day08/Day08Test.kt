package day08

import eu.jameshamilton.day08.parseInput
import eu.jameshamilton.day08.part1
import eu.jameshamilton.day08.part2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {

    private val testInput = """
RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
    """.trimIndent()
    private val parseInput = parseInput(testInput.lines())

    private val testInput2 = """
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)
    """.trimIndent()
    private val parseInput2 = parseInput(testInput2.lines())


    private val testInput3 = """
LR

IIA = (IIB, XXX)
IIB = (XXX, IIZ)
IIZ = (IIB, XXX)
TTA = (TTB, XXX)
TTB = (TTC, TTC)
TTC = (TTZ, TTZ)
TTZ = (TTB, TTB)
XXX = (XXX, XXX)
    """.trimIndent()
    private val parseInput3 = parseInput(testInput3.lines())

    @Test
    fun part1Example() {
        assertEquals(2, part1(parseInput))
    }

    @Test
    fun part1Example2() {
        assertEquals(6, part1(parseInput2))
    }

    @Test
    fun part2Example() {
        assertEquals(6, part2(parseInput3))
    }
}
