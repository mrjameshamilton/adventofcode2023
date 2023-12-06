package eu.jameshamilton.day06

fun main() {
    println(
        part1(
            listOf(
                Race(61, 430),
                Race(67, 1036),
                Race(75, 1307),
                Race(71, 1150)
            )
        )
    )
    println(part2(Race(61_67_75_71, 430_1036_1307_1150)))
}

data class Race(val time: Int, val distance: Long)

fun part1(input: List<Race>): Int = input.map { race ->
    (1..race.time.toLong()).asSequence()
        .map { it * (race.time - it) }
        .count { it > race.distance }
}.reduce { acc, i -> acc * i }

fun part2(input: Race) = part1(listOf(input))
