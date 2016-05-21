package com.github.pjozsef.logfx.view

import com.github.pjozsef.logfx.adapter.Row
import com.github.pjozsef.logfx.adapter.RowCell
import com.github.pjozsef.logfx.controller.FilterController
import com.github.pjozsef.logfx.model.Highlight
import javafx.application.Platform
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.SplitPane
import javafx.scene.control.TextArea
import javafx.util.Callback
import rx.javafx.kt.toObservable
import tornadofx.View
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

class LogView : View(){
    override val root: SplitPane by fxml()
    private val rules: TextArea by fxid()
    private val lines: ListView<Row> by fxid()

    private val filterController: FilterController by inject()

    private val fileLoadedProperty = SimpleObjectProperty(Object())
    private var baseList: List<Row> = listOf()


    init {
        setupListView()
        setupRulesArea()
    }

    fun setContent(rows: List<Row>?){
        rows?.let{
            baseList = it
            fileLoadedProperty.value = Object()
        }
    }

    private fun setupListView() {
        lines.cellFactory = Callback<ListView<Row>, ListCell<Row>> { RowCell() }
    }

    private fun setupRulesArea() {
        val onLoad = fileLoadedProperty.toObservable().map { rules.text }
        rules.textProperty()
                .toObservable()
                .mergeWith(onLoad)
                .sample(200, TimeUnit.MILLISECONDS)
                .map { filterController.getPredicate(it) }
                .forEach { predicate ->
                    Platform.runLater {
                        filterBaseList(predicate)
                    }
                }
    }

    private fun filterBaseList(predicate: Predicate<Row> = Predicate{true}){
        baseList.forEach { it.color = Highlight.BASE }
        val filtered = baseList.filter { predicate.test(it) }
        lines.items = FXCollections.observableArrayList(filtered)
        lines.refresh()
    }

}