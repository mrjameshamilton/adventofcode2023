package eu.jameshamilton.day01

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input01.txt")?.bufferedReader()?.readLines()!!
    println(part1(lines))
    println(part2(lines))
}

fun part1(input: List<String>): Int = input.sumOf { line ->
    "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
}

fun part2(input: List<String>): Int {
    class Parser(val source: String) {
        var start = 0
        var current = 0
        fun parse(): String {
            var result = ""

            while (!isAtEnd()) {
                start = current
                when {
                    matchDigit() -> result += previous()
                    match("zero") -> result += '0'
                    match("one") -> result += '1'
                    match("two") -> result += '2'
                    match("three") -> result += '3'
                    match("four") -> result += '4'
                    match("five") -> result += '5'
                    match("six") -> result += '6'
                    match("seven") -> result += '7'
                    match("eight") -> result += '8'
                    match("nine") -> result += '9'
                    else -> advance()
                }
            }

            return result
        }

        private fun matchDigit(): Boolean {
            if (peek().isDigit()) {
                advance()
                return true
            }
            return false
        }

        private fun match(string: String): Boolean {
            if (current + string.length > source.length) return false

            val slice = source.substring(start, current + string.length)

            if (slice == string) {
                current += string.length
                return true
            }

            return false
        }

        private fun advance() {
            current++
        }

        private fun peek(): Char {
            return source[current]
        }

        private fun previous(): Char {
            return source[current - 1]
        }

        private fun isAtEnd(): Boolean {
            return current >= source.length
        }
    }

    return input.map { Parser(it).parse() }.sumOf { line ->
        "${line.first()}${line.last()}".toInt()
    }
}