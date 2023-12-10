package eu.jameshamilton.day10

import eu.jameshamilton.day10.Tile.F
import eu.jameshamilton.day10.Tile.GROUND
import eu.jameshamilton.day10.Tile.HorizontalPipe
import eu.jameshamilton.day10.Tile.J
import eu.jameshamilton.day10.Tile.L
import eu.jameshamilton.day10.Tile.SEVEN
import eu.jameshamilton.day10.Tile.START
import eu.jameshamilton.day10.Tile.VerticalPipe
import java.util.*

var printMap = true

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input10.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

enum class Tile(val char: Char, val description: String) {
    VerticalPipe('|', "a vertical pipe connecting north and south"),
    HorizontalPipe('-', "a horizontal pipe connecting east and west"),
    L('L', "a 90-degree bend connecting north and east"),
    J('J', "a 90-degree bend connecting north and west"),
    SEVEN('7', "a 90-degree bend connecting south and west"),
    F('F', "a 90-degree bend connecting south and east"),
    GROUND('.', "ground; there is no pipe in this tile"),
    START(
        'S',
        "the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has"
    );

    companion object {
        fun of(c: Char): Tile = entries.find { it.char == c }!!
    }

    override fun toString(): String = char.toString()
}

typealias Map = Array<Array<Coordinate>>

data class Coordinate(val x: Int, val y: Int, val tile: Tile, var isPath: Boolean = false) {
    override fun toString(): String = tile.char.toString()
}

fun parseInput(input: List<String>): Pair<Coordinate, Map> {
    lateinit var start: Coordinate
    val map: Array<Array<Coordinate>> = input.mapIndexed { x, line ->
        line.mapIndexed { y, t ->
            val coordinate = Coordinate(x, y, Tile.of(t))
            if (t == 'S') start = coordinate
            coordinate
        }.toTypedArray()
    }.toTypedArray()

    return Pair(start, map)
}

fun Map.adjacent(coordinate: Coordinate) = adjacent(coordinate.x, coordinate.y)
fun Map.adjacent(x: Int, y: Int): List<Coordinate> {
    val current = this[x][y]

    val n = this.getOrNull(x - 1)?.getOrNull(y)
    val s = this.getOrNull(x + 1)?.getOrNull(y)
    val w = this.getOrNull(x)?.getOrNull(y - 1)
    val e = this.getOrNull(x)?.getOrNull(y + 1)

    return when (current.tile) {
        VerticalPipe -> listOf(n, s)
        HorizontalPipe -> listOf(e, w)
        L -> listOf(n, e)
        J -> listOf(n, w)
        SEVEN -> listOf(s, w)
        F -> listOf(s, e)
        GROUND -> emptyList()
        START -> listOf(e, w, s, n)
    }.filterNotNull().filterNot { it.tile == GROUND }
}

fun part1(input: Pair<Coordinate, Map>): Int {
    val (start, map) = input

    val queue: Queue<Coordinate> = LinkedList(map.adjacent(start))
    val visited = mutableSetOf(start)

    do {
        with(queue.poll()) {
            if (visited.add(this)) {
                queue.addAll(map.adjacent(this))
            }
        }
    } while (!queue.isEmpty())

    visited.forEach { it.isPath = true }

    return visited.size / 2
}

fun part2(input: Pair<Coordinate, Map>): Int {
    // run part one to fill out the Tile.isPath field.
    part1(input)

    val (_, map) = input
    val enclosed = mutableSetOf<Coordinate>()

    for (row in map) {
        for ((y, element) in row.withIndex()) {
            if (element.isPath) continue

            val countLoopCrossings = row
                .slice(y + 1 until row.size)
                .count { it.isPath && it.tile in setOf(VerticalPipe, L, J) }

            // An odd number of times crossing the loop means
            // that it must be enclosed within the loop.
            if (countLoopCrossings % 2 != 0) {
                enclosed.add(element)
            }
        }
    }

    if (printMap) for (row in map) {
        for (element in row) {
            print(
                when {
                    element in enclosed -> "I"
                    element.isPath -> element
                    else -> "O"
                }
            )
        }
        println()
    }

    return enclosed.size
}

