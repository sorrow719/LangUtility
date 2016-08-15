package com.otoil.langUtility.dbhelper

import com.otoil.langUtility.dbhelper.kjdbc.SqlHelper
import com.otoil.langUtility.model.LangData
import com.otoil.langUtility.model.NamingSystem
import com.otoil.langUtility.model.Table
import javafx.collections.FXCollections
import java.sql.Connection
import java.util.*
import kotlin.comparisons.compareBy

/**
 * Created by avladimirov on 8/2/2016.
 */


class Simple {

    companion object {

        val connection: Connection = ConnectionHelper.getCurrentConnection()

        val helper = SqlHelper(connection)

        var data = FXCollections.observableArrayList<LangData>(

        )

        var dataSearch = FXCollections.observableArrayList<LangData>(

        )

        var dataTranslate = FXCollections.observableArrayList<LangData>(

        )

        var dataChanged = FXCollections.observableArrayList<LangData>(

        )

        var dbDataCopy = HashMap<String, LangData>()

        var tableNames = FXCollections.observableArrayList<Table>()

        var namingSystem = FXCollections.observableArrayList<NamingSystem>()
    }


    val array = ArrayList<LangData>();

    fun getSelectAll() {
        helper.query("SELECT ROWNUM NUM, OBJECT_PK, VALUE, NS.IDENTIFIER, LANG_LANG_ID, TABLE_NAME " +
                "FROM CORE_BASE.OBJECT_ATTRIBUTES_LANG AL " +
                "LEFT JOIN CORE_BASE.NAMING_SYSTEMS NS " +
                "ON NS.NMSYS_ID = AL.NMSYS_NMSYS_ID")
        {}.use {
            it.forEach(LangData::class) {
                dbDataCopy.put(it.OBJECT_PK.value + it.IDENTIFIER.value + it.TABLE_NAME.value + it.LANG_LANG_ID.value, it)
            }
        }
        print(dbDataCopy.size)
    }

    fun getData(tableName: String, namingSystem: String): ArrayList<LangData> {
        array.clear()
        helper.query("SELECT ROWNUM NUM, OBJECT_PK, VALUE, NS.IDENTIFIER, LANG_LANG_ID, TABLE_NAME " +
                "FROM CORE_BASE.OBJECT_ATTRIBUTES_LANG AL " +
                "LEFT JOIN CORE_BASE.NAMING_SYSTEMS NS " +
                "ON NS.NMSYS_ID = AL.NMSYS_NMSYS_ID WHERE LANG_LANG_ID =1  AND TABLE_NAME = :TABLE_NAME AND NS.IDENTIFIER= :IDENTIFIER") {
            set("TABLE_NAME", tableName)
            set("IDENTIFIER", namingSystem)

        }.use {
            it.forEach(LangData::class) {
                array.add(it)
            }
        }
        // ConnectionHelper.closeCurrentConnection()
        return array
    }

    fun getTranslates(CURRENT_OBJECT : LangData) : ArrayList<LangData> {
        array.clear()
        helper.query("SELECT ROWNUM NUM, AL.*, NS.IDENTIFIER FROM OBJECT_ATTRIBUTES_LANG AL " +
                "LEFT JOIN CORE_BASE.NAMING_SYSTEMS NS " +
                "ON NS.NMSYS_ID = AL.NMSYS_NMSYS_ID " +
                "WHERE OBJECT_PK = :OBJECT_PK AND TABLE_NAME = :TABLE_NAME ") {
            set("OBJECT_PK", CURRENT_OBJECT.OBJECT_PK.get())
            set("TABLE_NAME", CURRENT_OBJECT.TABLE_NAME.get())
        }.use {
            it.forEach(LangData::class) {
                array.add(it)
            }
        }
        // ConnectionHelper.closeCurrentConnection()
        return array
    }

    fun getTableNames(): ArrayList<Table> {
        val array = ArrayList<Table>();
        helper.query("SELECT DISTINCT AL.TABLE_NAME FROM OBJECT_ATTRIBUTES_LANG AL ") {
        }.use {
            it.forEach(Table::class) {
                array.add(it)
            }
        }
        array.sortWith(compareBy({ it.TABLE_NAME.value }))
        // ConnectionHelper.closeCurrentConnection()
        return array
    }

    fun getNamingSystem(): ArrayList<NamingSystem> {
        val array = ArrayList<NamingSystem>();
        helper.query("SELECT DISTINCT NS.IDENTIFIER AS NAMING_SYSTEM FROM OBJECT_ATTRIBUTES_LANG AL " +
                "LEFT JOIN CORE_BASE.NAMING_SYSTEMS NS " +
                "ON NS.NMSYS_ID = AL.NMSYS_NMSYS_ID ") {
        }.use {
            it.forEach(NamingSystem::class) {
                array.add(it)
            }
        }
        array.sortWith(compareBy({ it.NAMING_SYSTEM.value }))
        // ConnectionHelper.closeCurrentConnection()
        return array
    }
}
