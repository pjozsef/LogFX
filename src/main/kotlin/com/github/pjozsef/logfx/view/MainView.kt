/**
 * Created by hunyadym on 2016. 02. 10..
 */

package com.github.pjozsef.logfx.view

import com.github.pjozsef.logfx.controller.FileController
import javafx.scene.control.MenuItem
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import nl.komponents.kovenant.then
import nl.komponents.kovenant.ui.promiseOnUi
import nl.komponents.kovenant.ui.successUi
import tornadofx.View

class MainView : View() {
    override val root: AnchorPane by fxml()

    private val container: VBox by fxid()
    private val open: MenuItem by fxid()

    private val logView: LogView by inject()

    private val fileLoader: FileController by inject()

    init{
        container.children.add(logView.root)
        VBox.setVgrow(logView.root, Priority.ALWAYS)

        setup()
    }

    private fun setup() {
        setupOpen()
    }

    private fun setupOpen() {
        open.setOnAction {
            promiseOnUi {
                fileLoader.chooseFile()
            } then{
                fileLoader.readFile(it)
            } successUi {
                logView.setContent(it)
            }
        }
    }
}