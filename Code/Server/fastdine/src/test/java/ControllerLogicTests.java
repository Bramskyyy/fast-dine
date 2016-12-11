import dataEntities.*;
import fastdine.*;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    
    @Test
    public void testNewReservation() {
        ReservationController rc = new ReservationController();
        
        String date = "2017-01-01";
        int shift = 1;
        String userEmail = "gebruiker1@odisee.be";
        int table1 = 1;
        int table2 = 2;
        int table3 = 3;
        
        assertFalse(rc.newReservation("", shift, userEmail, table1, table2, table3));
        assertFalse(rc.newReservation(date, 4, userEmail, table1, table2, table3));
        assertFalse(rc.newReservation(date, 0, userEmail, table1, table2, table3));
        assertFalse(rc.newReservation(date, shift, "", table1, table2, table3));
        
        assertFalse(rc.newReservation(date, shift, userEmail, -1, table2, table3));
        assertFalse(rc.newReservation(date, shift, userEmail, table1, -1, table3));
        assertFalse(rc.newReservation(date, shift, userEmail, table1, table2, -1));
        
        assertFalse(rc.newReservation(date, shift, userEmail, table1, table2, table3));
    }
    
    @Test
    public void testGetReservationsByRestaurantId() {
        ReservationController rc = new ReservationController();
        
        int id = 1;
        String shift = "";
        
        exception.expect(RuntimeException.class);
        rc.getReservationsByRestaurantIdAndShift(id, shift);
    }
    
    @Test
    public void testGetReservationsByRestaurantIdAndShift() {
        ReservationController rc = new ReservationController();
        
        int id = 1;
        String shift = "1";
        
        exception.expect(RuntimeException.class);
        rc.getReservationsByRestaurantIdAndShift(id, shift);
    }
    
    @Test
    public void testGetReservationsByUserId() {
        ReservationController rc = new ReservationController();
               
        assertEquals(rc.getReservationsByUserId("0"),null);
        assertEquals(rc.getReservationsByUserId(""),null);
        
        exception.expect(RuntimeException.class);
        rc.getReservationsByUserId("1");
    }
    
    @Test
    public void testRemoveReservation() {
        ReservationController rc = new ReservationController();
        
        String id = "1";
        String date = "2017-01-01";
        String shift = "1";
        
        assertFalse(rc.removeReservation("", date, shift));
        assertFalse(rc.removeReservation(id, "", shift));
        assertFalse(rc.removeReservation(id, date, ""));
        
        assertFalse(rc.removeReservation(id, date, shift));
    }
}
