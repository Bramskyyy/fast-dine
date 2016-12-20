package controllers;

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
    
    private String sqlQuery;
    
    @RequestMapping("/deleteReservation")
    public boolean removeReservation(@RequestParam(value="id") String userId, @RequestParam(value="date") String date, @RequestParam(value="shift") String shift) {
        if (!userId.isEmpty() && !date.isEmpty() && !shift.isEmpty()) {
            sqlQuery = "DELETE FROM reservations WHERE date = '" + date + "' and shift = " + shift + " and user_id = " + userId + ";";
            
            try {
                jdbcTemplate.update(sqlQuery);
                return true;
            } 
            catch (DataAccessException e) {
                return false;
            }
        }
        
        return false;
    }
    
    @RequestMapping("/reservationsByUserId")
    public List<Reservation> getReservationsByUserId(@RequestParam(value="id") String userId) {
        List<Reservation> reservations = new ArrayList<>();
        
        if (!userId.isEmpty() && !userId.equals("0")) {
            sqlQuery = "select date, shift, restaurants.name as name from reservations " +
                "inner join tables_has_reservations on tables_has_reservations.reservation_id = reservations.id " +
                "inner join `tables` on tables_has_reservations.table_id = `tables`.id " +
                "inner join restaurants on `tables`.restaurant_id = restaurants.id " +
                "where user_id = '" + userId + "';";
                       
            jdbcTemplate.query(
            sqlQuery,
            (rs, rowNum) -> reservations.add(new Reservation(rs.getDate("date"), rs.getInt("shift"), rs.getString("name"))));
        }
        
        if (!reservations.isEmpty()) return reservations;
               
        return null;
    }
    
    @RequestMapping("/reservations")
    public List<String> getReservationsByRestaurantIdAndDate(@RequestParam(value="id") int restuarantId, @RequestParam(value="date", defaultValue="") String date) {
        List<String> reservations;
       
        if (date.isEmpty()) {
            reservations = getReservations(restuarantId);
        } else {
            reservations = getReservations(restuarantId, date);    
        }
           
        if (!reservations.isEmpty()) return reservations;
               
        return null;
    }
    
    private List<String> getReservations(int restaurantId) {
        List<String> reservations = new ArrayList<>();
        
        sqlQuery = "select reservations.id, users.name, users.telephone, reservations.date, reservations.shift, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + restaurantId + ";";
            
        jdbcTemplate.query(
        sqlQuery,
        (rs, rowNum) -> reservations.addAll(Arrays.asList(
                Integer.toString(rs.getInt("id")),
                rs.getString("name"), 
                rs.getString("telephone"), 
                rs.getDate("date").toString(), 
                Integer.toString(rs.getInt("shift")), 
                Integer.toString(rs.getInt("tablenumber"))
        )));
            
        return reservations;
    }
    
    private List<String> getReservations(int restaurantId, String date) {
        List<String> reservations = new ArrayList<>();
        sqlQuery = "select reservations.id, users.name, users.telephone, reservations.shift, tables.tablenumber from users " +
                "inner join reservations on users.id = reservations.user_id " +
                "inner join tables_has_reservations on reservations.id = tables_has_reservations.reservation_id " +
                "inner join tables on tables_has_reservations.table_id = tables.id " +
                "inner join restaurants on tables.restaurant_id = restaurants.id " +
                "WHERE restaurants.id = " + restaurantId +
                " AND reservations.date = '" + date + "';";
            
        jdbcTemplate.query(
        sqlQuery,
        (rs, rowNum) -> reservations.addAll(Arrays.asList(
            Integer.toString(rs.getInt("id")),
            rs.getString("name"), 
            rs.getString("telephone"),
            Integer.toString(rs.getInt("shift")),
            Integer.toString(rs.getInt("tablenumber"))
        )));
            
        return reservations;    
    }
    
    @RequestMapping("/newReservation")
    public boolean addNewReservation (@RequestParam(value="date") String date, @RequestParam(value="shift") int shift, @RequestParam(value="email") String userEmail, @RequestParam(value="table1") int table1, @RequestParam(value="table2", defaultValue="-1") int table2, @RequestParam(value="table3", defaultValue="-1") int table3) {
        if (!date.isEmpty() && shift >= 1 && shift <= 3 && !userEmail.isEmpty()) {
            List<Integer> tables = addTablesToList(table1, table2, table3);
            
            if (tables.isEmpty()) return false;
                        
            if (checkIfUserHasReservation(date, shift, userEmail)) return false;
            
            sqlQuery = "INSERT INTO reservations (date, shift, user_id) "
                    + "VALUES ('" + date + "', " + shift + ", "
                    + "(SELECT id FROM users WHERE email = '" + userEmail + "' LIMIT 1))";
            
            try {
                jdbcTemplate.update(sqlQuery);
            } 
            catch (DataAccessException e) {
                return false;
            }
            
            for (int i = 0; i < tables.size(); i++) {
              sqlQuery = "INSERT INTO tables_has_reservations (table_id, reservation_id) VALUES (" + tables.get(i) + ",(SELECT id FROM reservations ORDER BY id DESC LIMIT 1))";
              
                try {
                    jdbcTemplate.update(sqlQuery);
                } 
                catch (DataAccessException e) {
                    return false;
                }
            }

            return true;
        }
        
        return false;
    }
    
    private List<Integer> addTablesToList (int table1, int table2, int table3) {
        List<Integer> tables = new ArrayList<>();
        
        if (table1 != -1) {
            tables.add(table1);
            if (table2 != -1) tables.add(table2);
            if (table3 != -1) tables.add(table3);
        }
        
        return tables;
    }
    
    private boolean checkIfUserHasReservation (String date, int shift, String userEmail) {
        sqlQuery = "SELECT COUNT(*) FROM reservations WHERE date = '" + date + "' AND shift = " + shift + " AND user_id = (SELECT id FROM users WHERE email = '" + userEmail + "' LIMIT 1)";
        
        int count;
        
        try {
            count = jdbcTemplate.queryForObject(sqlQuery, Integer.class);
        } 
        catch (DataAccessException e) {
            return true;
        }
        
        if (count != 0) return true;
        return false;
    }
}