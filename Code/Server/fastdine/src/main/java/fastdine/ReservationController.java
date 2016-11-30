package fastdine;

import dataEntities.User;
import dataEntities.Reservation;
import dataEntities.Table;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {   
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private String sql;
    
    private List<Reservation> reservations;

    
    @RequestMapping("/reservation")
    public Reservation getReservationsByRestaurantId(@RequestParam(value="id") int id) {
        reservations = new ArrayList<Reservation>();

        
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            
            if (r.getId() == id) {
                return r;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/reservations")
    public List<Reservation> getReservationsByRestaurantIdAndShift(@RequestParam(value="id") int id, @RequestParam(value="shift") int shift) {
        reservations = new ArrayList<Reservation>();
 
        sql = "SELECT * FROM reservations WHERE ";
            
        return null;
    }
}