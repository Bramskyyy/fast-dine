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
        if (!date.isEmpty() && !Integer.toString(shift).isEmpty() && !userEmail.isEmpty()) {
            try {
                String target = "Thu Sep 28 20:29:30 JST 2000";
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date result =  df.parse(date); 
            } catch (ParseException pe) {
                return false;
            }
            
            sql = "SELECT * FROM users WHERE email= ?";
            User user = new User();
            
            try 
            {
                //maak nieuwe user aan...
                user.setId(0);
                int result = jdbcTemplate.queryForObject(sql, new Object[] { userEmail }, Integer.class);
                //indien userId == null return false;
                if (result == 0) return false;
            }
            catch (Exception e)
            {
                return false;
            }
            
            Random rand = new Random();
            int reservationId = rand.nextInt((50000 - 0) + 1) + 0;
            
            int id = user.getId();
            //voeg toe aan database
            
            return true;
        }
        
        return false;
    }
}