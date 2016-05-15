package com.github.pjozsef.logfx.controller

import com.github.pjozsef.logfx.model.Filter
import com.github.pjozsef.logfx.model.Operation
import com.github.pjozsef.extension.core.bool.invoke
import com.github.pjozsef.logfx.adapter.Row
import org.jetbrains.kotlin.utils.alwaysTrue
import tornadofx.Controller
import java.util.function.Predicate

class FilterController : Controller() {
    fun toFilterList(input: String): List<Filter> {
        return input.trim()
                .split("\n")
                .map { Filter.of(it) }
                .filterNotNull()
                .toList()
    }

    fun normalized(input: List<Filter>): List<Filter> {
        /*val predicate: (input: Filter) -> Boolean = { it.operation == Operation.EXCLUDE || it.operation == null }
        val containsInclusion = input.any(predicate)
        containsInclusion {
            return input.filter(predicate)
        }*/
        return input
    }

    fun toPredicate(input: List<Filter>): Predicate<Row> {
        return if (input.isEmpty()) {
            Predicate { true }
        } else {
            Predicate { row ->
                input.forEach {
                    val matches = row.text.contains(it.searchText)
                    if (matches) row.color = it.color
                }
                val filtered = input.filter { it.operation != Operation.NONE }
                if (filtered.size > 0) {
                    filtered.map {
                        val matches = row.text.contains(it.searchText)
                        when (it.operation) {
                            Operation.INCLUDE -> matches
                            Operation.EXCLUDE -> !matches
                            else -> error("This should not have occoured!")
                        }
                    }.reduce { sum, next -> sum || next }
                } else {
                    true
                }
            }
        }

    }
}