package com.github.pjozsef.logfx.controller

import com.github.pjozsef.logfx.adapter.Row
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.FX
import tornadofx.get
import java.io.File
import java.io.FileReader
import java.io.LineNumberReader
import java.util.*

class FileLoadController : Controller() {
    fun chooseFile(): File? {
        val fileChooser = FileChooser();
        fileChooser.title = messages["filedialog_open_text"];
        return fileChooser.showOpenDialog(FX.primaryStage);
    }

    fun readFile(file: File?): List<Row>? {
        file?.let {
            val result = ArrayList<Row>()
            val reader = LineNumberReader(FileReader(file))
            var line = reader.readLine()
            while (line != null) {
                result += Row(reader.lineNumber, line)
                line = reader.readLine()
            }
            return result
        }
        return null
    }
}