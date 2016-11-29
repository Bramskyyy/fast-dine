package fastdine;

import dataEntities.Table;
import java.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {   
    Table c1 = new Table(1, 1, 1);
    Table c2 = new Table(2, 2, 2);
    Table c3 = new Table(3, 3, 3);

    @RequestMapping("/table")
    public Table table(@RequestParam(value="id") int id) {
        List<Table> tables = new ArrayList<Table>();
        tables.add(c1);
        tables.add(c2);
        tables.add(c3);
        
        for (int i = 0; i < tables.size(); i++) {
            Table r = tables.get(i);
            
            if (r.getId() == id) {
                return r;
            }
        }
        
        return null;
    }
    
    @RequestMapping("/tables")
    public List<Table> tables() {
        List<Table> tables = new ArrayList<Table>();
        tables.add(c1);
        tables.add(c2);
        tables.add(c3);
        
        return tables;
    }
}