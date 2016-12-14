package ControllerTestsLogic;

import Helper.FileReader;
import dataEntities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserControllerTestLogic {    
    public boolean getUserByEmailResult (String email) throws Exception {
        FileReader reader = new FileReader();        
        User userAPI = null;
        User userSQL = null;
        boolean cont = true;
        
        String value = "[" + reader.getValueFromURL("http://localhost:8090/user?email=" + email) + "]";
        
        JSONArray mJsonArray = new JSONArray(value);
        JSONObject mJsonObject = new JSONObject();

        for (int i = 0; i < mJsonArray.length(); i++) {
            mJsonObject = mJsonArray.getJSONObject(i);

            int id = mJsonObject.getInt("id");
            String name = mJsonObject.getString("name");
            String _email = mJsonObject.getString("email");
            String telephone = mJsonObject.getString("telephone");
            String type = mJsonObject.getString("type");
            String password = mJsonObject.getString("password");
            

            userAPI = new User(id, name, _email, telephone, type, password);
        }
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = "SELECT * FROM users WHERE email = '" + email + "' LIMIT 1";
                ResultSet rs = st.executeQuery(sql);
                cont = rs.first();
                
                if (cont) rs.previous();
                
                while (rs.next()) {
                    userSQL = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("telephone"), rs.getString("type"), rs.getString("password"));
                }     
            }
                      
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        if (!cont) userSQL = null;
        
        if (userSQL.toString() == null && userAPI.toString() == null) return true;
        else if (userSQL == null || userAPI == null) return false;
        else if (userSQL.toString().equals(userAPI.toString())) return true;
        return false;
    }
}
