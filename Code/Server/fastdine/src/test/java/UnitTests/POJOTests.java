package UnitTests;

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
        String customerPassword = "123";
        
        User customer1 = new User(customerId, customerName, customerEmail, customerPhone, customerType, customerPassword);
        
        assertEquals(customer1.getId(), customerId);
        assertEquals(customer1.getName(), customerName);
        assertEquals(customer1.getEmail(), customerEmail);
        assertEquals(customer1.getTelephone(), customerPhone);
        assertEquals(customer1.getType(), customerType);
        assertEquals(customer1.getPassword(), customerPassword);
        
        String toStringResult = "Klant [id=1, name='test', email='test@test.be', telephone='012334556789', type='klant', password='123']";
        
        assertEquals(customer1.toString(), toStringResult);
        
        int newId = 2;
        
        customer1.setId(newId);
        
        assertEquals(customer1.getId(), newId);
        
        User customer2 = new User(customerName, customerEmail, customerPhone, customerType, customerPassword);
        
        assertEquals(customer2.getName(), customerName);
        assertEquals(customer2.getEmail(), customerEmail);
        assertEquals(customer2.getTelephone(), customerPhone);
        assertEquals(customer2.getType(), customerType);
        assertEquals(customer2.getPassword(), customerPassword);
    }
    
    @Test
    public void testRestaurant() {
        int id = 1;
        String name = "test";
        String location = "nergens";
        String email = "test@test.be";
        String telephone = "012334556789";
        int seats = 0;        
        
        Restaurant restaurant = new Restaurant(id, name, location, email, telephone, seats);
        
        assertEquals(restaurant.getId(), id);
        assertEquals(restaurant.getName(), name);
        assertEquals(restaurant.getLocation(), location);
        assertEquals(restaurant.getEmail(), email);
        assertEquals(restaurant.getTelephone(), telephone);
        assertEquals(restaurant.getTables().isEmpty(), true);
        assertEquals(restaurant.getSeats(), seats);
        
        Table table = new Table(1, 1);
        restaurant.addTable(table);
        
        assertTrue(restaurant.getTables().contains(table));
        
        String toStringResult = "Restaurant [id=" + Integer.toString(id) + ", name='" + name + "', location='" + location + "', email='" + email + "', telephone='" + telephone + "', seats='" + Integer.toString(seats) + "']";
        
        assertEquals(restaurant.toString(), toStringResult);
    }
    
    @Test
    public void testTable() {
        int id = 1;
        int tableNumber = 1;
        int seats = 1;
        List<Reservation> reservations = new ArrayList<>();
               
        Table table = new Table(id, tableNumber, seats);
        
        assertEquals(table.getId(), id);
        assertEquals(table.getNumber(), tableNumber);
        assertEquals(table.getSeats(), seats);
        assertEquals(table.getReservations().isEmpty(), true);
        
        User customer = new User();
        Reservation reservation = new Reservation(1, new Date(2016, 11, 27), 1, customer);
        
        table.addReservation(reservation);       
        reservations.add(reservation);        
        assertTrue(table.getReservations().contains(reservation));
        
        String toStringResult = "Table{id=" + id + ", tableNumber=" + tableNumber + ", seats=" + seats + ", reservations=" + reservations.toString() + '}';       
        assertEquals(table.toString(), toStringResult);
    }
    
    @Test
    public void testReservation() {
        int id = 1;
        Date date = new Date(2016, 11, 27);
        int shift = 1;
        User customer = new User();
        String restaurant = "restaurant";
               
        Reservation reservation1 = new Reservation(id, date, shift, customer);
        
        assertEquals(reservation1.getId(), id);
        assertEquals(reservation1.getDate(), date);
        assertEquals(reservation1.getShift(), shift);
        assertEquals(reservation1.getCustomer(), customer);
        
        Reservation reservation2 = new Reservation(date, shift, restaurant);
        
        assertEquals(reservation2.getId(), 0);
        assertEquals(reservation2.getDate(), date);
        assertEquals(reservation2.getShift(), shift);
        assertEquals(reservation2.getCustomer(), null);
        assertEquals(reservation2.getRestaurantName(), restaurant);
    }
}
