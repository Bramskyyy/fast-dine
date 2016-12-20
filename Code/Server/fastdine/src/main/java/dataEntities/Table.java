package dataEntities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final int id;
    private final int number;
    private final int seats;
    private final List<Reservation> reservations;
    
    public Table(int tableId, int tableNumber, int seats) {
        this.id = tableId;
        this.number = tableNumber;
        this.seats = seats;
        this.reservations = new ArrayList<>();
    }
    
    public Table(int tableID, int seats) {
        this.id = tableID;
        this.number = 0;
        this.seats = seats;
        this.reservations = new ArrayList<>();        
    }
    
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
    
    public List<Reservation> getReservations() {
        return this.reservations;
    }

    public int getId() {
        return this.id;
    }

    public int getNumber() {
        return this.number;
    }

    public int getSeats() {
        return this.seats;
    }

    @Override
    public String toString() {
        return "Table{" + "id=" + id + ", tableNumber=" + number + ", seats=" + seats + ", reservations=" + reservations.toString() + '}';
    }
}
