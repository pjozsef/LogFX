package com.github.pjozsef.logfx.adapter

import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.layout.HBox

data class Row(val row: Int, val text: String)

class RowCell : ListCell<Row>() {
    val lineNumber = Label()
    val content = Label()
    val root = HBox(lineNumber, content)

    init{
        lineNumber.prefWidth = 30.0
    }

    override fun updateItem(item: Row?, empty: Boolean) {
        super.updateItem(item, empty)
        if(empty){
            graphic = null
        }else{
            item?.let{
                lineNumber.text = item.row.toString()
                content.text = item.text
            }
            graphic = root
        }
    }
}