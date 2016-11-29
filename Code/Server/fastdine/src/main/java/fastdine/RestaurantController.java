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
    JdbcTemplate jdbcTemplate;
    
    private List<Restaurant> restaurants;
    private String sql;

    @RequestMapping("/restaurant")
    public List<Restaurant> restaurant(@RequestParam(value="name") String restaurantName) {
        restaurants = new ArrayList<Restaurant>();
        
        sql = "SELECT * FROM restaurants WHERE name='" + restaurantName + "'";
        
        jdbcTemplate.query(
                sql,
                (rs, rowNum) -> restaurants.add(new Restaurant(rs.getInt("restaurant_id"), rs.getString("name"), rs.getString("location"), rs.getString("email"), rs.getString("telephone"), rs.getInt("seats")))
        );
        
        if (restaurants.size() != 0)
            return restaurants;
        else
            return null;
    }
    
    @RequestMapping("/restaurants")
    public List<Restaurant> restaurant() {
        restaurants = new ArrayList<Restaurant>();
        
        sql = "SELECT * FROM restaurants";
        
        jdbcTemplate.query(
                sql,
                (rs, rowNum) -> restaurants.add(new Restaurant(rs.getInt("restaurant_id"), rs.getString("name"), rs.getString("location"), rs.getString("email"), rs.getString("telephone"), rs.getInt("seats")))
        );

        return restaurants;
    }
}