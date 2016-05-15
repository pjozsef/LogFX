package com.github.pjozsef.logfx.model

import javafx.scene.paint.Color

data class Filter(
        val regex: String,
        val operation: Operation? = null,
        val color: Highlight? = null) {

    companion object{
        fun of(raw: String): Filter {
            val tokens = raw.split(" ")
            var cursor = 0

            val operation = Operation.of(tokens[cursor])
            operation?.let { cursor++ }
            val color = Highlight.of(tokens[cursor])
            color?.let { cursor++ }
            val regex = tokens.subList(cursor, tokens.size).joinToString(" ")
            return Filter(regex, operation, color)
        }
    }
}
/*
fun List<Filter>.normalize(): List<Filter>{
    val containsInclude = this.any{it.operation == Operation.INCLUDE}
    if(containsInclude){

    }
}*/

enum class Operation(val symbol: String) {
    INCLUDE("+"), EXCLUDE("-");

    companion object {
        fun of(symbol: String): Operation? {
            return when (symbol) {
                INCLUDE.symbol -> INCLUDE
                EXCLUDE.symbol -> EXCLUDE
                else -> null
            }
        }
    }
}

enum class Highlight(val value: Color, val codes: List<String>) {
    RED(Color.web("e06666"), listOf("red", "r")),
    ORANGE(Color.web("f6b26b"), listOf("orange", "o")),
    YELLOW(Color.web("ffd966"), listOf("yellow", "y")),
    GREEN(Color.web("93c47d"), listOf("green", "g")),
    CYAN(Color.web("85d3ce"), listOf("cyan", "c")),
    BLUE(Color.web("6fa8dc"), listOf("blue", "b")),
    PURPLE(Color.web("ab84c4"), listOf("purple", "p")),
    MAGENTA(Color.web("df6da8"), listOf("magenta", "m"));

    companion object {
        fun of(symbol: String): Highlight? {
            Highlight.values().forEach {
                if (symbol in it.codes) return it
            }
            return null
        }
    }
}