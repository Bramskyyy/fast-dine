package fastdine;

import dataEntities.User;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {  
    User c1 = new User(1, "test1", "email1", "phone1", "klant", "pswrd");
    User c2 = new User(2, "test2", "email2", "phone2", "eigenaar", "pswrd");
    User c3 = new User(3, "test3", "email3", "phone3", "klant", "pswrd");
    
    @RequestMapping("/customer")
    public User customer(@RequestParam(value="name") String name) {
        List<User> customers = new ArrayList<User>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        
        for (int i = 0; i < customers.size(); i++) {
            User r = customers.get(i);
            if (r.getName().equals(name)) {
                return r;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/customers")
    public List<User> customer() {
        List<User> customers = new ArrayList<User>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        
        return customers;
    }
}