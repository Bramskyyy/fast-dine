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
    @RequestMapping("/userPassword")
    public String getUserPasswordByEmail (@RequestParam(value="email") String userEmail) {
        if (!userEmail.isEmpty()) {
            sql = "SELECT password FROM users WHERE email= ? LIMIT 1";
            
            try 
            {
                String result = jdbcTemplate.queryForObject(sql, new Object[] { userEmail }, String.class);
                if (!result.equals("")) return result;
            }
            catch (Exception e)
            {
                return "Exception: " + e.getMessage();
            }
        }
        
        return null;
    }
    
    // Eventueel nog opkuisen maar werkt volgens mij (kevin bourgonjon) foutloos.
    @RequestMapping("/newUser")
    public boolean newUser (@RequestParam(value="name", defaultValue="") String name, @RequestParam(value="email") String email, @RequestParam(value="telephone", defaultValue="") String telephone, @RequestParam(value="type") String type, @RequestParam(value="password") String password) {
        if (!email.isEmpty()) {
            sql = "SELECT count(*) FROM users WHERE email= ?";
            
            try
            {
                int result = jdbcTemplate.queryForObject(sql, new Object[] { email }, Integer.class);
                if (result > 0) return false;
            }
            catch (Exception e)
            {
                return false;
            }
            
            if (!type.isEmpty() && !password.isEmpty()) {
                sql = "INSERT INTO users (id, name, email, telephone, type, password) VALUES (?, ?, ?, ?, ?, ?)";
                
                Random rand = new Random();
                int userId = rand.nextInt((50000 - 0) + 1) + 0;
                
                User user = new User(userId, name, email, telephone, type, password);
                
                jdbcTemplate.update(sql, new Object[] { 
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getType(),
                    user.getPassword()
                });
                
                return true;
            }
        }
        
        return false;
    }
}