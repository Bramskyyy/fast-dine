package UnitTests;

import ControllerTestsLogic.ReservationControllerTestLogic;
import Helper.FileReader;
import dataEntities.User;
import fastdine.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReservationControllerTests {
    private ReservationControllerTestLogic testLogic = new ReservationControllerTestLogic();
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
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
       
        exception.expect(NullPointerException.class);
        rc.newReservation(date, shift, userEmail, table1, table2, table3);
    }
    
    @Test
    public void testGetReservationsByRestaurantId() {
        ReservationController rc = new ReservationController();
        
        int id = 1;
        String date = "2017/01/01";
        
        exception.expect(RuntimeException.class);
        rc.getReservationsByRestaurantIdAndDate(id, date);
    }
    
    @Test
    public void testGetReservationsByRestaurantIdAndDate() {
        ReservationController rc = new ReservationController();
        
        int id = 1;
        String date = "2017/01/01";
        
        exception.expect(RuntimeException.class);
        rc.getReservationsByRestaurantIdAndDate(id, date);
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
        
        exception.expect(NullPointerException.class);
        rc.removeReservation(id, date, shift);
    }
    
    @Test
    public void getReservationsByRestaurantIdAndDateTest () throws Exception {       
        assertTrue(testLogic.getReservationsByRestaurantIdResult("0"));
        assertTrue(testLogic.getReservationsByRestaurantIdAndDateResult("0","3"));
        assertTrue(testLogic.getReservationsByRestaurantIdAndDateResult("1","3"));
        assertTrue(testLogic.getReservationsByRestaurantIdAndDateResult("1","0"));
        exception.expect(RuntimeException.class);
        testLogic.getReservationsByRestaurantIdAndDateResult("test","test");
    }
    
    @Test
    public void getReservationsByUserIdTest () throws Exception {
        assertFalse(testLogic.getReservationsByUserIdResult("0"));
        assertFalse(testLogic.getReservationsByUserIdResult(null));
    }
    
    @Test
    public void addAndRemoveReservationTest () throws Exception {
        String date = "2017-01-01";
        int shift = 1;
        String email = "gebruiker1@odisee.be";
        String inexistentEmail = "potato";
        int table1 = 1;
        int user_id = 1;
        
        FileReader reader = new FileReader();

        assertTrue(Boolean.parseBoolean(reader.getValueFromURL("http://localhost:8090/newReservation?date=" + date + "&shift=" + shift + "&email=" + email + "&table1=" + table1)));
        assertFalse(Boolean.parseBoolean(reader.getValueFromURL("http://localhost:8090/newReservation?date=" + date + "&shift=" + shift + "&email=" + email + "&table1=" + table1)));
        
        assertFalse(Boolean.parseBoolean(reader.getValueFromURL("http://localhost:8090/newReservation?date=" + date + "&shift=" + shift + "&email=" + inexistentEmail + "&table1=" + table1)));
        
        assertTrue(Boolean.parseBoolean(reader.getValueFromURL("http://localhost:8090/deleteReservation?id=" + user_id + "&date=" + date + "&shift=" + shift)));
        
    }
}
