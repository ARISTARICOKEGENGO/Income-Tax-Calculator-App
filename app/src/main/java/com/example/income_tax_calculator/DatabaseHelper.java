package com.example.income_tax_calculator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdatabase.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // New table for EMI calculations
    private static final String EMI_TABLE_NAME = "emiCalculations";
    public static final String COLUMN_EMI_ID = "id";
    public static final String COLUMN_EMI_DATE = "date";
    public static final String COLUMN_EMI_PRINCIPAL = "principal";
    public static final String COLUMN_EMI_INTEREST_RATE = "interest_rate";
    public static final String COLUMN_EMI_TENURE = "tenure";
    public static final String COLUMN_EMI_RESULT = "emi_result";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_EMI_TABLE = "CREATE TABLE " + EMI_TABLE_NAME + "("
                + COLUMN_EMI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMI_DATE + " TEXT,"
                + COLUMN_EMI_PRINCIPAL + " REAL,"
                + COLUMN_EMI_INTEREST_RATE + " REAL,"
                + COLUMN_EMI_TENURE + " INTEGER,"
                + COLUMN_EMI_RESULT + " REAL" + ")";
        db.execSQL(CREATE_EMI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EMI_TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String username, String email, String password) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, username);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public boolean checkUser(String email, String password) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query("users", new String[]{"id"}, "email=? AND password=?",
                     new String[]{email, password}, null, null, null)) {
            return cursor.getCount() > 0;
        }
    }

    public void addEmiCalculation(String date, double principal, double interestRate, int tenure, double emiResult) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMI_DATE, date);
            values.put(COLUMN_EMI_PRINCIPAL, principal);
            values.put(COLUMN_EMI_INTEREST_RATE, interestRate);
            values.put(COLUMN_EMI_TENURE, tenure);
            values.put(COLUMN_EMI_RESULT, emiResult);
            db.insert(EMI_TABLE_NAME, null, values);
        }
    }

    public Cursor getEmiCalculations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(EMI_TABLE_NAME, null, null, null, null, null, COLUMN_EMI_DATE + " DESC");
    }
}