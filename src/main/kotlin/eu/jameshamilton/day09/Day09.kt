package eu.jameshamilton.day09


fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input09.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}


fun parseInput(input: List<String>): List<List<Int>> =
    input.map { line ->
        line.split(" ").map { it.toInt() }
    }

fun part1(input: List<List<Int>>): Int = input.sumOf { list ->
    var current = list
    var nextValue = 0
    do {
        nextValue += current.last()
        current = current.zipWithNext().map { it.second - it.first }
    } while (current.sum() != 0)
    nextValue
}

fun part2(input: List<List<Int>>): Int = part1(input.map { it.reversed() })
