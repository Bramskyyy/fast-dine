package fastdine;

import dataEntities.Restaurant;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class RestaurantController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private List<Restaurant> restaurants;
    private String sql;
    
    @RequestMapping("/restaurantid")
    public Restaurant getRestaurantById(@RequestParam(value="id") String id) {
        restaurants = new ArrayList<>();
        
        sql = "SELECT * FROM restaurants WHERE id = '" + id + "'";
        
        jdbcTemplate.query(
            sql,
            (rs, rowNum) -> restaurants.add(new Restaurant(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getString("email"), rs.getString("telephone"), rs.getInt("seats")))
         );
        
        if (!restaurants.isEmpty()) return restaurants.get(0);
            
        return null;
    }

    @RequestMapping("/restaurant")
    public List<Restaurant> getRestaurantByName(@RequestParam(value="name") String restaurantName) {
        
        restaurants = new ArrayList<>();
        
        // Will match any string containing a part or the entire name
        sql = "SELECT * FROM restaurants WHERE name LIKE '%" + restaurantName + "%'";
        
        jdbcTemplate.query(
            sql,
            (rs, rowNum) -> restaurants.add(new Restaurant(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getString("email"), rs.getString("telephone"), rs.getInt("seats")))
         );
        
        if (!restaurants.isEmpty()) return restaurants;
            
        return null;
    }
    
    // Returns a list with all restaurants
    @RequestMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        restaurants = new ArrayList<>();    
        sql = "SELECT * FROM restaurants";
        
        jdbcTemplate.query(
            sql,
            (rs, rowNum) -> restaurants.add(new Restaurant(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getString("email"), rs.getString("telephone"), rs.getInt("seats")))
        );

        if (!restaurants.isEmpty()) return restaurants;
            
        return null;
    }
}