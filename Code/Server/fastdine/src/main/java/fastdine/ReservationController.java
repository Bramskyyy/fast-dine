package fastdine;

import dataEntities.Reservation;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    
    @RequestMapping("/deleteReservation")
    public boolean removeReservation(@RequestParam(value="id") String id, @RequestParam(value="date") String date, @RequestParam(value="shift") String shift) {
        if (!id.isEmpty() && !date.isEmpty() && !shift.isEmpty()) {
            sql = "delete from reservations where date = '" + date + "' and shift = " + shift + " and user_id = " + id + ";";
            
            try {
                jdbcTemplate.update(sql);
                return true;
            } 
            catch (DataAccessException e) {
                return false;
            }
        }
        
        return false;
    }
    
    @RequestMapping("/reservationsByUserId")
    public List<Reservation> getReservationsByUserId(@RequestParam(value="id") String id) {
        List<Reservation> reservationsByUserId = new ArrayList<>();
        
        if (!id.isEmpty() && !id.equals("0")) {
            sql = "select date, shift, restaurants.name as name from reservations " +
                "inner join tables_has_reservations on tables_has_reservations.reservation_id = reservations.id " +
                "inner join `tables` on tables_has_reservations.table_id = `tables`.id " +
                "inner join restaurants on `tables`.restaurant_id = restaurants.id " +
                "where user_id = '" + id + "';";
                       
            jdbcTemplate.query(
            sql,
            (rs, rowNum) -> reservationsByUserId.add(new Reservation(rs.getDate("date"), rs.getInt("shift"), rs.getString("name"))));
        }
        
        if (!reservationsByUserId.isEmpty()) return reservationsByUserId;
               
        return null;
    }
    
    @RequestMapping("/reservations")
    public List<String> getReservationsByRestaurantIdAndDate(@RequestParam(value="id") int id, @RequestParam(value="date", defaultValue="") String date) {
        reservations = new ArrayList<>();
       
        // In case no shift was specified, return all reservations for the given restaurant
        if (date.isEmpty()) {
            sql = "select reservations.id, users.name, users.telephone, reservations.date, reservations.shift, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + id + ";";
            
            jdbcTemplate.query(
            sql,
            (rs, rowNum) -> reservations.addAll(Arrays.asList(
                    Integer.toString(rs.getInt("id")),
                    rs.getString("name"), 
                    rs.getString("telephone"), 
                    rs.getDate("date").toString(), 
                    Integer.toString(rs.getInt("shift")), 
                    Integer.toString(rs.getInt("tablenumber"))
            )));
        } else {
            sql = "select reservations.id, users.name, users.telephone, reservations.shift, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + id +
                " AND reservations.date = '" + date + "';";
            
            jdbcTemplate.query(
            sql,
            (rs, rowNum) -> reservations.addAll(Arrays.asList(
                Integer.toString(rs.getInt("id")),
                rs.getString("name"), 
                rs.getString("telephone"),
                Integer.toString(rs.getInt("shift")),
                Integer.toString(rs.getInt("tablenumber"))
            )));    
        }
           
        if (!reservations.isEmpty()) return reservations;
               
        return null;
    }
    
    //Create
    @RequestMapping("/newReservation")
    public boolean newReservation (@RequestParam(value="date") String date, @RequestParam(value="shift") int shift, @RequestParam(value="email") String userEmail, @RequestParam(value="table1") int table1, @RequestParam(value="table2", defaultValue="-1") int table2, @RequestParam(value="table3", defaultValue="-1") int table3) {
        if (!date.isEmpty() && shift >= 1 && shift <= 3 && !userEmail.isEmpty()) {
            List<Integer> tables = new ArrayList<>();
            
            if(table1 != -1) tables.add(table1); else return false;
            if(table2 != -1) tables.add(table2); else return false;
            if(table3 != -1) tables.add(table3); else return false;
            
            if (tables.isEmpty()) return false;
            
            sql = "INSERT INTO reservations (date, shift, user_id) "
                    + "VALUES ('" + date + "', " + shift + ", "
                    + "(SELECT id FROM users WHERE email = '" + userEmail + "' LIMIT 1))";
            
            try {
                jdbcTemplate.update(sql);
            } 
            catch (DataAccessException e) {
                return false;
            }
            
            for (int i = 0; i < tables.size(); i++) {
              sql = "INSERT INTO tables_has_reservations (table_id, reservation_id) VALUES (" + tables.get(i) + ",(SELECT id FROM reservations ORDER BY id DESC LIMIT 1))";
              
                try {
                    jdbcTemplate.update(sql);
                } 
                catch (DataAccessException e) {
                    return false;
                }
            }

            return true;
        }
        
        return false;
    }
}