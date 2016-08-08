package com.otoil.langUtility.view

import com.otoil.langUtility.dbhelper.LangData
import com.otoil.langUtility.dbhelper.Simple
import com.otoil.langUtility.utility.TextAreaTableCell
import javafx.collections.FXCollections
import javafx.scene.control.TableView
import javafx.scene.layout.BorderPane
import tornadofx.View
import tornadofx.column
import tornadofx.onChange

/**
 * Created by avladimirov on 8/2/2016.
 */


class MainView : View() {
    override val root: BorderPane by fxml()
    val tableDataLang: TableView<LangData> by fxid()
    val tableDataTranslate :TableView<LangData> by fxid()

    var data = FXCollections.observableArrayList<LangData>(

    )

    var dataTranslate = FXCollections.observableArrayList<LangData>(

    )

    init {

        tableDataLang.isEditable=true
        tableDataTranslate.isEditable=true
        tableDataLang.column("NUM", LangData::NUM)
        tableDataLang.column("OBJECT_PK", LangData::OBJECT_PK)
        tableDataLang.column("VALUE", LangData::VALUE).setCellFactory{
            TextAreaTableCell()
        }
        tableDataLang.columns.get(2).editableProperty()
        tableDataLang.column("IDENTIFIER", LangData::IDENTIFIER)
        tableDataLang.column("TABLE_NAME", LangData::TABLE_NAME)
        tableDataLang.items = data

        tableDataTranslate.column("NUM", LangData::NUM)
        tableDataTranslate.column("VALUE", LangData::VALUE).setCellFactory {
            TextAreaTableCell()
        }
        tableDataTranslate.column("IDENTIFIER", LangData::IDENTIFIER).setCellFactory {
            TextAreaTableCell()
        }
        tableDataTranslate.column("TABLE_NAME", LangData::TABLE_NAME).setCellFactory {
            TextAreaTableCell()
        }
        tableDataTranslate.column("LANG_LANG_ID", LangData::LANG_LANG_ID).setCellFactory {
            TextAreaTableCell()
        }
        tableDataTranslate.items = dataTranslate


        runAsync {
            data.addAll(Simple().getData())
        }

        tableDataLang.selectionModel.selectedItemProperty().onChange() {
          println(tableDataLang.selectionModel.selectedIndex)
           println(tableDataLang.items.get(tableDataLang.selectionModel.selectedIndex).OBJECT_PK)
            runAsync {
                dataTranslate.clear()
                val id = tableDataLang.selectionModel.selectedIndex
                dataTranslate.addAll(Simple().getTranslates(tableDataLang.items.get(id)))
            }

       }

    }



}
