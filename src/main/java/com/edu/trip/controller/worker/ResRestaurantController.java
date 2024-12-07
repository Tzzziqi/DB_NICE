package com.edu.trip.controller.worker;

import com.edu.trip.jpa.RestaurantRepository;
import com.edu.trip.model.RestaurantEntity;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee/resource/restaurant")
@RestController
public class ResRestaurantController {

    @Autowired
    private RestaurantRepository database;


    @GetMapping("/read")
    public ResponseEntity read(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok(
                StringUtils.isBlank(keyword) ?
                        database.findAllByStatus(ResourceStatus.ACTIVE.name()) :
                        database.findAllByNameContainingAndStatus(keyword.strip(), ResourceStatus.ACTIVE.name())
        );
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody RestaurantEntity restaurantEntity) {
        restaurantEntity.setRestaurantId(0L);
        if (
                StringUtils.isBlank(restaurantEntity.getName()) ||
                        StringUtils.isBlank(restaurantEntity.getRestaurantType()) ||
                        StringUtils.isBlank(restaurantEntity.getDescription()) ||
                        StringUtils.isBlank(restaurantEntity.getServingTime()) ||
                        restaurantEntity.getFloor() <= 0 || restaurantEntity.getFloor() > 20) {
            return ResponseUtil.fail("Invalid restaurant parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        restaurantEntity.setStatus(ResourceStatus.ACTIVE.name());
        RestaurantEntity exists = database.findByNameAndFloorAndStatus(restaurantEntity.getName(), restaurantEntity.getFloor(), ResourceStatus.ACTIVE.name());
        if (exists != null) {
            return ResponseUtil.fail("There is already a restaurant with the same name on the same floor.");
        }
        database.saveAndFlush(restaurantEntity);
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody RestaurantEntity restaurantEntity) {
        if (restaurantEntity.getRestaurantId() == null ||
                StringUtils.isBlank(restaurantEntity.getName()) ||
                StringUtils.isBlank(restaurantEntity.getRestaurantType()) ||
                StringUtils.isBlank(restaurantEntity.getDescription()) ||
                StringUtils.isBlank(restaurantEntity.getServingTime()) ||
                restaurantEntity.getFloor() <= 0 || restaurantEntity.getFloor() > 20) {
            return ResponseUtil.fail("Invalid restaurant parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        RestaurantEntity exists = database.findByRestaurantIdAndStatus(restaurantEntity.getRestaurantId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Restaurant not exists.");
        }
        RestaurantEntity sameFloor = database.findByNameAndFloorAndStatus(restaurantEntity.getName(), restaurantEntity.getFloor(), ResourceStatus.ACTIVE.name());
        if (sameFloor != null) {
            return ResponseUtil.fail("There is already a restaurant with the same name on the same floor.");
        }
        restaurantEntity.setStatus(exists.getStatus());
        database.saveAndFlush(restaurantEntity);
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody RestaurantEntity restaurantEntity) {
        if (restaurantEntity.getRestaurantId() == null) {
            return ResponseUtil.fail("Restaurant not exists.");
        }
        RestaurantEntity exists = database.findByRestaurantIdAndStatus(restaurantEntity.getRestaurantId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Restaurant not exists.");
        }
        exists.setStatus(ResourceStatus.DELETED.name());
        database.saveAndFlush(exists);
        return null;
    }
}
