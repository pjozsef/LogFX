package com.github.pjozsef.logfx

import com.github.pjozsef.logfx.view.MainView
import javafx.application.Application
import tornadofx.App

fun main(args: Array<String>) {
    Application.launch(LogFxApp::class.java);
}

class LogFxApp : App() {
    override val primaryView = MainView::class
}
