package tankovani.Tankovani;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    private final DatabaseControls database;

    public WebController(DatabaseControls database) {
        this.database = database;
    }
    
    @GetMapping("/cars")
    public String greeting(Model model) {
        
        model.addAttribute("cars", database.findAllCars());
        return "cars";
    }

}
