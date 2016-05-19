package com.github.pjozsef.logfx.model

import org.junit.Assert.*
import org.junit.Test

class FilterTest {

    @Test
    fun operationOf() {
        assertEquals(Operation.INCLUDE, Operation.of("+"))
        assertEquals(Operation.EXCLUDE, Operation.of("-"))

        assertNotEquals(Operation.EXCLUDE, Operation.of("+"))
        assertNotEquals(Operation.INCLUDE, Operation.of("-"))

        assertEquals(Operation.NONE, Operation.of(""))
        assertEquals(Operation.NONE, Operation.of(" "))
        assertEquals(Operation.NONE, Operation.of("dummy"))
    }

    @Test
    fun highlightOf() {
        assertEquals(Highlight.RED, Highlight.of("red"))
        assertEquals(Highlight.RED, Highlight.of("r"))
        assertEquals(Highlight.ORANGE, Highlight.of("orange"))
        assertEquals(Highlight.ORANGE, Highlight.of("o"))
        assertEquals(Highlight.YELLOW, Highlight.of("yellow"))
        assertEquals(Highlight.YELLOW, Highlight.of("y"))
        assertEquals(Highlight.GREEN, Highlight.of("green"))
        assertEquals(Highlight.GREEN, Highlight.of("g"))
        assertEquals(Highlight.CYAN, Highlight.of("cyan"))
        assertEquals(Highlight.CYAN, Highlight.of("c"))
        assertEquals(Highlight.BLUE, Highlight.of("blue"))
        assertEquals(Highlight.BLUE, Highlight.of("b"))
        assertEquals(Highlight.PURPLE, Highlight.of("purple"))
        assertEquals(Highlight.PURPLE, Highlight.of("p"))
        assertEquals(Highlight.MAGENTA, Highlight.of("magenta"))
        assertEquals(Highlight.MAGENTA, Highlight.of("m"))

        assertEquals(Highlight.BASE, Highlight.of(""))
        assertEquals(Highlight.BASE, Highlight.of(" "))
        assertEquals(Highlight.BASE, Highlight.of("dummy"))
    }

    @Test
    fun filterOf() {
        assertEquals(
                Filter("com.github.* spaces in regex"),
                Filter.of("com.github.* spaces in regex"))
        assertEquals(
                Filter("com.github.* spaces in regex", Operation.EXCLUDE),
                Filter.of("- com.github.* spaces in regex"))
        assertEquals(
                Filter("com.github.* spaces in regex", Operation.NONE, Highlight.BLUE),
                Filter.of("blue com.github.* spaces in regex"))
        assertEquals(
                Filter("com.github.* spaces in regex", Operation.NONE, Highlight.BLUE),
                Filter.of("b com.github.* spaces in regex"))
        assertEquals(
                Filter("com.github.* spaces in regex", Operation.INCLUDE, Highlight.YELLOW),
                Filter.of("+ yellow com.github.* spaces in regex"))

        assertEquals(Filter("+", Operation.INCLUDE), Filter.of("+ +"))
        assertNull(Filter.of("+"))
        assertNull(Filter.of(""))
        assertNull(Filter.of("         "))
    }
}