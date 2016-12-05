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
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ControllerTests {    
    /* ReservationController Tests
    getReservationsByRestaurantId - TODO check id=0
    getReservationsByRestaurantIdAndShift - TODO check id=0
    newReservation - TODO
    */
    @Test
    public void getReservationsByRestaurantId () throws Exception
    {
        String id = "1";
        
        String valueAPI = getValueFromURL("http://localhost:8080/reservations?id=" + id);
        String valueSQL = "[";
        
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
           
            while (rs.next()) {
                String name = rs.getString("name");
                String telephone = rs.getString("telephone");
                String date  = rs.getDate("date").toString();
                String shift  = Integer.toString(rs.getInt("shift"));
                String tablenumber = Integer.toString(rs.getInt("tablenumber"));
                
                valueSQL +=  "\"" + name + "\",\"" + telephone + "\",\"" + date + "\",\"" + shift + "\",\"" + tablenumber + "\",";
            }
            
            valueSQL = valueSQL.substring(0,valueSQL.length() - 1);
            valueSQL += "]";
            valueSQL = valueSQL.replaceAll("\"null\"", "null");

            con.close();
        }
        catch (Exception e) {
            throw e;
        }
        
        assertEquals(valueSQL, valueAPI);
    }
    
    @Test
    public void getReservationsByRestaurantIdAndShiftTest () throws Exception
    {
        String id = "1";
        String shift = "3";
        
        String valueAPI = getValueFromURL("http://localhost:8080/reservations?id=" + id + "&shift=" + shift);
        String valueSQL = "[";
        
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
           
            while (rs.next()) {
                String name = rs.getString("name");
                String telephone = rs.getString("telephone");
                String date  = rs.getDate("date").toString();
                String tablenumber = Integer.toString(rs.getInt("tablenumber"));
                valueSQL +=  "\"" + name + "\",\"" + telephone + "\",\"" + date + "\",\"" + tablenumber + "\",";
            }
            
            valueSQL = valueSQL.substring(0,valueSQL.length() - 1);
            valueSQL += "]";
            valueSQL = valueSQL.replaceAll("\"null\"", "null");
            
            con.close();
        }
        catch (Exception e) {
            throw e;
        }
        
        assertEquals(valueSQL, valueAPI);
    }
    
    @Test
    public void newReservationTest () throws Exception
    {
        assertEquals("TRUE","TRUE");
    }
    
    /* RestaurantController Tests
    getRestaurantByName - done
    getAllRestaurants - done
    */
    @Test
    public void getRestaurantByNameTest () throws Exception
    {
        List<Restaurant> restosAPI = new ArrayList<Restaurant>();
        List<Restaurant> restosSQL = new ArrayList<Restaurant>();
        
        String restaurantName = "de";
      
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
        
        assertEquals(restosSQL.size(), restosAPI.size());
        
        int x = 0;
        for (Restaurant r : restosSQL) assertEquals(r.toString(), restosAPI.get(x++).toString());
    }
    
    @Test
    public void getAllRestaurantsTest () throws Exception
    {
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
    
    /* TableController Tests
    getTablesByRestaurantIdAndShiftAndDate - done
    */
    @Test
    public void getTablesByRestaurantIdAndShiftAndDateTest() throws Exception {
        List<Table> tablesAPI = new ArrayList<Table>();
        List<Table> tablesSQL = new ArrayList<Table>();
        
        int shift = 1;
        int id = 1;
        String date = "2017-01-01";
      
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
        
        assertEquals(tablesSQL.size(), tablesAPI.size());
        
        int x = 0;
        for (Table i : tablesSQL) assertEquals(i.toString(), tablesAPI.get(x++).toString());  
    }
    
    /* UserController Tests
    getUserPasswordByEmail - done
    newUser - TODO
    */
    @Test
    public void getUserPasswordByEmailTest() throws Exception {
        String passwordAPI = "0";
        String passwordSQL = "1";
        String email = "gebruiker1@odisee.be";
      
        passwordAPI = getValueFromURL("http://localhost:8080/userPassword?email=" + email);
                     
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://10.129.32.188/fastdine", "root", "Azerty123");

            Statement st = con.createStatement();
            String sql = "SELECT password FROM users WHERE email = '" + email + "' LIMIT 1";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) { 
                passwordSQL = rs.getString("password");     
            }

            con.close();
                      
        }
        catch (Exception e) {
            throw e;
        }
        
        assertEquals(passwordAPI, passwordSQL);
        
    }
    
    @Test
    public void newUserTest() throws Exception {
        assertEquals("TRUE","TRUE");
    }
    
    // Extra methods
    private String getValueFromURL(String urlRequest)
    {
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