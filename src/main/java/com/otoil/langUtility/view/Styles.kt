package com.otoil.langUtility.view

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

/**
 * Created by avladimirov on 8/2/2016.
 */
class Styles : Stylesheet() {
    companion object {
        val wrapper by cssclass()
        val row by cssclass()
    }

    init {


        s(wrapper) {
            s(label) {
                minWidth = 100.px;
            }
            padding = box(15.px)

            s(row) {
                padding = box(5.px)
            }

        }

        s(tableView) {
            s(row) {
                size = 50.px
            }
        }
    }
}