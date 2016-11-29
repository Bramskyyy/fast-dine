package dataEntities;

import java.util.Date;

public class Reservation {
    
    private final int id;
    private final Date date;
    private final int shift;
    private final User customer;
    
    public Reservation(int reservationId, Date date, int shift, User customer) {
        this.id = reservationId;
        this.date = date;
        this.shift = shift;
        this.customer = customer;
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
}
