package ControllerTestsLogic;

import Helper.FileReader;
import dataEntities.Restaurant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class RestaurantControllerTestLogic {
    private final FileReader reader;
    
    public RestaurantControllerTestLogic() {
        reader = new FileReader();
    }
    
    public boolean getRestaurantByNameResult (String restaurantName) throws Exception  {
        List<Restaurant> restosAPI = new ArrayList<>();
        List<Restaurant> restosSQL = new ArrayList<>();
        
        String value = reader.getValueFromURL("http://localhost:8080/restaurant?name=" + restaurantName);

        JSONArray mJsonArray = new JSONArray(value);
        JSONObject mJsonObject = new JSONObject();

        for (int i = 0; i < mJsonArray.length(); i++) {
            mJsonObject = mJsonArray.getJSONObject(i);

            int id = mJsonObject.getInt("id");
            String name = mJsonObject.getString("name");
            String location = mJsonObject.getString("location");
            String email = mJsonObject.getString("email");
            String telephone = mJsonObject.getString("telephone");
            int seats = mJsonObject.getInt("seats");

            restosAPI.add(new Restaurant(id, name, location, email, telephone, seats));
        }
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = ("SELECT * FROM restaurants WHERE name LIKE '%" + restaurantName + "%'");
                ResultSet rs = st.executeQuery(sql);
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String email = rs.getString("email");
                    String telephone = rs.getString("telephone");
                    int seats = rs.getInt("seats");
                    
                    restosSQL.add(new Restaurant(id, name, location, email, telephone, seats));
                }
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        boolean result;
        
        if (restosSQL.size() == restosAPI.size()) result = true; else return false;
        
        int x = 0;
        for (Restaurant r : restosSQL)
        {
            result = (r.toString().equals(restosAPI.get(x++).toString()));
            if (!result) break;
        }
        
        return result;
    }
    
    public boolean getRestaurantByIdResult (String id) throws Exception  {
        Restaurant restaurant1;
        Restaurant restaurant2;
        
        String value = reader.getValueFromURL("http://localhost:8080/restaurantid?id=" + id);

        JSONArray mJsonArray = new JSONArray(value);
        JSONObject mJsonObject = new JSONObject();
        
        int _id = mJsonObject.getInt("id");
        String name = mJsonObject.getString("name");
        String location = mJsonObject.getString("location");
        String email = mJsonObject.getString("email");
        String telephone = mJsonObject.getString("telephone");
        int seats = mJsonObject.getInt("seats");

        restaurant1 = new Restaurant(_id, name, location, email, telephone, seats);
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try (Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123")) {
                Statement st = con.createStatement();
                String sql = ("SELECT * FROM restaurants WHERE id = '" + id + "';");
                ResultSet rs = st.executeQuery(sql);
                
                rs.next();
                _id = rs.getInt("id");
                name = rs.getString("name");
                location = rs.getString("location");
                email = rs.getString("email");
                telephone = rs.getString("telephone");
                seats = rs.getInt("seats");
                
                restaurant2 = new Restaurant(_id, name, location, email, telephone, seats);
            }
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            throw e;
        }
        
        return restaurant1.toString().equals(restaurant2.toString());
    }
}
