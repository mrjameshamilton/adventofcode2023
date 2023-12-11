package eu.jameshamilton.day11

import kotlin.math.abs

const val debug = false

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input11.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

data class Node(val id: Int, val x: Long, val y: Long)

val rowsToExpand = mutableSetOf<Int>()
val colsToExpand = mutableSetOf<Int>()
fun parseInput(input: List<String>): List<String> {
    for ((index, row) in input.withIndex()) {
        if (row.count { it == '.' } == row.length) {
            rowsToExpand += index
        }
    }

    for (c in 0 until input[0].length) {
        var count = 0
        for (row in input) {
            if (row[c] == '.') count++
        }

        if (count == input.size) {
            colsToExpand += c
        }
    }

    return input.toList()
}

fun part1(input: List<String>): Long = calculate(input, 2)
fun part2(input: List<String>): Long = calculate(input, 1_000_000)

fun calculate(input: List<String>, adjustment: Int): Long {
    val nodes = mutableListOf<Node>()

    var dy = 0L
    for ((y, row) in input.withIndex()) {
        if (y in rowsToExpand) dy += adjustment - 1

        var dx = 0L
        for ((x, c) in row.withIndex()) {
            if (x in colsToExpand) dx += adjustment - 1

            if (c == '#') {
                nodes.add(Node(nodes.size + 1, dx, dy))
            }

            if (debug) print(c)
            dx++
        }
        if (debug) println()
        dy++
    }

    val pairs = nodes.mapIndexed { index, s ->
        nodes.slice(index + 1 until nodes.size).map { Pair(s, it) }
    }.flatten()

    return pairs.sumOf { pair ->
        // Manhattan distance
        abs(pair.first.x - pair.second.x) + abs(pair.first.y - pair.second.y)
    }
}



