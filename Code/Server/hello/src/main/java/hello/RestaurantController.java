package hello;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    private static final String template = "%s";
    private final AtomicLong counter = new AtomicLong();
    
    Restaurant r1 = new Restaurant(1, "Gust", 8, "Annonciadenstraat 4, 9000 Gent");
    Restaurant r2 = new Restaurant(2, "Souplounge", 16, "Zuivelbrugstraat 4, 9000 Gent");
    Restaurant r3 = new Restaurant(3, "Tasty World", 12, "Hoogpoort 1, 9000 Gent");

    @RequestMapping("/restaurant")
    public Restaurant restaurant(@RequestParam(value="name") String name) {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
        
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
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
        
        return restaurants;
    }
}