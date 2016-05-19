package com.github.pjozsef.logfx.controller

import com.github.pjozsef.logfx.adapter.Row
import com.github.pjozsef.logfx.model.Filter
import com.github.pjozsef.logfx.model.Operation
import tornadofx.Controller
import java.util.*
import java.util.function.Predicate

class FilterController : Controller() {
    private data class ParseResult(
            val all: List<Filter>,
            val include: List<Filter>,
            val exclude: List<Filter>)

    fun getPredicate(input: String): Predicate<Row> {
        val (all, include, exclude) = parseInput(input)

        return if (all.isEmpty()) {
            Predicate { true }
        } else {
            Predicate { row ->
                colorize(row, all)
                val contains: (Filter)->Boolean = { row.text.contains(it.searchText) }
                val isIncluded = include.any(contains) || include.isEmpty()
                val isExcluded = exclude.any(contains)
                isIncluded && !isExcluded
            }
        }
    }

    private fun parseInput(input: String): ParseResult {
        val filters = input.trimStart()
                .split("\n")
                .map { Filter.of(it) }
                .filterNotNull()
                .toList()
        val include = ArrayList<Filter>()
        val exclude = ArrayList<Filter>()

        filters.forEach {
            when (it.operation) {
                Operation.INCLUDE -> include.add(it)
                Operation.EXCLUDE -> exclude.add(it)
            }
        }
        return ParseResult(filters, include, exclude)
    }

    private fun colorize(row: Row, all: List<Filter>){
        all.forEach {
            val matches = row.text.contains(it.searchText)
            if (matches) row.color = it.highlight
        }
    }
}