package com.github.pjozsef.logfx

import com.github.pjozsef.logfx.style.ListViewStyle
import com.github.pjozsef.logfx.view.MainView
import javafx.application.Application
import javafx.application.Platform
import nl.komponents.kovenant.Dispatcher
import nl.komponents.kovenant.ProcessAwareDispatcher
import tornadofx.App
import nl.komponents.kovenant.ui.KovenantUi
import tornadofx.importStylesheet
import tornadofx.reloadStylesheetsOnFocus

fun main(args: Array<String>) {
    Application.launch(LogFxApp::class.java);
}

class LogFxApp : App() {
    override val primaryView = MainView::class

    init{
        KovenantUi.uiContext {
            dispatcher = object: ProcessAwareDispatcher {
                override fun offer(task: () -> Unit): Boolean {
                    Platform.runLater(task)
                    return true
                }
                override fun ownsCurrentProcess(): Boolean = Platform.isFxApplicationThread()
            }
        }

        importStylesheet(ListViewStyle::class)
        reloadStylesheetsOnFocus()
    }
}
