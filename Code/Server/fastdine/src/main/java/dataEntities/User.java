package dataEntities;

public class User {
    private int id;
    private final String name;
    private final String email;
    private final String telephone;
    private final String type;
    private final String password;

    public User() {
        this.id = 0;
        this.name = null;
        this.email = null;
        this.telephone = null;
        this.type = null;
        this.password = null;
    }
    
    public User(int userId, String name, String email, String telephone, String type, String password) {
        this.id = userId;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.type = type;
        this.password = password;
    }

    public User(String name, String email, String telephone, String type, String password) {
        this.id = 0;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.type = type;
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
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
                "Klant [id=%d, name='%s', email='%s', telephone='%s', type='%s', password='%s']",
                this.id, this.name, this.email, this.telephone, this.type, this.password);
    }
}