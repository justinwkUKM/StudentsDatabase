package com.myxlab.studentsdatabase;

/**
 * Created by haslina on 9/25/2016.
 */
public class StudentData {
    private int id;
    private String studentName;
    private String metricNumber;

    public StudentData() {
    }

    public StudentData(String studentName, String metricNumber) {
        this.studentName = studentName;
        this.metricNumber = metricNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMetricNumber() {
        return metricNumber;
    }

    public void setMetricNumber(String metricNumber) {
        this.metricNumber = metricNumber;
    }

    @Override
    public String toString() {
        return "id =  " + id + ", Student Name = " + studentName + ", Metric Number = " + metricNumber + "\n";
    }
}
