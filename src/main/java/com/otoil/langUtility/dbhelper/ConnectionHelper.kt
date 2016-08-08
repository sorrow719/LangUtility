package com.otoil.langUtility.dbhelper

import java.sql.Connection
import java.sql.DriverManager

/**
 * Created by avladimirov on 8/4/2016.
 */

class ConnectionHelper {
    companion object {
        val driver = "oracle.jdbc.OracleDriver"
        val url = "jdbc:oracle:thin:@10.100.22.13:1521:REMGTM"
        val username = "core_base"
        val password = "base"
        private lateinit var connection: Connection

        fun getCurrentConnection(): Connection {
            Class.forName(driver)
            connection = DriverManager.getConnection(url, username, password)
            return connection
        }

        fun closeCurrentConnection() {
            connection.close()
        }
    }
}