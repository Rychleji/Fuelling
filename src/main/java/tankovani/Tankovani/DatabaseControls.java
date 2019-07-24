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
    
    private boolean isLicenceLengthOk(String licence){
        return licence.length()<=8;
    }
    
    private boolean isPriceOk(double price){
        return price <= 99.9 && price >= 0;
    }
    
    private boolean isLitresOk(double litres){
        return litres >= 0;
    }
    
    private boolean isCityLengthOk(String city){
        return city.length()<=60;
    }
    
    private boolean isColourLengthOk(String colour){
        return colour.length()<=20;
    }
    
    private boolean isMileageOk(int mileage){
        return mileage>=0;
    }
    
    private boolean isLicenceFilled(String licence){
        return licence.length()>0;
    }
    
    private boolean carExists(String licence){
        String sql = "SELECT count(*) FROM CAR WHERE LICENCE_PLATE = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { licence }, Integer.class);

        return count>0;
    }
    
    private boolean isCarOk(Car car) throws Exception{
        if(!isLicenceLengthOk(car.getLicencePlate())){
            throw new Exception("Too many characters in a Licence plate");
        }
        if(!isLicenceFilled(car.getLicencePlate())){
            throw new Exception("Licence plate is empty");
        }
        if(carExists(car.getLicencePlate())){
            throw new Exception("Car with this licence plate alredy exists");
        }
        if(!isMileageOk(car.getCurrentMileage())){
            throw new Exception("Mileage can not be a negative number");
        }
        if(!isColourLengthOk(car.getColour())){
            throw new Exception("Too many characters in a Colour");
        }
        return true;
    }
    
    private boolean isFuellingOk(Fuelling fuel) throws Exception{
        if(!isLicenceLengthOk(fuel.getRefuelledCar())){
            throw new Exception("Too many characters in a Licence plate");
        }
        if(!isLicenceFilled(fuel.getRefuelledCar())){
            throw new Exception("Licence plate is empty");
        }
        if(!carExists(fuel.getRefuelledCar())){
            throw new Exception("Car with this licence plate doesn't exist");
        }
        if(!isPriceOk(fuel.getPricePerLitre())){
            throw new Exception("Price has to be between 0.0 and 99.9 (including)");
        }
        if(!isLitresOk(fuel.getPricePerLitre())){
            throw new Exception("Litres can not be a negative number");
        }
        if(!isCityLengthOk(fuel.getCity())){
            throw new Exception("Too many characters in a City");
        }
        return true;
    }
    
    @Transactional
    public void addCar(Car car) throws Exception {
        logger.info("Adding car: " + car.getLicencePlate());
        
        isCarOk(car);
        
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("CAR");
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LICENCE_PLATE", car.getLicencePlate());
        parameters.put("COLOUR", car.getColour());
        parameters.put("CURRENT_MILEAGE", car.getCurrentMileage());
        
        simpleJdbcInsert.execute(parameters);
    }
    
    @Transactional
    public void addFuelling(Fuelling fuelling) throws Exception {
        
        isFuellingOk(fuelling);
        
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
    public void editCar(Car car, String oldLicence) throws Exception {
        logger.info("Editing car: " + car.getLicencePlate());
        
        isCarOk(car);
        
        jdbcTemplate.update("update CAR "
                + "set LICENCE_PLATE = (?), COLOUR = (?), CURRENT_MILEAGE = (?) "
                + "where LICENCE_PLATE = (?)", car.getLicencePlate(), car.getColour(),
                car.getCurrentMileage(), oldLicence);
    }
    
    @Transactional
    public void editFuelling(Fuelling fuelling) throws Exception {
        isFuellingOk(fuelling);
        
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
    
    public Car findCar(String licence){
        String sql = "SELECT * FROM CAR WHERE LICENCE_PLATE = ?";
        Car car = (Car)jdbcTemplate.queryForObject(
			sql, new Object[] { licence }, 
			new BeanPropertyRowMapper(Car.class));        
        return car;
    }
    
    public List<Car> findAllCars() {
        String sql = "SELECT * FROM CAR";
		
	List<Car> cars  = jdbcTemplate.query(sql,
			new BeanPropertyRowMapper(Car.class));
		
	return cars;
    }
    
    public Fuelling findFuelling(String id){
        String sql = "SELECT * FROM FUELLING WHERE ID = ?";
        Fuelling fuel = (Fuelling)jdbcTemplate.queryForObject(
			sql, new Object[] { id }, 
			new BeanPropertyRowMapper(Fuelling.class));
        return fuel;
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
