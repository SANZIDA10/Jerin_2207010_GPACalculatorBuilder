package com.example._207010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DB {
    private Connection connection;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void getConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection( "jdbc:sqlite:sample.db" );
                logger.info("Connected to database");
                createTable();
            }
        }catch(SQLException e){
            logger.info(e.toString());
        }
    }

    private void createTable(){
        getConnection();
        String query = "Create table if not exists note (id integer not null primary key autoincrement, title text not null, content text not null)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.executeUpdate();
            logger.info("Table created");
        }catch(SQLException e){
            logger.info(e.toString());
        }
    }

    private void closeConnection () throws SQLException {
        if(connection != null || !connection.isClosed()){
            connection.close();
        }
    }

     public void insertNote(String title, String content){
        getConnection();
        String query = "Insert into note (title,content) values (?,?)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,title);
            statement.setString(2,content);
            statement.executeUpdate();
            logger.info("Note inserted");
        }catch(SQLException e){
            logger.info(e.toString());
        }
     }
}
