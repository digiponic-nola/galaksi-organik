package com.npe.galaxyorganic.ui.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
class DatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Customer.db", null , 1){
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as DatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(CustomerModel.TABLE_CUSTOMER, true,
            CustomerModel.ID to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
            CustomerModel.CUSTOMER_ID to INTEGER + UNIQUE,
            CustomerModel.CUSTOMER_NAME to TEXT,
            CustomerModel.CUSTOMER_EMAIL to TEXT,
            CustomerModel.CUSTOMER_PHOTO to TEXT
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(CustomerModel.TABLE_CUSTOMER, true)
    }
}
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)