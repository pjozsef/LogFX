package com.github.pjozsef.logfx.view

import com.github.pjozsef.logfx.adapter.Row
import com.github.pjozsef.logfx.adapter.RowCell
import com.github.pjozsef.logfx.controller.FilterController
import com.github.pjozsef.logfx.model.Highlight
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.SplitPane
import javafx.scene.control.TextArea
import javafx.util.Callback
import rx.javafx.kt.toObservable
import tornadofx.View
import java.util.concurrent.TimeUnit

class LogView : View(){
    override val root: SplitPane by fxml()
    private val rules: TextArea by fxid()
    private val lines: ListView<Row> by fxid()

    private val filterController: FilterController by inject()

    private var baseList: List<Row> = listOf()

    init {
        setupListView()
        setupRulesArea()
    }

    fun setContent(rows: List<Row>?){
        rows?.let{
            baseList = it
            lines.items = FXCollections.observableArrayList(baseList)
        }
    }

    private fun setupListView() {
        lines.cellFactory = Callback<ListView<Row>, ListCell<Row>> { RowCell() }
    }

    private fun setupRulesArea() {
        rules.textProperty()
                .toObservable()
                .sample(200, TimeUnit.MILLISECONDS)
                .map { filterController.toFilterList(it) }
                .map { filterController.normalized(it) }
                .map { filterController.toPredicate(it) }
                .forEach { predicate ->
                    Platform.runLater {
                        baseList.forEach { it.color = Highlight.BASE }
                        val filtered = baseList.filter { predicate.test(it) }
                        lines.items = FXCollections.observableArrayList(filtered)
                        lines.refresh()
                    }
                }
    }
}