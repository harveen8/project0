package Util;//package Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionUtil {
    private static Connection conn;
    public static Connection getConnection(){
        if(conn==null) {
            try {
                String url = "jdbc:sqlserver://grocerystore.database.windows.net:1433;database=database1;user=grocery@grocerystore;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
                String username = "grocery";
                String password = "P@SSWORD123";
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
