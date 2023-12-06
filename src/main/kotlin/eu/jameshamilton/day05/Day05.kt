package eu.jameshamilton.day05

import java.util.*

const val debug = false

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input05.txt")?.bufferedReader()?.readLines()!!
    println(part1(lines))
    println(part2(lines))
}

data class Seed(val start: Long, val end: Long = start)
data class MapEntry(val dest: Long, val src: Long, val length: Long)
typealias Map = List<MapEntry>

fun Map.destination(seed: Seed): Seed {

    forEach {
        if (seed.start >= it.src && seed.start <= it.src + it.length) return Seed(seed.start - it.src + it.dest)
    }

    return seed
}

fun part1(input: List<String>, seedTransformer: (List<Seed>) -> List<Seed> = { it }): Long {
    val list = mutableListOf<MapEntry>()
    val queue: Queue<String> = LinkedList(input)
    val (_, seedString) = queue.remove().split(": ")
    var seeds = seedTransformer(seedString.split(" ").map { Seed(it.toLong()) })

    queue.remove() // blank line

    fun update(seeds: List<Seed>, map: List<MapEntry>): List<Seed> {
        return seeds.map { seed -> map.destination(seed) }
    }

    do {
        when (val line = queue.remove()) {
            "seed-to-soil map:",
            "soil-to-fertilizer map:",
            "fertilizer-to-water map:",
            "water-to-light map:",
            "light-to-temperature map:",
            "temperature-to-humidity map:",
            "humidity-to-location map:" -> list.clear()

            "" -> seeds = update(seeds, list)
            else -> {
                val (d, s, l) = line.split(" ")
                list.add(MapEntry(d.toLong(), s.toLong(), l.toLong()))
            }
        }

        if (queue.isEmpty()) seeds = update(seeds, list)
    } while (queue.isNotEmpty())

    return seeds.minOf { it.start }
}

// TODO: refactor to use intervals instead, this is too slow / uses too much memory.
fun part2(input: List<String>): Long = part1(input) { seeds ->
    val result = mutableListOf<Seed>()

    seeds
        .map { it.start }
        .windowed(2, 2)
        .forEach {
            (it.first()..(it.first() + it.last())).forEach {
                result.add(Seed(it))
            }
        }

    return@part1 result
}
