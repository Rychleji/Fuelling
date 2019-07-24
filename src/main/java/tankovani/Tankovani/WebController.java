package tankovani.Tankovani;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    
    private final DatabaseControls database;

    public WebController(DatabaseControls database) {
        this.database = database;
    }
    
    @GetMapping("/cars")
    public String cars(Model model) {
        
        model.addAttribute("cars", database.findAllCars());
        return "cars";
    }
    
    @GetMapping("/fuelling")
    public String fuelling(@RequestParam(name="car", required=true) String licence, Model model) {
        
        model.addAttribute("fuellings", database.findFuellingsByCar(licence));
        return "fuelling";
    }
    
    @GetMapping("/remove")
    public String remove(@RequestParam(name="car", required=false, defaultValue = "") String licence, @RequestParam(name="fuel", required=false, defaultValue = "-1") String id, Model model) {
        if (!"".equals(licence))
            database.removeCar(licence);
        if(!"-1".equals(id))
            database.removeFuelling(Long.parseLong(id));
        return "remove";
    }
    
    @GetMapping("/caredit")
    public String carEdit(@RequestParam(name="car", required=false, defaultValue = "") String licence, Model model) {
        if(licence.equals("")){
            model.addAttribute("car", new Car());
        }else{
            model.addAttribute("car", database.findCar(licence));
        }
        return "caredit";
    }
    
    @GetMapping("/fuellingedit")
    public String fuellingEdit(@RequestParam(name="fuel", required=false, defaultValue = "") String id, Model model) {
        if(id.equals("")){
            model.addAttribute("fuelling", new Fuelling());
        }else{
            model.addAttribute("fuelling", database.findFuelling(id));
        }
        return "fuellingedit";
    }
    
    @GetMapping("/caredited")
    public String carEdited(@RequestParam(name="oldLocence", required=false, defaultValue = "") String oldLicence,
            @RequestParam(name="licence", required=true) String licence, 
            @RequestParam(name="colour", required=true) String colour, 
            @RequestParam(name="mileage", required=true) String mileage, Model model) {
        /*if(licence.equals("")){
            model.addAttribute("car", new Car());
        }else{
            model.addAttribute("car", database.findCar(licence));
        }*/
        return "caredited";
    }
    
    @GetMapping("/fuellingedited")
    public String fuellingEdited(@RequestParam(name="fuel", required=false, defaultValue = "") String id, Model model) {
        if(id.equals("")){
            model.addAttribute("fuelling", new Fuelling());
        }else{
            model.addAttribute("fuelling", database.findFuelling(id));
        }
        return "fuellingedited";
    }

}
