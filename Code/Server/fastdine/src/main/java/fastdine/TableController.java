package fastdine;

import dataEntities.Table;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {   
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private String sql;
    
    private List<String> tables;

    // Returns all free tables and their amount of seats for a given resto ID, shift and date
    @RequestMapping("/tables")
    public List<Table> getTablesByRestaurantIdAndShiftAndDate(@RequestParam(value="id") int id, @RequestParam(value="shift") String shift, @RequestParam(value="date") String date) {
        List<Table> tables = new ArrayList<Table>();
        
        sql = "select tables.id as tablesID, tables.seats as tablesSeats from tables " +
            "where tables.restaurant_id = " + id + " " + 
            "AND tables.id NOT IN (select tables.id from restaurants " +
            "inner join tables on restaurants.id = tables.restaurant_id " +
            "inner join tables_has_reservations on tables.id = tables_has_reservations.table_id " +
            "inner join reservations on tables_has_reservations.reservation_id = reservations.id " +
            "where restaurants.id = " + id +
            " AND reservations.date = '" + date + "'" +
            " AND reservations.shift = " + shift + ");";
       
        
        jdbcTemplate.query(
            sql,
            (rs, rowNum) -> tables.add(new Table(rs.getInt("tablesID"), rs.getInt("tablesSeats")))
        );
      
        if (!tables.isEmpty()) return tables;
               
        return null;
    }
}