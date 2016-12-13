package dataEntities;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final int id;
    private final String name;
    private final String location;
    private final String email;
    private final String telephone;
    private final int seats;
    private final List<Table> tables;

    public Restaurant(int restaurantId, String name, String location, String email, String telephone, int seats) {
        this.id = restaurantId;
        this.name = name;
        this.location = location;
        this.email = email;
        this.telephone = telephone;
        this.seats = seats;
        this.tables = new ArrayList<>();
    }
    
    public void addTable(Table table) {
        this.tables.add(table);
    }
    
    public List<Table> getTables() {
        return this.tables;
    }
    
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getTelephone() {
        return this.telephone;
    }
    
    public int getSeats() {
        return this.seats;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Restaurant [id=%d, name='%s', location='%s', email='%s', telephone='%s', seats='%d']",
                this.id, this.name, this.location, this.email, this.telephone, this.seats);
    }
}