package com.github.sigute.demo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.github.sigute.demo.api.model.Customer;
import com.github.sigute.demo.ui.tables.Table;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuandooDB";
    private static final int CURRENT_VERSION = 1;


    private class Customers {
        private static final String TABLE = "customers";
        private static final String ID = "customer_id";
        private static final String FIRST_NAME = "customer_first_name";
        private static final String LAST_NAME = "customer_last_name";
    }

    private class Tables {
        private static final String TABLE = "tables";
        private static final String ID = "table_id";
        private static final String AVAILABILITY = "table_availability";
    }

    @Inject
    public Database(@NonNull Context context) {
        super(context, DATABASE_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Customers.TABLE + "(" +
                Customers.ID + " INTEGER NOT NULL," +
                Customers.FIRST_NAME + " TEXT NOT NULL," +
                Customers.LAST_NAME + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Tables.TABLE + "(" +
                Tables.ID + " INTEGER NOT NULL," +
                Tables.AVAILABILITY + " INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no upgrades at the moment
    }

    public void storeCustomers(List<Customer> customers) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(Customers.TABLE, null, null);
        for (Customer customer : customers) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Customers.ID, customer.getId());
            contentValues.put(Customers.FIRST_NAME, customer.getFirstName());
            contentValues.put(Customers.LAST_NAME, customer.getLastName());
            db.insert(Customers.TABLE, null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Customers.TABLE, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Customers.ID));
            String firstName = cursor.getString(cursor.getColumnIndex(Customers.FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(Customers.LAST_NAME));
            Customer customer = new Customer(id, firstName, lastName);
            customers.add(customer);
        }
        cursor.close();
        close();
        return customers;
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Tables.TABLE, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Tables.ID));
            boolean availability = cursor.getInt(cursor.getColumnIndex(Tables.AVAILABILITY)) == 1;
            Table table = new Table(id, availability);
            tables.add(table);
        }
        cursor.close();

        close();
        return tables;
    }

    public void storeTables(List<Boolean> tables) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(Tables.TABLE, null, null);
        int id = 1;
        for (Boolean available : tables) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Tables.ID, id);
            contentValues.put(Tables.AVAILABILITY, available ? 1 : 0);
            db.insert(Tables.TABLE, null, contentValues);
            id++;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close();
    }

    public void resetTableAvailability() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Tables.AVAILABILITY, 1);
        db.update(Tables.TABLE, cv, null, null);
        close();
    }

    public void setTableAvailability(@NonNull Table table) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Tables.AVAILABILITY, table.available() ? 1 : 0);
        db.update(Tables.TABLE, cv,
                Tables.ID + "=?", new String[]{Integer.toString(table.getId())});
        close();
    }
}
