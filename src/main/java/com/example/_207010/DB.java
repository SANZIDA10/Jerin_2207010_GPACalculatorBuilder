package com.example._207010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DB
{
    private Connection a;
    private Logger b = Logger.getLogger(this.getClass().getName());

    public void getConnection()
    {
        try
        {
            if(a == null || a.isClosed())
            {
                a = DriverManager.getConnection( "jdbc:sqlite:sample.db" );
                b.info("Connected to database");
                createTable();
            }
        }
        catch(SQLException c)
        {

            b.info("Database connection failed (Continuing): " + c.toString());
        }
    }

    private void createTable()
    {
        getConnection();
        if (a == null) return;
        String c = "CREATE TABLE IF NOT EXISTS course (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "code TEXT NOT NULL," +
                "credit REAL NOT NULL," +
                "teacher1 TEXT," +
                "teacher2 TEXT," +
                "grade TEXT NOT NULL" +
                ")";
        try(PreparedStatement d = a.prepareStatement(c))
        {
            d.executeUpdate();
            b.info("Table created");
        }
        catch(SQLException e)
        {
            b.info(e.toString());
        }
    }

    private void closeConnection () throws SQLException
    {
        if(a != null || !a.isClosed())
        {
            a.close();
        }
    }

    public void insertCourse(CourseController.Course c)
    {
        getConnection();
        if (a == null) return;
        String d = "INSERT INTO course (name, code, credit, teacher1, teacher2, grade) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement e = a.prepareStatement(d))
        {
            e.setString(1, c.getName());
            e.setString(2, c.getCode());
            e.setDouble(3, c.getCredit());
            e.setString(4, c.getTeacher1());
            e.setString(5, c.getTeacher2());
            e.setString(6, c.getGrade());
            e.executeUpdate();
            b.info("Course inserted");
        }
        catch(SQLException f)
        {
            b.info(f.toString());
        }
    }

    public void updateCourse(CourseController.Course c)
    {
        getConnection();
        if (a == null) return;
        String d = "UPDATE course SET name = ?, code = ?, credit = ?, teacher1 = ?, teacher2 = ?, grade = ? WHERE id = ?";
        try(PreparedStatement e = a.prepareStatement(d))
        {
            e.setString(1, c.getName());
            e.setString(2, c.getCode());
            e.setDouble(3, c.getCredit());
            e.setString(4, c.getTeacher1());
            e.setString(5, c.getTeacher2());
            e.setString(6, c.getGrade());
            e.setInt(7, c.getId());
            e.executeUpdate();
            b.info("Course updated");
        }
        catch(SQLException f)
        {
            b.info(f.toString());
        }
    }

    public void deleteCourse(int c)
    {
        getConnection();
        if (a == null) return;
        String d = "DELETE FROM course WHERE id = ?";
        try(PreparedStatement e = a.prepareStatement(d))
        {
            e.setInt(1, c);
            e.executeUpdate();
            b.info("Course deleted");
        }
        catch(SQLException f)
        {
            b.info(f.toString());
        }
    }
}