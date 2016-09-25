package com.myxlab.studentsdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by haslina on 9/25/2016.
 */
public class StudentDBHandler extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "STUDENTSDB.db";
    public static final String DATABASE_TABLE_STUDENTS = "Students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "student_name";
    public static final String COLUMN_METRIC = "student_metric";
    public static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + DATABASE_TABLE_STUDENTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_METRIC + " TEXT" + ")";
    ;


    public StudentDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_STUDENTS);
        onCreate(db);
    }


    // ADD C-R-U-D METHODS BELOW TO PERFORM FUNCTIONALITY OVER DATABASE

    public void addStudent(StudentData studentData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, studentData.getStudentName());
        contentValues.put(COLUMN_METRIC, studentData.getMetricNumber());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE_STUDENTS, null, contentValues);
        db.close();
    }

    //method to delete a student by his name
    public boolean deleteStudent(String name) {
        boolean result = false;
        String queryDeleteSTudent = "Select * From " + DATABASE_TABLE_STUDENTS + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryDeleteSTudent, null);
        StudentData studentData = new StudentData();
        if (cursor.moveToFirst()) {
            studentData.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(DATABASE_TABLE_STUDENTS, COLUMN_ID + " = ?", new String[]{String.valueOf(studentData.getId())});
            cursor.close();
            result = true;
        }

        return result;
    }

    public void deleteAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_STUDENTS, null, null);
    }

    //method to find a student by his/her name
    public StudentData findStudentByName(String name) {
        String queryFindStudent = "Select *  From " + DATABASE_TABLE_STUDENTS + " WHERE " + COLUMN_NAME + " = \'" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryFindStudent, null);
        StudentData studentData = new StudentData();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            studentData.setId(Integer.parseInt(cursor.getString(0)));
            studentData.setStudentName(cursor.getString(1));
            studentData.setMetricNumber(cursor.getString(2));
            cursor.close();
        } else {
            studentData = null;
        }
        db.close();
        return studentData;

    }
    //method to find all students in a database table
    public List<StudentData> findAllStudents(){
        List<StudentData> studentDataList = new LinkedList<StudentData>();
        String findAllQuery = "Select * From "+ DATABASE_TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(findAllQuery, null);

        StudentData studentData = null;
        if (cursor.moveToFirst()) {
            do {
                studentData = new StudentData();
                studentData.setId(Integer.parseInt(cursor.getString(0)));
                studentData.setStudentName(cursor.getString(1));
                studentData.setMetricNumber(cursor.getString(2));
                studentDataList.add(studentData);
            }while (cursor.moveToNext());
        }
        return studentDataList;
    }




}
