package hello;

public class Restaurant {

    private final long id;
    private final String name;
    private final int tables;
    private final String address;

    public Restaurant(long id, String name, int tables, String address) {
        this.id = id;
        this.name = name;
        this.tables = tables;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public int getTables() {
        return tables;
    }
    
    public String getAddress() {
        return address;
    }
}