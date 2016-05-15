package com.github.pjozsef.logfx.model

import javafx.scene.paint.Color

data class Filter(
        val searchText: String,
        val operation: Operation = Operation.NONE,
        val color: Highlight = Highlight.BASE) {

    companion object {
        fun of(raw: String): Filter? {
            val tokens = raw.trim().split(" ")
            if (tokens.size > 1) {
                var cursor = 0
                val operation = Operation.of(tokens[cursor])
                if(operation!= Operation.NONE) { cursor++ }
                var color: Highlight
                if(1-cursor+tokens.size>2){
                    color = Highlight.of(tokens[cursor])
                    if(color!= Highlight.BASE) { cursor++ }
                }else{
                    color = Highlight.BASE
                }
                val regex = tokens.subList(cursor, tokens.size).joinToString(" ")
                return Filter(regex, operation, color)
            }
            return null
        }
    }
}

enum class Operation(val symbol: String?) {
    INCLUDE("+"), EXCLUDE("-"), NONE(null);

    companion object {
        fun of(symbol: String): Operation {
            return when (symbol) {
                INCLUDE.symbol -> INCLUDE
                EXCLUDE.symbol -> EXCLUDE
                else -> NONE
            }
        }
    }
}

enum class Highlight(val value: Color, val codes: List<String>) {
    BASE(Color.WHITE, listOf()),
    RED(Color.web("e06666"), listOf("red", "r")),
    ORANGE(Color.web("f6b26b"), listOf("orange", "o")),
    YELLOW(Color.web("ffd966"), listOf("yellow", "y")),
    GREEN(Color.web("93c47d"), listOf("green", "g")),
    CYAN(Color.web("85d3ce"), listOf("cyan", "c")),
    BLUE(Color.web("6fa8dc"), listOf("blue", "b")),
    PURPLE(Color.web("ab84c4"), listOf("purple", "p")),
    MAGENTA(Color.web("df6da8"), listOf("magenta", "m"));

    companion object {
        fun of(symbol: String): Highlight {
            Highlight.values().forEach {
                if (symbol in it.codes) return it
            }
            return Highlight.BASE
        }
    }
}