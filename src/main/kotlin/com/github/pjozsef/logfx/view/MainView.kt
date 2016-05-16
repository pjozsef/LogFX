/**
 * Created by hunyadym on 2016. 02. 10..
 */

package com.github.pjozsef.logfx.view

import com.github.pjozsef.logfx.adapter.Row
import com.github.pjozsef.logfx.adapter.RowCell
import com.github.pjozsef.logfx.controller.FileController
import com.github.pjozsef.logfx.controller.FilterController
import com.github.pjozsef.logfx.model.Highlight
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import javafx.util.Callback
import nl.komponents.kovenant.then
import nl.komponents.kovenant.ui.promiseOnUi
import nl.komponents.kovenant.ui.successUi
import rx.javafx.kt.toObservable
import tornadofx.View
import java.util.concurrent.TimeUnit

class MainView : View() {
    override val root: BorderPane by fxml()
    val open: Button by fxid()
    val rules: TextArea by fxid()
    val lines: ListView<Row> by fxid()

    val fileLoader: FileController by inject()
    val filterController: FilterController by inject()

    var baseList: List<Row> = listOf()

    init {
        setupOpenButton()
        setupListView()
        setupRulesArea()
    }

    private fun setupOpenButton() {
        open.setOnMouseClicked {
            promiseOnUi {
                fileLoader.chooseFile()
            } then{
                fileLoader.readFile(it)
            } successUi {
                it?.let {
                    baseList = it
                    lines.items = FXCollections.observableArrayList(it)
                }
            }
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