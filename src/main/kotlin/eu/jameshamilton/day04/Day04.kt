package eu.jameshamilton.day04

import java.util.*
import kotlin.math.pow

const val debug = false

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input04.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

data class Card(val id: Int, val winning: Set<Int>, val yours: Set<Int>) {
    val matching by lazy { winning.intersect(yours).size }
    val points by lazy { 2.0.pow(matching - 1.0).toInt() }
}

fun parseInput(input: List<String>): List<Card> = input.map { line ->
    val (cardN, rest) = line.split(": ")
    val (_, id) = cardN.split("\\s+".toRegex())
    val (winning, yours) = rest.split(" | ")
    Card(
        id.toInt(),
        winning.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet(),
        yours.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
    )
}

fun part1(input: List<Card>): Int = input.sumOf { it.points }

fun part2(input: List<Card>): Int {
    val queue: Deque<Card> = LinkedList(input)
    //val result = mutableListOf<Card>()
    var count = 0

    while (queue.isNotEmpty()) {
        with(queue.pollFirst()) {
            //result.add(this)
            count++
            queue.addAll(input.subList(id, id + matching))
        }
    }

    return count
}
