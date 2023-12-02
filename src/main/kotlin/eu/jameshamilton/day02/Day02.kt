package eu.jameshamilton.day02

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input02.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

data class Game(val id: Int, val sets: List<List<Pair<String, Int>>>) {
    fun maxOf(colour: String): Int = sets.flatten().filter { it.first == colour }.maxOf { it.second }
}

fun parseInput(input: List<String>): List<Game> {
    return input.map { line ->
        val (game, sets) = line.split(':')
        val (_, id) = game.split(' ')

        Game(
            id.toInt(),
            sets.split(';')
                .map { it.trim() }
                .map {
                    it.split(',')
                        .map { it.trim() }
                        .map {
                            val (amount, colour) = it.split(' ')
                            Pair(colour, amount.toInt())
                        }
                }
        )
    }
}

fun part1(input: List<Game>): Int = input.filter {
    it.maxOf("red") <= 12 && it.maxOf("green") <= 13 && it.maxOf("blue") <= 14
}.sumOf { it.id }

fun part2(input: List<Game>): Int = input.sumOf {
    it.maxOf("red") * it.maxOf("green") * it.maxOf("blue")
}
