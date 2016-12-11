package ControllerTestsLogic;

import Helper.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserControllerTestLogic {    
    public boolean getUserPasswordByEmailResult (String email) throws Exception {
        FileReader reader = new FileReader();
        
        String passwordAPI = reader.getValueFromURL("http://localhost:8080/userPassword?email=" + email);
        if (passwordAPI.equals("Exception: Incorrect result size: expected 1, actual 0")) passwordAPI = null;
        String passwordSQL = "";
        boolean cont = true;
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = "SELECT password FROM users WHERE email = '" + email + "' LIMIT 1";
                ResultSet rs = st.executeQuery(sql);
                cont = rs.first();
                
                if (cont) rs.previous();
                
                while (rs.next()) {
                    passwordSQL = rs.getString("password");
                }     
            }
                      
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        if (!cont) passwordSQL = null;
        
        if (passwordSQL == null && passwordAPI == null) return true;
        else if (passwordSQL == null || passwordAPI == null) return false;
        else if (passwordSQL.equals(passwordAPI)) return true;
        return false;
    }
}
