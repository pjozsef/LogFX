package com.github.pjozsef.logfx.style

import tornadofx.*

class ListStyle : Stylesheet() {
    init {
        s(listCell){
            padding = box(0.px)
            fontFamily = Array<String>(1){"monospace"}
        }

    }
}