package Controllers;

import fastdine.dataEntities.Restaurant;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
    private Restaurant r1 = new Restaurant(1, "Gust", "Annonciadenstraat 4, 9000 Gent", "gust@mail.com","123456789", 8);
    private Restaurant r2 = new Restaurant(1, "Souplounge", "Zuivelbrugstraat 4, 9000 Gent", "souplounge@mail.com", "123456789", 16);
    private Restaurant r3 = new Restaurant(1, "Tasty World", "Hoogpoort 1, 9000 Gent", "tastyworld@mail.com", "123456789", 12);
    
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    
    public void AddRestaurants() {
        restaurants.clear();
        
        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);        
    }

    @RequestMapping("/restaurant")
    public Restaurant restaurant(@RequestParam(value="name") String name) {
        AddRestaurants();
        
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant r = restaurants.get(i);
            if (r.getName().equals(name)) {
                return r;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/restaurants")
    public List<Restaurant> restaurant() { 
        AddRestaurants();
        
        return restaurants;
    }
}