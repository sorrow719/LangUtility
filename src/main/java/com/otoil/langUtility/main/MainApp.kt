package com.otoil.langUtility.main

import com.otoil.langUtility.view.LoginView
import com.otoil.langUtility.view.MainView
import com.otoil.langUtility.view.Styles
import tornadofx.App
import tornadofx.importStylesheet
import tornadofx.reloadStylesheetsOnFocus

/**
 * Created by avladimirov on 8/2/2016.
 */
class MainApp : App(){
    override val primaryView = LoginView::class;

    init {
       importStylesheet(Styles::class)
        reloadStylesheetsOnFocus()
    }

}