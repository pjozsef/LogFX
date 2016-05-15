package com.github.pjozsef.logfx.style

import tornadofx.*

class ListViewStyle : Stylesheet() {
    companion object {
        // Define our styles
        /*val wrapper by cssclass()
        val bob by cssclass()
        val alice by cssclass()*/

        // Define our colors
        val bgColor = c("#000")
    }

    init {
        s(listCell){
            +s(focused){
                backgroundColor = bgColor
            }
//        style = "-fx-padding: 0; -fx-margin: 0; -fx-font-family: monospace;"
            padding = box(0.px)
            fontFamily = arrayOf("monospace")
        }




        /*s(wrapper) {
            padding = box(10.px)
            spacing = 10.px
        }

        s(label) {
            fontSize = 56.px
            padding = box(5.px, 10.px)
            maxWidth = infinity

            +s(bob, alice) {
                borderWidth = box(5.px)
            }
        }*/
    }
}