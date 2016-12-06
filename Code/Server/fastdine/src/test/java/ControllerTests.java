import dataEntities.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ControllerTests {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void getReservationsByRestaurantIdAndShiftTest () throws Exception {
        assertTrue(getReservationsByRestaurantIdResult("0"));
        assertTrue(getReservationsByRestaurantIdResult("1"));
        assertTrue(getReservationsByRestaurantIdAndShiftResult("0","3"));
        assertTrue(getReservationsByRestaurantIdAndShiftResult("1","3"));
        assertTrue(getReservationsByRestaurantIdAndShiftResult("1","0"));
        exception.expect(RuntimeException.class);
        getReservationsByRestaurantIdAndShiftResult("test","test");
    }
    
    //TODO - complete test
    @Test
    public void newReservationTest () throws Exception {
        
    }
    
    @Test
    public void getRestaurantByNameTest () throws Exception {
        assertTrue(getRestaurantByNameResult("de"));
        assertTrue(getRestaurantByNameResult(""));
        exception.expect(NullPointerException.class);
        getRestaurantByNameResult(null);
    }
       
    @Test
    public void getAllRestaurantsTest () throws Exception {
        List<Restaurant> restosAPI = new ArrayList<Restaurant>();
        List<Restaurant> restosSQL = new ArrayList<Restaurant>();
      
        String value = getValueFromURL("http://localhost:8080/restaurants");

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
    
    @Test
    public void getTablesByRestaurantIdAndShiftAndDateTest() throws Exception {
        assertTrue(getTablesByRestaurantIdAndShiftAndDateResult(0, 1, "0"));
        assertTrue(getTablesByRestaurantIdAndShiftAndDateResult(0, 1, "2017-01-01"));
        assertTrue(getTablesByRestaurantIdAndShiftAndDateResult(1, 1, "0"));
        assertTrue(getTablesByRestaurantIdAndShiftAndDateResult(1, 1, "2017-01-01"));
        exception.expect(NullPointerException.class);        
        getTablesByRestaurantIdAndShiftAndDateResult(0, 0, "0");
    }
    
    @Test
    public void getUserPasswordByEmailTest() throws Exception {
        assertTrue(getUserPasswordByEmailResult("1"));
        assertTrue(getUserPasswordByEmailResult("gebruiker1@odisee.be"));
        assertTrue(getUserPasswordByEmailResult(null));
        exception.expect(NullPointerException.class);
        getUserPasswordByEmailResult("");
    }
    
    //TODO - complete test
    @Test
    public void newUserTest() throws Exception {
        String email = "testgebruiker@student.odisee.be";
        String type = "customer";
        String password = "123";

        //assertTrue(Boolean.parseBoolean(getValueFromURL("http://localhost:8080/newUser?email=testgebruiker1&type=customer&password=123")));
        //assertFalse(Boolean.parseBoolean(getValueFromURL("http://localhost:8080/newUser?email=testgebruiker1&type=customer&password=123")));
        
        // Vraag gebruiker op via sql
        // Id != null bool = true else return false;
        // (bool) remove user where id = Id
    }
    
    private boolean getRestaurantByNameResult (String restaurantName) throws Exception  {
        List<Restaurant> restosAPI = new ArrayList<Restaurant>();
        List<Restaurant> restosSQL = new ArrayList<Restaurant>();
      
        String value = getValueFromURL("http://localhost:8080/restaurant?name=" + restaurantName);

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

            con.close();
        }
        catch (Exception e) {
            throw e;
        }
        
        boolean result;
        
        if (restosSQL.size() == restosAPI.size()) result = true; else return false;
        
        int x = 0;
        for (Restaurant r : restosSQL)
        {
            result = (r.toString().equals(restosAPI.get(x++).toString())) ? true : false;
            if (!result) break;
        }
        
        return result;
    }
    
    private boolean getTablesByRestaurantIdAndShiftAndDateResult(int shift, int id, String date) throws Exception {
        List<Table> tablesAPI = new ArrayList<Table>();
        List<Table> tablesSQL = new ArrayList<Table>();
      
        String value = getValueFromURL("http://localhost:8080/tables?id=" + id + "&shift=" + shift + "&date=" + date);

        JSONArray mJsonArray = new JSONArray(value);
        JSONObject mJsonObject = new JSONObject();

        for (int i = 0; i < mJsonArray.length(); i++) {
            mJsonObject = mJsonArray.getJSONObject(i);
            
            int tableId = mJsonObject.getInt("id");
            int seats = mJsonObject.getInt("seats");     

            tablesAPI.add(new Table(tableId, seats));
        };
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123");

            Statement st = con.createStatement();
            String sql = "select tables.id as tablesID, tables.seats as tablesSeats from tables " +
            "where tables.restaurant_id = " + id + " " +
            "AND tables.id NOT IN (select tables.id from restaurants " +
            "inner join tables on restaurants.id = tables.restaurant_id " +
            "inner join tables_has_reservations on tables.id = tables_has_reservations.table_id " +
            "inner join reservations on tables_has_reservations.reservation_id = reservations.id " +
            "where restaurants.id = " + id +
            " AND reservations.date = '" + date + "'" +
            " AND reservations.shift = " + shift + ");";
            ResultSet rs = st.executeQuery(sql);

           
            while (rs.next()) { 
                int tableId = rs.getInt("tablesID");
                int seats = rs.getInt("tablesSeats");   
                
                tablesSQL.add(new Table(tableId, seats));
            }

            con.close();
                      
        }
        catch (Exception e) {
            throw e;
        }
        
        boolean result = false;
        
        if (tablesSQL.size() == tablesAPI.size()) {    
            int x = 0;
            for (Table i : tablesSQL)
            {
                result = (i.toString().equals(tablesAPI.get(x++).toString())) ? true : false;
                if (!result) break;
            }
        }
        
        return result;
    }
    
    private boolean getUserPasswordByEmailResult (String email) throws Exception {
        String passwordAPI = getValueFromURL("http://localhost:8080/userPassword?email=" + email);
        if (passwordAPI.equals("Exception: Incorrect result size: expected 1, actual 0")) passwordAPI = null;
        String passwordSQL = "";
        boolean cont = true;
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123");

            Statement st = con.createStatement();
            String sql = "SELECT password FROM users WHERE email = '" + email + "' LIMIT 1";
            ResultSet rs = st.executeQuery(sql);
            cont = rs.first();
            
            if (cont) rs.previous();

            while (rs.next()) { 
                passwordSQL = rs.getString("password");     
            }

            con.close();
                      
        }
        catch (Exception e) {
            throw e;
        }
        
        if (!cont) passwordSQL = null;
        
        if (passwordSQL == null && passwordAPI == null) return true;
        else if (passwordSQL == null || passwordAPI == null) return false;
        else if (passwordSQL.equals(passwordAPI)) return true;
        return false;
    }
    
    private boolean getReservationsByRestaurantIdResult (String id) throws Exception {
        String valueAPI = getValueFromURL("http://localhost:8080/reservations?id=" + id);
        String valueSQL = "[";
        
        boolean cont = true;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123");

            Statement st = con.createStatement();
            String sql = ("select users.name, users.telephone, reservations.date, reservations.shift, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = '" + id + "';");
            ResultSet rs = st.executeQuery(sql);
            cont = rs.first();
            
            if (cont) rs.previous();
           
            while (rs.next()) {
                String name = rs.getString("name");
                String telephone = rs.getString("telephone");
                String date  = rs.getDate("date").toString();
                String shift  = Integer.toString(rs.getInt("shift"));
                String tablenumber = Integer.toString(rs.getInt("tablenumber"));
                
                valueSQL +=  "\"" + name + "\",\"" + telephone + "\",\"" + date + "\",\"" + shift + "\",\"" + tablenumber + "\",";
            }
            
            con.close();
        }
        catch (Exception e) {
            throw e;
        }
        
        valueSQL = valueSQL.substring(0,valueSQL.length() - 1);        
        valueSQL += "]";
        valueSQL = valueSQL.replaceAll("\"null\"", "null");
        
        if (!cont) valueSQL = null;
        
        if (valueSQL == null && valueAPI == null) return true;
        else if (valueSQL == null || valueAPI == null) return false;
        else if (valueSQL.equals(valueAPI)) return true;
        return false;
    }
    
    private boolean getReservationsByRestaurantIdAndShiftResult (String id, String shift) throws Exception {
        String valueAPI = getValueFromURL("http://localhost:8080/reservations?id=" + id + "&shift=" + shift);
        String valueSQL = "[";
        
        boolean cont = true;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123");

            Statement st = con.createStatement();
            String sql = ("select users.name, users.telephone, reservations.date, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + id +
                " AND reservations.shift = " + shift + ";");
            ResultSet rs = st.executeQuery(sql);
            
            cont = rs.first();
            
            if (cont) rs.previous();
           
            while (rs.next()) {
                String name = rs.getString("name");
                String telephone = rs.getString("telephone");
                String date  = rs.getDate("date").toString();
                String tablenumber = Integer.toString(rs.getInt("tablenumber"));
                valueSQL +=  "\"" + name + "\",\"" + telephone + "\",\"" + date + "\",\"" + tablenumber + "\",";
            }
            
            con.close();
        }
        catch (Exception e) {
            throw e;
        }
        
        valueSQL = valueSQL.substring(0,valueSQL.length() - 1);
        valueSQL += "]";
        valueSQL = valueSQL.replaceAll("\"null\"", "null");
        
        if (!cont) valueSQL = null;
        
        if (valueSQL == null && valueAPI == null) return true;
        else if (valueSQL == null || valueAPI == null) return false;
        else if (valueSQL.equals(valueAPI)) return true;
        return false;
    }
    
    private String getValueFromURL(String urlRequest) {
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
             
            String output = br.readLine();

            conn.disconnect();
            return output;
            
        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }       
        
        return null;
    }
    
    private void showData() {
        String value = getValueFromURL("http://localhost:8080/reservations?id=1&shift=3");
        System.out.println(value);
    }
}