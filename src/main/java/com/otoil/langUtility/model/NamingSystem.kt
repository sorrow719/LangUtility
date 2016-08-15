package com.otoil.langUtility.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.property

/**
 * Created by avladimirov on 8/10/2016.
 */
class NamingSystem {
    var NAMING_SYSTEM by property(SimpleStringProperty())

    constructor(NAMING_SYSTEM: String) {
        this.NAMING_SYSTEM = SimpleStringProperty(NAMING_SYSTEM)
    }

    override fun toString(): String {
        return NAMING_SYSTEM.value
    }
}