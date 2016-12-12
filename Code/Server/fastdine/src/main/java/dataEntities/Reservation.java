package dataEntities;

import java.util.Date;

public class Reservation {
    private final int id;
    private final Date date;
    private final int shift;
    private final User customer;
    private final String restaurantName;
    
    public Reservation(Date date, int shift, String restaurantName) {
        this.id = 0;
        this.date = date;
        this.shift = shift;
        this.customer = null;
        this.restaurantName = restaurantName;
    }
    
    public Reservation(int reservationId, Date date, int shift, User customer) {
        this.id = reservationId;
        this.date = date;
        this.shift = shift;
        this.customer = customer;
        this.restaurantName = null;
    }

    public int getId() {
        return this.id;
    }

    public Date getDate() {
        return this.date;
    }

    public int getShift() {
        return this.shift;
    }
    
    public User getCustomer() {
        return this.customer;
    }
    
    public String getRestaurantName() {
        return this.restaurantName;
    }
}
