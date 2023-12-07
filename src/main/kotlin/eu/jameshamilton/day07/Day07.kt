package eu.jameshamilton.day07

import eu.jameshamilton.day07.Card.J
import eu.jameshamilton.day07.Card.JOKER
import eu.jameshamilton.day07.Hand.Type.FiveOfAKind
import eu.jameshamilton.day07.Hand.Type.FourOfAKind
import eu.jameshamilton.day07.Hand.Type.FullHouse
import eu.jameshamilton.day07.Hand.Type.HighCard
import eu.jameshamilton.day07.Hand.Type.OnePair
import eu.jameshamilton.day07.Hand.Type.ThreeOfAKind
import eu.jameshamilton.day07.Hand.Type.TwoPair

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("/input07.txt")?.bufferedReader()?.readLines()!!
    val input = parseInput(lines)
    println(part1(input))
    println(part2(input))
}

enum class Card {
    A, K, Q, J, T, _9, _8, _7, _6, _5, _4, _3, _2, JOKER;

    companion object {

        private val cardChars = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

        fun of(c: Char): Card {
            val index = cardChars.indexOf(c)
            if (index == -1) {
                throw IllegalArgumentException("Invalid card type: $c")
            }
            return entries[index]
        }
    }
}


data class Hand(val cards: List<Card>, val bid: Int) : Comparable<Hand> {

    enum class Type {
        FiveOfAKind,
        FourOfAKind,
        FullHouse,
        ThreeOfAKind,
        TwoPair,
        OnePair,
        HighCard
    }

    private val map by lazy {
        cards
            .groupBy { it }
            .mapValues { it.value.size }
    }

    private val hasJoker: Boolean by lazy { cards.contains(JOKER) }

    private val type: Type by lazy {
        when {
            isNOfAKind(5) -> FiveOfAKind
            isNOfAKind(4) -> if (hasJoker) FiveOfAKind else FourOfAKind
            isFullHouse() -> if (hasJoker) FiveOfAKind else FullHouse
            isNOfAKind(3) -> if (hasJoker) FourOfAKind else ThreeOfAKind
            isTwoPair() -> when (cards.count { it == JOKER }) {
                0 -> TwoPair
                2 -> FourOfAKind
                else -> FullHouse
            }

            isOnePair() -> if (hasJoker) ThreeOfAKind else OnePair
            isHighCard() -> if (hasJoker) OnePair else HighCard
            else -> throw RuntimeException("Invalid type")
        }
    }

    private fun isNOfAKind(n: Int): Boolean = map
        .any { it.value >= n }

    private fun isFullHouse(): Boolean = map
        .values.containsAll(setOf(2, 3))

    private fun isTwoPair(): Boolean = map
        .values.count { it == 2 } == 2

    private fun isOnePair(): Boolean = map.values.count { it == 2 } == 1

    private fun isHighCard(): Boolean = cards.toSet().size == 5

    override fun compareTo(other: Hand): Int = when (this.type) {
        other.type -> {
            // Same type, so compare each card. Find the first card
            // that differs and use that for comparison.
            this.cards.zip(other.cards).first {
                it.first.ordinal != it.second.ordinal
            }.let { it.second.ordinal - it.first.ordinal }
        }

        else -> other.type.ordinal - this.type.ordinal
    }
}

fun parseInput(input: List<String>): List<Hand> =
    input.map { line ->
        val (hand, bid) = line.split(" ")
        Hand(hand.toList().map { Card.of(it) }, bid.toInt())
    }

fun part1(input: List<Hand>): Int = input
    .sorted()
    .mapIndexed { index, hand ->
        (index + 1) * hand.bid
    }.sum()

fun part2(input: List<Hand>): Int = part1(input.map { hand ->
    // Replace J with JOKER
    Hand(hand.cards.map { if (it == J) JOKER else it }, hand.bid)
})
