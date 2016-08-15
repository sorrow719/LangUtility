package com.otoil.langUtility.model

import com.otoil.langUtility.dbhelper.Simple
import javafx.beans.property.SimpleStringProperty
import tornadofx.onChange
import tornadofx.property

/**
 * Created by avladimirov on 8/10/2016.
 */
class LangData {
    var NUM by property(SimpleStringProperty())
    var OBJECT_PK by property(SimpleStringProperty())
    var VALUE by property(SimpleStringProperty())
    var IDENTIFIER by property(SimpleStringProperty())
    var LANG_LANG_ID by property(SimpleStringProperty())
    var TABLE_NAME by property(SimpleStringProperty())

    constructor(NUM: String, OBJECT_PK: String?, VALUE: String?, IDENTIFIER: String?, LANG_LANG_ID: String?, TABLE_NAME: String?) {
        this.NUM = SimpleStringProperty(NUM)
        this.OBJECT_PK = SimpleStringProperty(OBJECT_PK)
        this.VALUE = SimpleStringProperty(VALUE)
        this.IDENTIFIER = SimpleStringProperty(IDENTIFIER)
        this.LANG_LANG_ID = SimpleStringProperty(LANG_LANG_ID)
        this.TABLE_NAME = SimpleStringProperty(TABLE_NAME)

        this.VALUE.onChange {
            Simple.dataChanged.add(this)
        }
    }


    override fun toString(): String {
        return OBJECT_PK.value + " " + VALUE.value
    }
}