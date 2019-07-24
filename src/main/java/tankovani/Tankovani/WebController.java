package tankovani.Tankovani;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    
    private final DatabaseControls database;
    private final String exceptStr = "redirect:exception?ex=%s";

    public WebController(DatabaseControls database) {
        this.database = database;
    }
    
    @GetMapping("/cars")
    public String cars(Model model) {
        try{
            model.addAttribute("cars", database.findAllCars());
        }catch(NumberFormatException numberEx){
            return String.format(exceptStr, numberEx.getLocalizedMessage());
        /*}catch (InvalidResultSetAccessException e){
            return String.format(exceptStr, e.getLocalizedMessage());*/
        }catch (DataAccessException e){
            return String.format(exceptStr, e.getLocalizedMessage());
        }catch(Exception e){
            return String.format(exceptStr, e.getLocalizedMessage());
        }
        return "cars";
    }
    
    @GetMapping("/fuelling")
    public String fuelling(@RequestParam(name="car", required=true) String licence, Model model) {
        try{
            model.addAttribute("fuellings", database.findFuellingsByCar(licence));
        }catch(NumberFormatException numberEx){
            return String.format(exceptStr, numberEx.getLocalizedMessage());
        /*}catch (InvalidResultSetAccessException e){
            return String.format(exceptStr, e.getLocalizedMessage());*/
        }catch (DataAccessException e){
            return String.format(exceptStr, e.getLocalizedMessage());
        }catch(Exception e){
            return String.format(exceptStr, e.getLocalizedMessage());
        }
        return "fuelling";
    }
    
    @GetMapping("/remove")
    public String remove(@RequestParam(name="car", required=false, defaultValue = "") String licence, @RequestParam(name="fuel", required=false, defaultValue = "-1") String id, Model model) {
        if (!"".equals(licence))
            try{
                database.removeCar(licence);
            }catch(NumberFormatException numberEx){
                return String.format(exceptStr, numberEx.getLocalizedMessage());
            /*}catch (InvalidResultSetAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());*/
            }catch (DataAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }catch(Exception e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }
        if(!"-1".equals(id))
            try{
                database.removeFuelling(Long.parseLong(id));
            }catch(NumberFormatException numberEx){
                return String.format(exceptStr, numberEx.getLocalizedMessage());
            /*}catch (InvalidResultSetAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());*/
            }catch (DataAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }catch(Exception e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }
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
    public String fuellingEdit(@RequestParam(name="fuel", required=false, defaultValue = "") String id,
            @RequestParam(name="car", required=true) String carLicence, Model model) {
        if(id.equals("")){
            model.addAttribute("fuelling", new Fuelling());
        }else{
            model.addAttribute("fuelling", database.findFuelling(id));
        }
        model.addAttribute("oldCar", carLicence);
        return "fuellingedit";
    }
    
    @GetMapping("/caredited")
    public String carEdited(@RequestParam(name="oldLicence", required=false, defaultValue = "") String oldLicence,
            @RequestParam(name="licence", required=true) String licence, 
            @RequestParam(name="colour", required=true) String colour, 
            @RequestParam(name="mileage", required=true) String mileage, Model model) {
        if(oldLicence.equals("")){
            try{
                database.addCar(new Car(licence, colour, Integer.parseInt(mileage)));
            }catch(NumberFormatException numberEx){
                return String.format(exceptStr, numberEx.getLocalizedMessage());
            /*}catch (InvalidResultSetAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());*/
            }catch (DataAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }catch(Exception e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }
            
        }else{
            try{
                database.editCar(new Car(licence, colour, Integer.parseInt(mileage)), oldLicence);
            }catch(NumberFormatException numberEx){
                return String.format(exceptStr, numberEx.getLocalizedMessage());
            /*}catch (InvalidResultSetAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());*/
            }catch (DataAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }catch(Exception e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }
        }
        return "redirect:cars";
    }
    
    @GetMapping("/fuellingedited")
    public String fuellingEdited(@RequestParam(name="id", required=false, defaultValue = "0") String id,
            @RequestParam(name="litres", required=true) String litres, 
            @RequestParam(name="price", required=true) String price, 
            @RequestParam(name="city", required=true) String city,
            @RequestParam(name="car", required=true) String car, Model model) {
        if(id.equals("0")){
            try{
                database.addFuelling(new Fuelling(0, Double.parseDouble(litres), Double.parseDouble(price), city, car));
            }catch(NumberFormatException numberEx){
                return String.format(exceptStr, numberEx.getLocalizedMessage());
            /*}catch (InvalidResultSetAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());*/
            }catch (DataAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }catch(Exception e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }
        }else{
            try{
                database.editFuelling(new Fuelling(Integer.parseInt(id), Double.parseDouble(litres), Double.parseDouble(price), city, car));
            }catch(NumberFormatException numberEx){
                return String.format(exceptStr, numberEx.getLocalizedMessage());
            /*}catch (InvalidResultSetAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());*/
            }catch (DataAccessException e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }catch(Exception e){
                return String.format(exceptStr, e.getLocalizedMessage());
            }
        }
        return "redirect:fuelling?car="+car;
    }
    
    @GetMapping("/exception")
    public String exception(@RequestParam(name="ex", required=true) String e, Model model) {
        
        model.addAttribute("exception", e);
        
        return "exception";
    }
}
