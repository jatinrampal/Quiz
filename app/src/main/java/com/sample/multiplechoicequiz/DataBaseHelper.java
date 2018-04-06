package com.sample.multiplechoicequiz;
/**
 * Created by Jatin Rampal on 02/04/2018.
 */
/**
 *   Database Helper Call used to create , upgrade database ,
 *   create table and perform C.R.U.D.  operations
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private String question;
    private String option;
    private String answer;
    private int totalQuestions;

    // Database Name
    public static String DATABASE_NAME = "quiz_database";
    // Current version of database
    private static final int DATABASE_VERSION = 1;


    // TABLE-1 QUESTIONS
    private static final String TABLE_QUESTIONS = "questions";
    private static final String QID = "qid";
    private static final String QUESTION = "question";

    public static String TAG = "my_tag";

    // Question Table Create Query in this string
    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE "
            + TABLE_QUESTIONS + "(" + QID
            + " INTEGER PRIMARY KEY ," + QUESTION + " TEXT );";

    // TABLE-2 OPTIONS
    private static final String TABLE_OPTIONS = "options";
    private static final String OID = "oid";
    private static final String OPTION = "option";


    // Option Table Create Query in this string
    private static final String CREATE_TABLE_OPTIONS = "CREATE TABLE "
            + TABLE_OPTIONS + "(" + OID
            + " INTEGER  ," + OPTION + " TEXT, "
            + QID + " INTEGER );";

    //TABLE-3 ANSWERS
    private static final String TABLE_ANSWERS = "answers";
    private static final String ANSWER = "answer";

    private static final String CREATE_TABLE_ANSWERS = "CREATE TABLE "
            + TABLE_ANSWERS + "(" + QID
            + " INTEGER PRIMARY KEY ," + ANSWER + " TEXT );";




    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method is called by system if the database is accessed but not yet
     * created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_OPTIONS);
        db.execSQL(CREATE_TABLE_ANSWERS);
    }

    /**
     * This method is called when any modifications in database are done like
     * version is updated or database schema is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_QUESTIONS); // drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ANSWERS);
        onCreate(db);
    }

    public void addQuestion(int id, String ques) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(QID, id);
        values.put(QUESTION, ques);

        // insert row in client table

        long insert = db.insert(TABLE_QUESTIONS, null, values);
    }

    public void addOption(int id, String option, int qid) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(OID, id);
        values.put(OPTION, option);
        values.put(QID, qid);

        // insert row in client table

        long insert = db.insert(TABLE_OPTIONS, null, values);
    }

    public void addAnswer(int id, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(QID, id);
        values.put(ANSWER, answer);

        // insert row in client table

        long insert = db.insert(TABLE_ANSWERS, null, values);
    }

    public int totalQuestions() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS ;
        Cursor c = db.rawQuery(selectQuery, null);

        totalQuestions = c.getCount();
        return totalQuestions;
    }



    public String updateQuestion(int qid) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT * FROM client WHERE id = ?;
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS + " WHERE "
                + QID + " = " + qid;
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        question = c.getString(c.getColumnIndex(QUESTION));

        return question;
    }


    public String updateOptions(int qid, int oid) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_OPTIONS + " WHERE "
                + OID + " = " + oid + " AND " + QID + " = " + qid;
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        option = c.getString(c.getColumnIndex(OPTION));

        return option;
    }

    public String checkAnswer(int qid) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SELECT * FROM client WHERE id = ?;
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWERS + " WHERE "
                + QID + " = " + qid ;
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        answer = c.getString(c.getColumnIndex(ANSWER));
        return answer;
    }






    /**
     * Used to get detail of entire database and save in array list of data type
     * Clients
     *
     * @return

    public List<Client> getAllClientsList() {
        List<Client> clientArrayList = new ArrayList<Client>();

        String selectQuery = "SELECT  * FROM " + TABLE;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Client clients = new Client();
                clients.id = c.getInt(c.getColumnIndex(KEY_ID));
                clients.phone_number = c.getString(c
                        .getColumnIndex(PHONENUMBER));
                clients.name = c.getString(c.getColumnIndex(NAME));

                // adding to Clients list
                clientArrayList.add(clients);
            } while (c.moveToNext());
        }
        return clientArrayList;

    }
    */
}