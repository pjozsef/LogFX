/**
 * Created by hunyadym on 2016. 02. 10..
 */

package com.github.pjozsef.logfx.view

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextArea
import javafx.scene.layout.BorderPane
import tornadofx.View

class MainView : View() {
    override val root: BorderPane by fxml()
    val open: JFXButton by fxid()
    val rules: JFXTextArea by fxid()

    init {
        println(open)
        println(rules)
    }
}