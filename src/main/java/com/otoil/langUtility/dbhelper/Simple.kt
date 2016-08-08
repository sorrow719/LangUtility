package com.otoil.langUtility.dbhelper

import com.otoil.langUtility.dbhelper.kjdbc.SqlHelper
import java.sql.Connection
import java.util.*

/**
 * Created by avladimirov on 8/2/2016.
 */
data class LangData(
        val NUM: String,
        val OBJECT_PK: String,
        val VALUE: String,
        val IDENTIFIER: String,
        val LANG_LANG_ID: String,
        val TABLE_NAME: String
)

class Simple {
    fun getData(): ArrayList<LangData> {
        val connection: Connection
        val array = ArrayList<LangData>();
        connection = ConnectionHelper.getCurrentConnection()
        val helper = SqlHelper(connection)
        helper.query("SELECT ROWNUM NUM, OBJECT_PK, VALUE, NS.IDENTIFIER, LANG_LANG_ID, TABLE_NAME " +
                "FROM CORE_BASE.OBJECT_ATTRIBUTES_LANG AL " +
                "LEFT JOIN CORE_BASE.NAMING_SYSTEMS NS " +
                "ON NS.NMSYS_ID = AL.NMSYS_NMSYS_ID WHERE LANG_LANG_ID =1 AND TABLE_NAME = :TABLE_NAME ") {
            set("TABLE_NAME", "WORD_LIBRARY")
        }.use {
            it.forEach(LangData::class) {
                array.add(it)
            }
        }
        ConnectionHelper.closeCurrentConnection()
        return array
    }

    fun getTranslates(CURRENT_OBJECT : LangData) : ArrayList<LangData> {
        val connection: Connection
        val array = ArrayList<LangData>();
        connection = ConnectionHelper.getCurrentConnection()
        val helper = SqlHelper(connection)
        helper.query("SELECT ROWNUM NUM, AL.*, NS.IDENTIFIER FROM OBJECT_ATTRIBUTES_LANG AL " +
                "LEFT JOIN CORE_BASE.NAMING_SYSTEMS NS " +
                "ON NS.NMSYS_ID = AL.NMSYS_NMSYS_ID " +
                "WHERE OBJECT_PK = :OBJECT_PK AND TABLE_NAME = :TABLE_NAME ") {
            set("OBJECT_PK", CURRENT_OBJECT.OBJECT_PK)
            set("TABLE_NAME", CURRENT_OBJECT.TABLE_NAME)
        }.use {
            it.forEach(LangData::class) {
                array.add(it)
            }
        }
        ConnectionHelper.closeCurrentConnection()
        return array
    }
}
