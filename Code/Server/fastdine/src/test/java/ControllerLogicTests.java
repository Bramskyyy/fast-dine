import dataEntities.*;
import fastdine.*;
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
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

    
@RestController
public class ControllerLogicTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private List<Restaurant> restaurants;
    private String sql;    
    
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
    public void testNewUser() {
        UserController uc = new UserController();
        
        int customerId = 1;
        String customerName = "test";
        String customerEmail = "test@test.be";
        String customerPhone = "012334556789";
        String customerType = "klant";
        String customerPswd = "123";
              
        assertFalse(uc.newUser(customerName, "", customerPhone, customerType, customerPswd));
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, "", customerPswd));
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, customerType, ""));
        
        assertFalse(uc.newUser(customerName, "", customerPhone, customerType, ""));
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, "", ""));
        assertFalse(uc.newUser(customerName, "", customerPhone, "", customerPswd));
        
        assertFalse(uc.newUser(customerName, "", customerPhone, "", ""));
        
       
        assertFalse(uc.newUser(customerName, customerEmail, customerPhone, customerType, customerPswd));
    }
    
    @Test
    public void testGetUserPassword() {
        UserController uc = new UserController();
        String customerEmail = "test@test.be";
        String expectedException = "Exception: null";
        
        assertEquals(uc.getUserPasswordByEmail(""), null);
        assertEquals(uc.getUserPasswordByEmail(customerEmail), expectedException);
        
    }
    
    @Test
    public void testGetTablesByRestaurantIdAndShiftAndDate() {
        TableController tc = new TableController();
        exception.expect(RuntimeException.class);
        tc.getTablesByRestaurantIdAndShiftAndDate(1, "1", "2016, 11, 27");
    }
    
//    @Test
//    public void testNewReservation() {
//        ReservationController rc = new ReservationController();
//        rc.newReservation(sql, 0, sql, 0, 0, 0)
//    }
}
