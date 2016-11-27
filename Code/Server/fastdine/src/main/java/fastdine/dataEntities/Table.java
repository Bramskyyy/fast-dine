package fastdine.dataEntities;

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
        this.reservations = new ArrayList<Reservation>();
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
}
