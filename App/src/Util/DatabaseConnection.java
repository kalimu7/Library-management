package Util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Class.forName;

public class DatabaseConnection {
    private static final String DatabaseUrl = "jdbc:mysql://localhost:3306/library";
    private static final String username = "root";
    private static final String password = "";

    public void DatabaseUtil(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            throw new RuntimeException("Something went wrong while trying to connect with database" + e);
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseUrl,username,password);
    }

}
