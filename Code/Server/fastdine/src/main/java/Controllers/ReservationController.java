package Controllers;

import fastdine.dataEntities.Customer;
import fastdine.dataEntities.Reservation;
import fastdine.dataEntities.Table;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {   

    Reservation c1 = new Reservation(1, new Date(2016, 11, 27), 1, new Customer());
    Reservation c2 = new Reservation(2, new Date(2016, 11, 27), 2, new Customer());   
    Reservation c3 = new Reservation(3, new Date(2016, 11, 27), 3, new Customer());
    
    @RequestMapping("/reservation")
    public Reservation reservation(@RequestParam(value="id") int id) {
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(c1);
        reservations.add(c2);
        reservations.add(c3);
        
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            
            if (r.getId() == id) {
                return r;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/reservations")
    public List<Reservation> reservations() {
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(c1);
        reservations.add(c2);
        reservations.add(c3);
        
        return reservations;
    }
}