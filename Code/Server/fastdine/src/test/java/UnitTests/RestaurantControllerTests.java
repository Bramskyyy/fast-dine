package UnitTests;

import ControllerTestsLogic.RestaurantControllerTestLogic;
import Helper.FileReader;
import dataEntities.Restaurant;
import fastdine.RestaurantController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RestaurantControllerTests {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testGetAllRestaurants() {
        RestaurantController rc = new RestaurantController();
        exception.expect(RuntimeException.class);      
        rc.getAllRestaurants();
    }
    
    @Test
    public void testGetRestaurantById() {
        RestaurantController rc = new RestaurantController();
        String id = "1";
        exception.expect(RuntimeException.class);             
        rc.getRestaurantById(id);
    }
    
    @Test
    public void testGetRestaurantByName() {
        RestaurantController rc = new RestaurantController();
        String name = "test";
        exception.expect(RuntimeException.class);             
        rc.getRestaurantByName(name);
    }
    
    @Test
    public void getRestaurantByNameTest () throws Exception {
        RestaurantControllerTestLogic testLogic = new RestaurantControllerTestLogic();
        
        assertTrue(testLogic.getRestaurantByNameResult("de"));
        assertTrue(testLogic.getRestaurantByNameResult(""));
        exception.expect(NullPointerException.class);
        testLogic.getRestaurantByNameResult(null);
    }
       
    @Test
    public void getAllRestaurantsTest () throws Exception {
        List<Restaurant> restosAPI = new ArrayList<Restaurant>();
        List<Restaurant> restosSQL = new ArrayList<Restaurant>();
        
        FileReader reader = new FileReader();
        
        String value = reader.getValueFromURL("http://localhost:8080/restaurants");

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
        };
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123");

            Statement st = con.createStatement();
            String sql = ("SELECT * FROM restaurants;");
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

            con.close(); 
        }
        catch (Exception e) {
            throw e;
        }
        
        assertEquals(restosSQL.size(), restosAPI.size());
        
        int x = 0;
        for (Restaurant r : restosSQL) assertEquals(r.toString(), restosAPI.get(x++).toString());
    }
    
    //TODO - complete test
    @Test
    public void getRestaurantByIdTest () throws Exception {
    
    }
}
