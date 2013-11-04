package pmd.blaster;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.HashMap;

public class DatabaseEngine
{
    public static Connection dbConn = null;
    
    private static final String
        DB_InsertPref
            = "REPLACE INTO PREFERENCES (KEY,VALUE) VALUES (?,?)",
            
        DB_InsertRost
            = "REPLACE INTO ROSTERS (ID,ROSTER,FIRST,LAST,EMAIL,PHONE) "
            + "VALUES (?,?,?,?,?,?);",
            
        DB_RemoveRost
            = "DELETE FROM ROSTERS WHERE ROSTER=?";
    
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
                        + "(ID              INTEGER PRIMARY KEY AUTOINCREMENT,"
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
    
    public static void purgeRosters(boolean confirm, boolean certain, boolean positive)
    {
        try
        {
            if (confirm && certain && positive)
            {
                if (dbConn != null && !dbConn.isClosed())
                {
                    dbConn.createStatement().executeUpdate(
                            "DROP TABLE IF EXISTS ROSTERS;");
                } 
                
                else
                    System.out.println("[ERROR] No database connection.");

            }
        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not purge rosters.");
        } 
    }

    public static void purgePreferences(boolean confirm, boolean certain, boolean positive)
    {
        try
        {
            if (confirm && certain && positive)
            {
                if (dbConn != null && !dbConn.isClosed())
                {
                    dbConn.createStatement().executeUpdate(
                            "DROP TABLE IF EXISTS PREFERENCES;");
                } 
                
                else
                    System.out.println("[ERROR] No database connection.");

            }
        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not purge preferences.");
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
    
    public static void savePreference(String Key, String Value)
    {
        try
        {
            if(dbConn != null && !dbConn.isClosed())
            {
                PreparedStatement prefStatement 
                        = dbConn.prepareStatement(DB_InsertPref);
                
                prefStatement.setString(1, Key);
                prefStatement.setString(2, Value);
                
                prefStatement.executeUpdate();
            }
            
            else
                System.out.println("[ERROR] No database connection.");
            
        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not insert preference into Database.");
        }
    }
    
    public static HashMap<String, LinkedList<Contact>> getRosters()
    {      
        HashMap<String, LinkedList<Contact>> hashMapReturn = new HashMap<>();
        
        try
        {
            if (dbConn != null && !dbConn.isClosed())
            {
                ResultSet rs = dbConn.createStatement().executeQuery(
                        "SELECT * FROM ROSTERS;");
                
                while(rs.next())
                {
                    String r = rs.getString("ROSTER"),
                            f = rs.getString("FIRST"),
                            l = rs.getString("LAST"),
                            e = rs.getString("EMAIL"),
                            p = rs.getString("PHONE");
                    
                    if(!hashMapReturn.containsKey(r))
                        hashMapReturn.put(r, new LinkedList<Contact>());
                    
                    hashMapReturn.get(r).add(new Contact(f, l, e, p));
                }
            }
            
            else
                System.out.println("[ERROR] No database connection.");

        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not retrieve rosters from Database.");
        }    
        
        return hashMapReturn;
    }
    
    public static void saveRosters(String rosterName, LinkedList<Contact> roster) 
    {
        try
        {
            if(dbConn != null && !dbConn.isClosed())
            {
                PreparedStatement remRost = dbConn.prepareStatement(DB_RemoveRost);
                
                remRost.setString(1, rosterName);
                
                remRost.executeUpdate();
                
                for(Contact c : roster)
                {
                    PreparedStatement addRoster = dbConn.prepareStatement
                            (DB_InsertRost);
                    
                    addRoster.setString(2, rosterName);
                    addRoster.setString(3, c.first);
                    addRoster.setString(4, c.last);
                    addRoster.setString(5, c.email);
                    addRoster.setString(6, c.phone);

                    addRoster.executeUpdate();
                }
            }
            
            else
                System.out.println("[WARNING] No database connection.");

        } catch (SQLException e)
        {
            System.out.println("[ERROR] Could not add roster to the database.");
        }        
    }
}