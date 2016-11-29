import dataEntities.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class POCOTests {
   
    @Test
    public void testCustomer() {
        int customerId = 1;
        String customerName = "test";
        String customerEmail = "test@test.be";
        String customerPhone = "012334556789";
        
        Customer c = new Customer(customerId, customerName, customerEmail, customerPhone);
        
        assertEquals(c.getId(), customerId);
        assertEquals(c.getName(), customerName);
        assertEquals(c.getEmail(), customerEmail);
        assertEquals(c.getTelephone(), customerPhone);
    }
    
    @Test
    public void testRestaurant() {
        int id = 1;
        String name = "test";
        String location = "nergens";
        String email = "test@test.be";
        String telephone = "012334556789";
        int seats = 0;
        List<Table> tables;        
        
        Restaurant c = new Restaurant(id, name, location, email, telephone, seats);
        
        assertEquals(c.getId(), id);
        assertEquals(c.getName(), name);
        assertEquals(c.getLocation(), location);
        assertEquals(c.getEmail(), email);
        assertEquals(c.getTelephone(), telephone);
        assertEquals(c.getTables().isEmpty(), true);
    }
    
    @Test
    public void testTable() {
    
        int id = 1;
        int tableNumber = 1;
        int seats = 1;
        List<Reservation> reservations;
               
        Table c = new Table(id, tableNumber, seats);
        
        assertEquals(c.getId(), id);
        assertEquals(c.getTableNumber(), tableNumber);
        assertEquals(c.getSeats(), seats);
        assertEquals(c.getReservations().isEmpty(), true);
    }
    
    @Test
    public void testReservation() {
    
        int id = 1;
        Date date = new Date(2016, 11, 27);
        int shift = 1;
        Customer customer = new Customer();
               
        Reservation c = new Reservation(id, date, shift, customer);
        
        assertEquals(c.getId(), id);
        assertEquals(c.getDate(), date);
        assertEquals(c.getShift(), shift);
        assertEquals(c.getCustomer(), customer);
    }
}
