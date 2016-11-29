package fastdine;

import dataEntities.User;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private List<User> users;
    private String sql;
    
    @RequestMapping("/user")
    public String user(@RequestParam(value="email") String userEmail) {
        users = new ArrayList<User>();
        sql = "SELECT password FROM users WHERE email='" + userEmail + "' LIMIT 1";
        
        jdbcTemplate.query(
                sql,
                (rs, rowNum) -> users.add(new User(0,"","","","",rs.getString("password")))
        );
        
        return users.get(0).getPassword();
    }
}