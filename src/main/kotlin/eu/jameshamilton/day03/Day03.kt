package eu.jameshamilton.day03

const val debug = false

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input03.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

sealed interface Cell

object DotCell : Cell {
    override fun toString(): String = "  .  "
}

open class SymbolCell(private val n: Char) : Cell {
    override fun toString(): String = "  $n  "
}

object GearSymbolCell : SymbolCell('*')

// All NumberCells are unique
class NumberCell(val n: Int) : Cell {
    override fun toString(): String = " ${"$n".padStart(3, ' ')} "
    operator fun times(other: NumberCell) = n * other.n
}

typealias Matrix = Array<Array<Cell>>

fun parseInput(input: List<String>): Matrix {
    // Assumes input is square matrix, of size input.size
    class Parser(val source: String) {
        private var start = 0
        private var current = 0
        private var rowIndex = 0
        private var colIndex = 0
        private val tokens = Array(input.size) { (Array<Cell>(input.size) { DotCell }) }

        fun parse(): Matrix {
            while (!isAtEnd()) {
                start = current
                scanToken()
            }
            return tokens
        }

        private fun scanToken() {
            val c = advance()
            when {
                c.isDigit() -> number()
                c == '\n' -> line()
                else -> symbol()
            }
        }

        private fun number() {
            while (peek().isDigit()) advance()

            val cell = NumberCell(source.substring(start, current).toInt())

            val startIndex = colIndex
            // Increment colIndex to after the number
            colIndex = startIndex + current - start

            // Put the same NumberCell in the corresponding matrix locations
            for (i in startIndex until colIndex) {
                tokens[rowIndex][i] = cell
            }
        }

        private fun line() {
            rowIndex++
            colIndex = 0
        }

        private fun symbol() {
            val c = previous()
            tokens[rowIndex][colIndex] = when (c) {
                '.' -> DotCell
                '*' -> GearSymbolCell
                else -> SymbolCell(c)
            }
            colIndex++
        }

        private fun peek(): Char = if (isAtEnd()) 0.toChar() else source[current]
        private fun advance(): Char = source[current++]
        private fun previous(): Char = source[current - 1]
        private fun isAtEnd() = current >= source.length
    }

    val parser = Parser(input.joinToString("\n"))
    val matrix = parser.parse()

    if (debug) {
        for (line in matrix) {
            for (cell in line) {
                print(cell)
            }
            println()
        }
    }

    return matrix
}

fun part1(input: Matrix): Int {
    val numbers = mutableSetOf<NumberCell>()

    input.apply { cell, x, y ->
        if (cell is SymbolCell)
            numbers.addAll(adjacent(x, y))
    }

    return numbers.sumOf { it.n }
}

fun part2(input: Matrix): Int {
    var ratioSum = 0

    input.apply { cell, x, y ->
        if (cell is GearSymbolCell) with(adjacent<NumberCell>(x, y)) {
            // It's actually only gear if there are two adjacent numbers.
            if (size == 2) ratioSum += first() * last()
        }
    }

    return ratioSum
}

fun Matrix.apply(f: Matrix.(cell: Cell, x: Int, y: Int) -> Unit) {
    for ((x, row) in this.withIndex()) {
        for ((y, _) in row.withIndex()) {
            f(this[x][y], x, y)
        }
    }
}

inline fun <reified T : Cell> Matrix.adjacent(x: Int, y: Int): Set<T> {
    val result = mutableSetOf<T>()

    for (dx in -1..1) {
        for (dy in -1..1) {
            try {
                val adj = this[x + dx][y + dy]
                if (adj is T) result.add(adj)
            } catch (_: ArrayIndexOutOfBoundsException) {
                // Ignore.
            }
        }
    }

    return result
}
