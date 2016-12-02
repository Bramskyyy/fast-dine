package fastdine;

import dataEntities.User;
import dataEntities.Reservation;
import dataEntities.Table;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    private List<String> reservations;
    
    @RequestMapping("/reservations")
    public List<String> getReservationsByRestaurantIdAndShift(@RequestParam(value="id") int id, @RequestParam(value="shift", defaultValue="") String shift) {
        reservations = new ArrayList<String>();
       
        // In case no shift was specified, return all reservations for the given restaurant
        if (shift.isEmpty()) {
            sql = "select users.name, users.telephone, reservations.date, reservations.shift, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + id + ";";
            
            jdbcTemplate.query(
            sql,
            (rs, rowNum) -> reservations.addAll(Arrays.asList(
                    rs.getString("name"), 
                    rs.getString("telephone"), 
                    rs.getDate("date").toString(), 
                    Integer.toString(rs.getInt("shift")), 
                    Integer.toString(rs.getInt("tablenumber"))
            )));
  
        // In case a shift was specified, return all reservations for the given restaurant and shift
        } else {
            sql = "select users.name, users.telephone, reservations.date, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + id +
                " AND reservations.shift = " + shift + ";";
            
            jdbcTemplate.query(
            sql,
            (rs, rowNum) -> reservations.addAll(Arrays.asList(
                rs.getString("name"), 
                rs.getString("telephone"), 
                rs.getDate("date").toString(), 
                Integer.toString(rs.getInt("tablenumber"))
            )));    
        }
           
        if (!reservations.isEmpty()) return reservations;
               
        return null;
    }
    
    //Create
    @RequestMapping("/newReservation")
    public boolean newReservation (@RequestParam(value="date") String date, @RequestParam(value="shift") int shift, @RequestParam(value="email") String userEmail) {
        if (!date.isEmpty() && shift >= 0 && shift <= 2 && !userEmail.isEmpty()) {
            // Check if the date can  be parsed, return false otherwise
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate =  df.parse(date); 
            } catch (ParseException pe) {
                return false;
            }
            
            int useriId = 0;
                      
            // Check if the user exists, return false otherwise
            try {
                sql = "SELECT * FROM users WHERE email= ?";            
                useriId = jdbcTemplate.queryForObject(sql, new Object[] { userEmail }, Integer.class);
                                
                if (useriId == 0) return false;
            }
            catch (Exception e)
            {
                return false;
            }
            
            // Generate random reservation ID
            Random rand = new Random();
            int reservationId = rand.nextInt((50000 - 0) + 1) + 0;
            
            // TODO add reservation to database
            sql = "INSERT INTO reservations ...";
            
            return true;
        }
        
        return false;
    }
}