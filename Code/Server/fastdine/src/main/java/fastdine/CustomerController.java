package fastdine;

import dataEntities.Customer;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {  
    Customer c1 = new Customer(1, "test1", "email1", "phone1");
    Customer c2 = new Customer(2, "test2", "email2", "phone2");
    Customer c3 = new Customer(3, "test3", "email3", "phone3");
    
    @RequestMapping("/customer")
    public Customer customer(@RequestParam(value="name") String name) {
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        
        for (int i = 0; i < customers.size(); i++) {
            Customer r = customers.get(i);
            if (r.getName().equals(name)) {
                return r;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/customers")
    public List<Customer> customer() {
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        
        return customers;
    }
}