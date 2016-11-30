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
}