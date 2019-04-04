package laaki

import javafx.application.Application
import tornadofx.App

class LaakiApp: App(LaakiView::class)

fun main(args: Array<String>) {
    Application.launch(LaakiApp::class.java, *args)
}
