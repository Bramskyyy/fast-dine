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
    
    private String sql;
    
    @RequestMapping("/userPassword")
    public String getUserPassword (@RequestParam(value="email") String userEmail) {
        sql = "SELECT password FROM users WHERE email= ? LIMIT 1";
        
        return jdbcTemplate.queryForObject(sql, new Object[] { userEmail }, String.class);
    }
}