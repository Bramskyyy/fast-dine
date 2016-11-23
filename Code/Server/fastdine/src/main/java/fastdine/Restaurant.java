package fastdine;

public class Restaurant {

    private final long id;
    private final String name;
    private final String location;
    private final String email;
    private final int telephone;
    private final int seats;

    public Restaurant(int restaurant_id, String name, String location, String email, int telephone, int seats) {
        this.id = restaurant_id;
        this.name = name;
        this.location = location;
        this.email = email;
        this.telephone = telephone;
        this.seats = seats;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    
    public String getLocation() 
    {
        return this.location;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public int getTelefoon() {
        return this.telephone;
    }
    
    public int getSeats()
    {
        return this.seats;
    }
    
    @Override
    public String toString()
    {
        return String.format(
                "Restaurant[id=%d, name='%s', location='%s', email='%s', telephone='%d', seats='%d']",
                id, name, location, email, telephone, seats);
    }
}