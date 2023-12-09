package eu.jameshamilton.day08

import eu.jameshamilton.day08.Instruction.L
import eu.jameshamilton.day08.Instruction.R

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input08.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

data class Node(val name: String)
typealias Network = kotlin.collections.Map<Node, Pair<Node, Node>>
typealias Map = Pair<List<Instruction>, Network>

enum class Instruction { L, R }

fun parseInput(input: List<String>): Map {
    val instructions = input.first().map {
        when (it) {
            'L' -> L
            'R' -> R
            else -> throw RuntimeException("Unknown instruction $it")
        }
    }

    val regex = "([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)".toRegex()

    val network = input.subList(2, input.size).associate { line ->
        val (n, l, r) = regex.matchEntire(line)?.destructured!!
        Node(n) to Pair(Node(l), Node(r))
    }

    return Map(instructions, network)
}

private fun steps(instructions: List<Instruction>, network: Network, start: Node, stop: (node: Node) -> Boolean): Int {
    var current = start
    val repeatingSequence = RepeatingSequence(instructions)
    val iterator = repeatingSequence.iterator()

    do {
        current = when (iterator.next()) {
            L -> network[current]!!.first
            R -> network[current]!!.second
        }
    } while (stop(current))

    return repeatingSequence.steps
}

fun part1(input: Map): Long {
    val (instructions, network) = input
    val start = Node("AAA")
    val end = Node("ZZZ")

    return steps(instructions, network, start) { it != end }.toLong()
}

fun part2(input: Map): Long {
    val (instructions, network) = input
    val startingPoints = network.keys.filter { it.name.endsWith("A") }

    val steps = startingPoints.map { start ->
        steps(instructions, network, start) { !it.name.endsWith("Z") }.toLong()
    }

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

    return steps.reduce(::lcm)
}

class RepeatingSequence<T>(private val list: List<T>) : Sequence<T> {
    private var index = 0
    val steps: Int get() = index

    override fun iterator() = object : Iterator<T> {
        override fun hasNext() = true

        override fun next(): T {
            return list[index % list.size].also {
                index++
            }
        }
    }
}
