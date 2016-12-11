package UnitTests;

import ControllerTestsLogic.ReservationControllerTestLogic;
import fastdine.ReservationController;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReservationControllerTests {
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
    
    @Test
    public void getReservationsByRestaurantIdAndShiftTest () throws Exception {
        ReservationControllerTestLogic testLogic = new ReservationControllerTestLogic();
        
        assertTrue(testLogic.getReservationsByRestaurantIdResult("0"));
        assertTrue(testLogic.getReservationsByRestaurantIdResult("1"));
        assertTrue(testLogic.getReservationsByRestaurantIdAndShiftResult("0","3"));
        assertTrue(testLogic.getReservationsByRestaurantIdAndShiftResult("1","3"));
        assertTrue(testLogic.getReservationsByRestaurantIdAndShiftResult("1","0"));
        exception.expect(RuntimeException.class);
        testLogic.getReservationsByRestaurantIdAndShiftResult("test","test");
    }
    
    //TODO - complete test 
    @Test
    public void removeReservationTest () throws Exception {
    
    }
    
    //TODO - complete test
    @Test
    public void getReservationsByUserIdTest () throws Exception {
    
    }
    
    //TODO - complete test
    @Test
    public void newReservationTest () throws Exception {
        
    }
}
