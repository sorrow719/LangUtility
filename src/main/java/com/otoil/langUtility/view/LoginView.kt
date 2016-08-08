package com.otoil.langUtility.view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.VBox
import tornadofx.*

/**
 * Created by avladimirov on 8/2/2016.
 */
class LoginView : View() {
override val root = VBox()

val databases = FXCollections.observableArrayList("Austin",
            "Dallas","Midland","San Antonio","Fort Worth")

    val selectedDb = SimpleStringProperty()
    val mainView: MainView by inject()

    init {
        title = "Вход"
        with(root) {
            addClass(Styles.wrapper)
            hbox {
                label("Username")
                textfield()
            }

            hbox {
                label("Password")
                passwordfield ()
            }

            hbox {
                label("Database")
                combobox(selectedDb, databases)
            }

            hbox {
                button ("Login") {
                    setOnAction {
                        if (FX.primaryStage.scene.root != mainView.root) {
                            FX.primaryStage.scene.root = mainView.root
                            FX.primaryStage.sizeToScene()
                            FX.primaryStage.centerOnScreen()
                        }
                    }
                }

            }
        }
    }
}

