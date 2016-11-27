package fastdine;

import fastdine.dataEntities.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
        log.info("Querying for restaurant records");
        jdbcTemplate.query(
                "SELECT * FROM restaurants",
                (rs, rowNum) -> new Restaurant(rs.getInt("restaurant_id"), rs.getString("naam"), rs.getString("locatie"), rs.getString("email"), rs.getString("telefoon"), rs.getInt("aantal_plaatsen"))
        ).forEach(Restaurant -> log.info(Restaurant.toString()));
    }
}