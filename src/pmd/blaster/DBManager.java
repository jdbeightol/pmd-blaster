package pmd.blaster;

import java.sql.*;

public class DBManager
{
    public static Connection dbConn = null;
    
    private static void connectToDB()
    {
        try
        {
            if(dbConn != null && dbConn.isClosed())
                dbConn.close();
            
            Class.forName("org.sqlite.JDBC");
            dbConn = DriverManager.getConnection("jdbc:sqlite:data.db");
        } catch (ClassNotFoundException | SQLException e)
        {
             System.out.println("[ERROR] Could not connect to the database.");           
        }
    }
    
    private static void createDB(boolean forceCreate)
    {
        try
        {
            if(dbConn != null && !dbConn.isClosed())
            {
                dbConn.createStatement().executeUpdate("CREATE TABLE "
                        + ((forceCreate)?"":"IF NOT EXISTS ") + "PREFERENCES "
                        + "(KEY             VARCHAR(128) PRIMARY KEY NOT NULL, "
                        + " VALUE           VARCHAR(128) NOT NULL);");

                dbConn.createStatement().executeUpdate("CREATE TABLE "
                        + ((forceCreate)?"":"IF NOT EXISTS ") + "ROSTERS "
                        + "(ID              INT PRIMARY KEY NOT NULL,"
                        + " ROSTER          VARCHAR(128) NOT NULL, "
                        + " FIRST           VARCHAR(32) NOT NULL, "
                        + " LAST            VARCHAR(32), "
                        + " EMAIL           VARCHAR(128),"
                        + " PHONE           VARCHAR(32));");
            }
            else
                System.out.println("[ERROR] No database connection.");
            
        } catch (SQLException e)
        {
             System.out.println("[ERROR] Could not create the database.");   
             e.printStackTrace();
        }
    }
    
    public static void closeDB()
    {
        try
        {
            if(dbConn != null && !dbConn.isClosed())
                dbConn.close();
            
            else
                System.out.println("[WARNING] No database connection.");

        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not close database connection.");
        }
    }
    
    public static void initDB(boolean forceCreate)
    {
        try
        {
            if((dbConn == null)?true:dbConn.isClosed())
                connectToDB();
            
            if(dbConn != null && !dbConn.isClosed())
            {
                createDB(forceCreate);
            }

        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not initialize the DB Connection.");
        }
    }
    
    public static void savePreference(String Key, String Value)
    {
        try
        {
            if(dbConn != null && !dbConn.isClosed())
                dbConn.createStatement().executeUpdate("REPLACE INTO PREFERENCES"
                        + "(KEY,VALUE) VALUES(\"" + Key + "\",\"" + Value +"\" );");
            
            else
                System.out.println("[ERROR] No database connection.");
            
        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not insert preference into Database.");
            e.printStackTrace();
        }
    }
    
    public static String getPreference(String Key)
    {
        String ret = "";
        
        try
        {
            if (dbConn != null && !dbConn.isClosed())
            {
                ResultSet rs = dbConn.createStatement().executeQuery(
                        "SELECT * FROM PREFERENCES WHERE KEY = \"" + Key + "\";");
                while(rs.next())
                    ret = rs.getString("VALUE");
            }
            
            else
                System.out.println("[ERROR] No database connection.");

        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not retrieve preference from Database.");
        }
        
        return ret;
    }
}