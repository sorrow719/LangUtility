package com.otoil.langUtility.view

import com.otoil.langUtility.dbhelper.Simple
import com.otoil.langUtility.model.LangData
import com.otoil.langUtility.model.NamingSystem
import com.otoil.langUtility.model.Table
import com.otoil.langUtility.utility.TextAreaTableCell
import javafx.scene.control.*
import javafx.scene.input.KeyCode
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
    val tableDataTranslate: TableView<LangData> by fxid()
    val listViewChanged: ListView<LangData> by fxid()
    val buttonSearch: Button by fxid()
    val tableNameBox: ComboBox<Table> by fxid()
    val namingSystemBox: ComboBox<NamingSystem> by fxid()
    val OBJECT_PK: TextField by fxid()
    val searchArea: TextArea by fxid()

    val buttonGenerate: Button by fxid()
    val buttonCommit: Button by fxid()

    val digit = "[0-9]+$".toRegex()


    companion object {
        var CURRENT_TABLE_NAME: String = "WORD_LIBRARY"
        var CURRENT_NAMING_SYSTEM: String = "full name"
    }

    init {
        tableDataLang.isEditable = true
        tableDataTranslate.isEditable = true
        tableDataLang.column("NUM", LangData::NUM.getter)
        tableDataLang.column("OBJECT_PK", LangData::OBJECT_PK.getter)
        tableDataLang.column("VALUE", LangData::VALUE.getter).setCellFactory {
            TextAreaTableCell()
        }
        tableDataLang.columns.get(2).editableProperty()
        tableDataLang.column("IDENTIFIER", LangData::IDENTIFIER.getter)
        tableDataLang.column("TABLE_NAME", LangData::TABLE_NAME.getter)


        tableDataTranslate.column("NUM", LangData::NUM.getter)
        tableDataTranslate.column("VALUE", LangData::VALUE.getter).setCellFactory {
            TextAreaTableCell()
        }
        tableDataTranslate.column("IDENTIFIER", LangData::IDENTIFIER.getter)
        tableDataTranslate.column("TABLE_NAME", LangData::TABLE_NAME.getter)
        tableDataTranslate.column("LANG_LANG_ID", LangData::LANG_LANG_ID.getter)
        tableDataTranslate.items = Simple.dataTranslate


        listViewChanged.isEditable = true
        listViewChanged.items = Simple.dataChanged


        buttonSearch.setOnAction {
            if (!searchArea.getText().isEmpty() || !OBJECT_PK.getText().isEmpty()) {
                runAsync {
                    Simple.dataSearch.clear()
                    Simple.data.forEach {
                        if (!OBJECT_PK.getText().isEmpty() && !searchArea.getText().isEmpty()) {
                            if (it.OBJECT_PK.value.contains(OBJECT_PK.getText())
                                    && it.VALUE.value.toUpperCase().contains(searchArea.getText().toUpperCase())) {
                                Simple.dataSearch.add(it)
                            }
                        } else if (!searchArea.getText().isEmpty()) {
                            if (it.VALUE.value.toUpperCase().contains(searchArea.getText().toUpperCase())) {
                                Simple.dataSearch.add(it)
                            }
                        } else {
                            if (it.OBJECT_PK.value.toUpperCase().contains(OBJECT_PK.getText().toUpperCase())) {
                                Simple.dataSearch.add(it)
                            }
                        }
                    }
                } ui {
                    tableDataLang.items = Simple.dataSearch
                    //Simple.data.clear()
                    //Simple.data.addAll(Simple.dataSearch)
                }
            }


            /*  if (digit.matches(OBJECT_PK.getText())) {
                  tableDataLang.requestFocus()
                  tableDataLang.selectionModel.select(OBJECT_PK.getText().toInt() - 1)
                  tableDataLang.focusModel.focus(OBJECT_PK.getText().toInt() - 1)
                  tableDataLang.scrollTo(OBJECT_PK.getText().toInt() - 1)
                  tableDataLang.refresh()
              }*/
        }

        runAsync {
            Simple.data.addAll(Simple().getData(CURRENT_TABLE_NAME, CURRENT_NAMING_SYSTEM))
            Simple.tableNames.addAll(Simple().getTableNames())
            Simple.namingSystem.addAll(Simple().getNamingSystem())
        } ui {
            tableDataLang.items = Simple.data
            tableNameBox.items = Simple.tableNames
            namingSystemBox.items = Simple.namingSystem
        }

        tableNameBox.setOnAction {
            CURRENT_TABLE_NAME = tableNameBox.items.get(tableNameBox.selectionModel.selectedIndex).TABLE_NAME.value

            runAsync {
                Simple.data.clear()
                Simple.data.addAll(Simple().getData(
                        tableNameBox.items.get(tableNameBox.selectionModel.selectedIndex).TABLE_NAME.value, CURRENT_NAMING_SYSTEM))
            } ui {
                tableDataLang.items = Simple.data
            }
        }

        namingSystemBox.setOnAction {
            CURRENT_NAMING_SYSTEM = namingSystemBox.items.get(namingSystemBox.selectionModel.selectedIndex).NAMING_SYSTEM.value

            runAsync {
                Simple.data.clear()
                Simple.data.addAll(Simple().getData(CURRENT_TABLE_NAME,
                        namingSystemBox.items.get(namingSystemBox.selectionModel.selectedIndex).NAMING_SYSTEM.value))
            } ui {
                tableDataLang.items = Simple.data
            }
        }

        tableDataLang.selectionModel.selectedItemProperty().onChange() {
            if (tableDataLang.selectionModel.selectedIndex != -1) {
                runAsync {
                    Simple.dataTranslate.clear()
                    val id = tableDataLang.selectionModel.selectedIndex
                    Simple.dataTranslate.addAll(Simple().getTranslates(tableDataLang.items.get(id)))
                }
            }

        }

        tableDataLang.setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {

            }
        }

        buttonGenerate.setOnAction {
            Simple.helper.insert("REPORT_ATOLL.OBJECT_VALID_LANG ") {
                set("VALUE_RU", "test1")
                set("VALUE_EN", "test2")
                set("VALUE_VI", "test3")
                set("VALUE_SR", "test4")
                set("PK", 1)
            }
        }


        buttonCommit.setOnAction {
            tableDataTranslate.selectionModel.clearSelection()
        }

    }


}
