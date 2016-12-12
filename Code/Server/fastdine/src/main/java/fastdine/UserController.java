package fastdine;

import dataEntities.User;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private String sql;
    
    // Returns the hashed password for a user based on its email
    @RequestMapping("/user")
    public User getUserByEmail (@RequestParam(value="email") String userEmail) {
        if (!userEmail.isEmpty()) {
            sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
            
            try
            {
                User userByEmail = new User();
                if (!userEmail.isEmpty() && !userEmail.equals("0")) {            
                    userByEmail = jdbcTemplate.queryForObject(sql, new Object[] { userEmail }, (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("telephone"), rs.getString("type"), rs.getString("password")));
                }
        
                return userByEmail;
            }
            catch (Exception e)
            {
                throw e;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/newUser")
    public boolean newUser (@RequestParam(value="name", defaultValue="") String name, @RequestParam(value="email") String email, @RequestParam(value="telephone", defaultValue="") String telephone, @RequestParam(value="type") String type, @RequestParam(value="password") String password) {
        if (!email.isEmpty() && !type.isEmpty() && !password.isEmpty()) {
            sql = "SELECT count(*) FROM users WHERE email= ?";
            
            try {
                int result = jdbcTemplate.queryForObject(sql, new Object[] { email }, Integer.class);
                if (result > 0) return false;
            }
            catch (Exception e) {
                return false;
            }
            
            sql = "INSERT INTO users (name, email, telephone, type, password) VALUES (?, ?, ?, ?, ?)";

            User user = new User(name, email, telephone, type, password);
            
            try {
                jdbcTemplate.update(sql, new Object[] {
                    user.getName(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getType(),
                    user.getPassword()
                });
            } 
            catch (Exception e) {
                return false;
            }

            return true;
        }
        
        return false;
    }
}