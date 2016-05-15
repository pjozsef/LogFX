/**
 * Created by hunyadym on 2016. 02. 10..
 */

package com.github.pjozsef.logfx.view

import com.github.pjozsef.logfx.adapter.Row
import com.github.pjozsef.logfx.adapter.RowCell
import com.github.pjozsef.logfx.controller.FileLoadController
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextArea
import javafx.collections.FXCollections
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.BorderPane
import javafx.stage.FileChooser
import javafx.util.Callback
import nl.komponents.kovenant.task
import nl.komponents.kovenant.then
import nl.komponents.kovenant.ui.promiseOnUi
import nl.komponents.kovenant.ui.successUi
import tornadofx.FX
import tornadofx.View
import tornadofx.get

class MainView : View() {
    override val root: BorderPane by fxml()
    val open: JFXButton by fxid()
    val rules: JFXTextArea by fxid()
    val lines: ListView<Row> by fxid()

    val fileLoader: FileLoadController by inject()

    init {
        setupOpenButton()
        setupListView()
    }

    private fun setupOpenButton(){
        open.setOnMouseClicked {
            promiseOnUi {
                fileLoader.chooseFile()
            } then{
                fileLoader.readFile(it)
            } successUi {
                it?.let {
                    lines.items = FXCollections.observableArrayList(it)
                }
            }
        }
    }

    private fun setupListView() {
        lines.cellFactory = Callback<ListView<Row>, ListCell<Row>> { RowCell() }
    }
}