package fastdine.dataEntities;

public class Customer {
    
    private final int id;
    private final String name;
    private final String email;
    private final String telephone;
    
    public Customer(int klantId, String name, String email, String telephone) {
        this.id = klantId;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelephone() {
        return this.telephone;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Klant [id=%d, name='%s', email='%s', telephone='%s']",
                this.id, this.name, this.email, this.telephone);
    }
}