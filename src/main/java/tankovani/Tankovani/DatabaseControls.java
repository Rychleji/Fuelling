/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankovani.Tankovani;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseControls {
    private final static Logger logger = LoggerFactory.getLogger(DatabaseControls.class);

    private final JdbcTemplate jdbcTemplate;
    
    public DatabaseControls(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Transactional
    public void addCar(Car car) {
        logger.info("Adding car: " + car.getLicencePlate());
        
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("CAR");
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LICENCE_PLATE", car.getLicencePlate());
        parameters.put("COLOUR", car.getColour());
        parameters.put("CURRENT_MILEAGE", car.getCurrentMileage());
        
        simpleJdbcInsert.execute(parameters);
    }
    
    @Transactional
    public void addFuelling(Fuelling fuelling) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("FUELLING").usingGeneratedKeyColumns("ID");
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LITRES", fuelling.getLitres());
        parameters.put("PRICE_PER_LITRE", fuelling.getPricePerLitre());
        parameters.put("CITY", fuelling.getCity());
        parameters.put("REFUELLED_CAR", fuelling.getRefuelledCar());
        
        fuelling.setId((long) simpleJdbcInsert.executeAndReturnKey(parameters));
        logger.info("Added fuelling: " + fuelling.getId());
    }
    
    @Transactional
    public void editCar(Car car, String oldLicence) {
        logger.info("Editing car: " + car.getLicencePlate());
        
        jdbcTemplate.update("update CAR "
                + "set LICENCE_PLATE = (?), COLOUR = (?), CURRENT_MILEAGE = (?) "
                + "where LICENCE_PLATE = (?)", car.getLicencePlate(), car.getColour(),
                car.getCurrentMileage(), oldLicence);
    }
    
    @Transactional
    public void editFuelling(Fuelling fuelling) {
        logger.info("Editing fuelling: " + fuelling.getId());
        
        jdbcTemplate.update("update FUELLING "
                + "set LITRES = (?), PRICE_PER_LITRE = (?), CITY = (?), REFUELLED_CAR = (?) "
                + "where ID = (?)", fuelling.getLitres(), fuelling.getPricePerLitre(),
                fuelling.getCity(), fuelling.getRefuelledCar(), fuelling.getId());
    }
    
    /*public void addCars(Car... cars){
        for (Car c : cars){
            addCar(c);
        }
    }*/
    
    public List<Car> findAllCars() {
        String sql = "SELECT * FROM CAR";
		
	List<Car> cars  = jdbcTemplate.query(sql,
			new BeanPropertyRowMapper(Car.class));
		
	return cars;
    }
    
    public List<Fuelling> findFuellingsByCar(Car car){
        return findFuellingsByCar(car.getLicencePlate());
    }
    
    public List<Fuelling> findFuellingsByCar(String licence) {
        String sql = String.format("SELECT * FROM FUELLING WHERE REFUELLED_CAR LIKE '%s'", licence);
		
	List<Fuelling> fuellings  = jdbcTemplate.query(sql,
			new BeanPropertyRowMapper(Fuelling.class));
		
	return fuellings;
    }
    
    public void removeCar(Car car){
        removeCar(car.getLicencePlate());
    }
    
    public void removeCar(String licence){
        String deleteQuery = "delete from CAR where LICENCE_PLATE = (?)";
        jdbcTemplate.update(deleteQuery, licence);
    }
    
    public void removeFuelling(Fuelling fuelling){
        removeFuelling(fuelling.getId());
    }
    
    public void removeFuelling(long id){
        String deleteQuery = "delete from FUELLING where ID = ?";
        jdbcTemplate.update(deleteQuery, id);
    }
}
