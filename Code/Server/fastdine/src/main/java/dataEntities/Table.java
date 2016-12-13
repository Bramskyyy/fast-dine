package dataEntities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final int id;
    private final int tableNumber;
    private final int seats;
    private final List<Reservation> reservations;
    
    public Table(int tableId, int tableNumber, int seats) {
        this.id = tableId;
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.reservations = new ArrayList<>();
    }
    
    public Table(int tableID, int seats) {
        this.id = tableID;
        this.tableNumber = 0;
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

    public int getTableNumber() {
        return this.tableNumber;
    }

    public int getSeats() {
        return this.seats;
    }

    @Override
    public String toString() {
        return "Table{" + "id=" + id + ", tableNumber=" + tableNumber + ", seats=" + seats + ", reservations=" + reservations.toString() + '}';
    }
}
