package com.github.pjozsef.logfx.adapter

import com.github.pjozsef.logfx.model.Highlight
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.HBox

data class Row(val row: Int, val text: String, var color: Highlight = Highlight.BASE)

class RowCell : ListCell<Row>() {
    val lineNumber = Label()
    val content = Label()
    val root = HBox(lineNumber, content)

    init {
        lineNumber.prefWidth = 55.0
    }

    override fun updateItem(item: Row?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            graphic = null
        } else {
            item?.let {
                lineNumber.text = item.row.toString()
                content.text = item.text
                root.background = Background(BackgroundFill(item.color.value, CornerRadii.EMPTY, Insets.EMPTY))
            }
            graphic = root
        }
    }
}