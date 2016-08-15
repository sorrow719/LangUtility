package com.otoil.langUtility.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.property

/**
 * Created by avladimirov on 8/10/2016.
 */
class Table {
    var TABLE_NAME by property(SimpleStringProperty())

    constructor(TABLE_NAME: String) {
        this.TABLE_NAME = SimpleStringProperty(TABLE_NAME)
    }

    override fun toString(): String {
        return TABLE_NAME.value
    }
}