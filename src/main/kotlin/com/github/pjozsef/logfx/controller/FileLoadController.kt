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
    private var lastSelected: File? = null
    fun chooseFile(): File? {
        val fileChooser = FileChooser();
        lastSelected?.let{fileChooser.initialDirectory = it.parentFile}
        fileChooser.title = messages["filedialog_open_text"];
        val selected =  fileChooser.showOpenDialog(FX.primaryStage);
        selected?.let{ lastSelected = it }
        return selected
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