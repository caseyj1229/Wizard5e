import java.sql.*;

public class Connector {
    Connection conn;
    //Statement stmt;

    public boolean open(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wizardspells?useSSL=false", "root", "pw");
            //System.out.println("Successful Connection");
            //stmt = conn.createStatement();
        }
        catch(Exception err){
            System.out.print(err.getMessage());
        }

        if(conn == null){
            return false;
        }
        return true;
    }
}
