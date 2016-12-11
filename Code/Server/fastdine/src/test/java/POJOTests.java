import dataEntities.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class POJOTests {
   
    @Test
    public void testCustomer() {
        int customerId = 1;
        String customerName = "test";
        String customerEmail = "test@test.be";
        String customerPhone = "012334556789";
        String customerType = "klant";
        String customerPswd = "123";
        
        User c = new User(customerId, customerName, customerEmail, customerPhone, customerType, customerPswd);
        
        assertEquals(c.getId(), customerId);
        assertEquals(c.getName(), customerName);
        assertEquals(c.getEmail(), customerEmail);
        assertEquals(c.getTelephone(), customerPhone);
        assertEquals(c.getType(), customerType);
        assertEquals(c.getPassword(), customerPswd);
        
        String toStringResult = "Klant [id=1, name='test', email='test@test.be', telephone='012334556789', type='klant', password='123']";
        
        assertEquals(c.toString(), toStringResult);
        
        int newID = 2;
        
        c.setId(newID);
        
        assertEquals(c.getId(), newID);
        
        User c2 = new User(customerName, customerEmail, customerPhone, customerType, customerPswd);
        
        assertEquals(c2.getName(), customerName);
        assertEquals(c2.getEmail(), customerEmail);
        assertEquals(c2.getTelephone(), customerPhone);
        assertEquals(c2.getType(), customerType);
        assertEquals(c2.getPassword(), customerPswd);
        
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
        assertEquals(c.getSeats(), seats);
        
        Table t = new Table(1, 1);
        c.addTable(t);
        
        assertTrue(c.getTables().contains(t));
        
        String toStringResult = "Restaurant [id=" + Integer.toString(id) + ", name='" + name + "', location='" + location + "', email='" + email + "', telephone='" + telephone + "', seats='" + Integer.toString(seats) + "']";
        
        assertEquals(c.toString(), toStringResult);
        
        
        
    }
    
    @Test
    public void testTable() {
    
        int id = 1;
        int tableNumber = 1;
        int seats = 1;
        List<Reservation> reservations = new ArrayList<Reservation>();
               
        Table c = new Table(id, tableNumber, seats);
        
        assertEquals(c.getId(), id);
        assertEquals(c.getTableNumber(), tableNumber);
        assertEquals(c.getSeats(), seats);
        assertEquals(c.getReservations().isEmpty(), true);
        
        User u = new User();
        Reservation r = new Reservation(1, new Date(2016, 11, 27), 1, u);
        
        c.addReservation(r);       
        reservations.add(r);        
        assertTrue(c.getReservations().contains(r));
        
        String toStringResult = "Table{id=" + id + ", tableNumber=" + tableNumber + ", seats=" + seats + ", reservations=" + reservations.toString() + '}';       
        assertEquals(c.toString(), toStringResult);

    }
    
    @Test
    public void testReservation() {
    
        int id = 1;
        Date date = new Date(2016, 11, 27);
        int shift = 1;
        User customer = new User();
               
        Reservation c = new Reservation(id, date, shift, customer);
        
        assertEquals(c.getId(), id);
        assertEquals(c.getDate(), date);
        assertEquals(c.getShift(), shift);
        assertEquals(c.getCustomer(), customer);
        
        Reservation c1 = new Reservation(date, shift);
        
        assertEquals(c1.getId(), 0);
        assertEquals(c1.getDate(), date);
        assertEquals(c1.getShift(), shift);
        assertEquals(c1.getCustomer(), null);
    }
}
