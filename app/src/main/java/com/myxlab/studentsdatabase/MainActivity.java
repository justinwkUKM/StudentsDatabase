package com.myxlab.studentsdatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btAdd, btDelete, btFind;
    EditText etStudent, etMetricNo;
    ListView studentLV;
    List<StudentData> studentDataList;
    ArrayAdapter studentListAdapter;
    StudentDBHandler studentDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btAdd = (Button) findViewById(R.id.buttonADD);
        btDelete = (Button) findViewById(R.id.buttonDelete);
        btFind = (Button) findViewById(R.id.buttonFind);
        etStudent = (EditText) findViewById(R.id.editTextStudentName);
        etMetricNo = (EditText) findViewById(R.id.editTextMetric);
        studentLV = (ListView) findViewById(R.id.lvShowStudentRecords);

        studentDBHandler = new StudentDBHandler(this, StudentDBHandler.DATABASE_NAME, null, StudentDBHandler.DATABASE_VERSION);
        studentDBHandler.addStudent(new StudentData("Waqas", "P80535"));
        /*studentDBHandler.addStudent(new StudentData("Amir", "P80535"));
        studentDBHandler.addStudent(new StudentData("Amin", "P80535"));*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //constructor for the DB
        final StudentDBHandler studentDBHandler = new StudentDBHandler(this, StudentDBHandler.DATABASE_NAME, null, StudentDBHandler.DATABASE_VERSION);


        studentLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value = (String) studentLV.getItemAtPosition(position);
                //call the method findstudentbyname which requires a string a an input.
                //the methid will return a StudentData object
                StudentData studentData = studentDBHandler.findStudentByName(value);
                if (studentData != null){
                    Snackbar.make(view, "Student Metric Number : "+ studentData.getMetricNumber(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(view, "No Match Found in the Student Database", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        });

        studentLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //update the database here
                return true;
            }
        });

        refreshList();

    }

    private void refreshList() {
        StudentDBHandler studentDBHandler = new StudentDBHandler(this, StudentDBHandler.DATABASE_NAME, null, StudentDBHandler.DATABASE_VERSION);

        studentDataList = studentDBHandler.findAllStudents();
        List lisOfNames = new ArrayList();
        for (int i = 0; i < studentDataList.size(); i++) {
            lisOfNames.add(studentDataList.get(i).getStudentName());
        }

        studentListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lisOfNames);
        studentLV.setAdapter(studentListAdapter);


    }

    public void addStudent(View view) {
        String studentName = etStudent.getText().toString();
        String studentMetric = etMetricNo.getText().toString();
        if (studentMetric.isEmpty() || studentName.isEmpty()) {
            Snackbar.make(view, "Please Fill In The Fields Above", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            // we need to save the data in the database
            StudentDBHandler s = new StudentDBHandler(this, StudentDBHandler.DATABASE_NAME, null, StudentDBHandler.DATABASE_VERSION);
            StudentData studentData = new StudentData(studentName, studentMetric);
            s.addStudent(studentData);
            etMetricNo.setText("");
            etStudent.setText("");
            refreshList();
        }
    }

    public void deleteStudent(View view) {
        //delete a student by his/her name
        StudentDBHandler s = new StudentDBHandler(this, StudentDBHandler.DATABASE_NAME, null, StudentDBHandler.DATABASE_VERSION);
        boolean result = s.deleteStudent(etStudent.getText().toString());
        if(result){
            Snackbar.make(view, "Record deleted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            Snackbar.make(view, "Record not found", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        refreshList();
    }

    public void findStudent(View view) {

        String value = etStudent.getText().toString();
        StudentData studentData = studentDBHandler.findStudentByName(value);
        if (studentData != null){
            Snackbar.make(view, "Student Metric Number : "+ studentData.getMetricNumber(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            Snackbar.make(view, "No Match Found in the Student Database", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_alllll) {

            StudentDBHandler s = new StudentDBHandler(this, StudentDBHandler.DATABASE_NAME, null, StudentDBHandler.DATABASE_VERSION);
            s.deleteAllStudents();
            refreshList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
