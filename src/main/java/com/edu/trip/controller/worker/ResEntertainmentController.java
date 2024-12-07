package com.edu.trip.controller.worker;

import com.edu.trip.jpa.EntertainmentRepository;
import com.edu.trip.model.EntertainmentEntity;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee/resource/entertainment")
@RestController
public class ResEntertainmentController {

    @Autowired
    private EntertainmentRepository database;


    @GetMapping("/read")
    public ResponseEntity read(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok(
                StringUtils.isBlank(keyword) ?
                        database.findAllByStatus(ResourceStatus.ACTIVE.name()) :
                        database.findAllByNameContainingAndStatus(keyword.strip(), ResourceStatus.ACTIVE.name())
        );
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody EntertainmentEntity entertainmentEntity) {
        entertainmentEntity.setEntertainmentId(0L);
        if (
                StringUtils.isBlank(entertainmentEntity.getName()) ||
                        StringUtils.isBlank(entertainmentEntity.getEntertainmentType()) ||
                        StringUtils.isBlank(entertainmentEntity.getDescription()) ||
                        entertainmentEntity.getFloor() <= 0 || entertainmentEntity.getFloor() > 20) {
            return ResponseUtil.fail("Invalid entertainment parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        entertainmentEntity.setStatus(ResourceStatus.ACTIVE.name());
        EntertainmentEntity exists = database.findByNameAndFloorAndStatus(entertainmentEntity.getName(), entertainmentEntity.getFloor(), ResourceStatus.ACTIVE.name());
        if (exists != null) {
            return ResponseUtil.fail("There is already a entertainment with the same name on the same floor.");
        }
        database.saveAndFlush(entertainmentEntity);
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody EntertainmentEntity entertainmentEntity) {
        if (entertainmentEntity.getEntertainmentId() == null ||
                StringUtils.isBlank(entertainmentEntity.getName()) ||
                StringUtils.isBlank(entertainmentEntity.getEntertainmentType()) ||
                StringUtils.isBlank(entertainmentEntity.getDescription()) ||
                entertainmentEntity.getFloor() <= 0 || entertainmentEntity.getFloor() > 20) {
            return ResponseUtil.fail("Invalid entertainment parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        EntertainmentEntity exists = database.findByEntertainmentIdAndStatus(entertainmentEntity.getEntertainmentId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Entertainment not exists.");
        }
        EntertainmentEntity sameFloor = database.findByNameAndFloorAndStatus(entertainmentEntity.getName(), entertainmentEntity.getFloor(), ResourceStatus.ACTIVE.name());
        if (sameFloor != null) {
            return ResponseUtil.fail("There is already a entertainment with the same name on the same floor.");
        }
        entertainmentEntity.setStatus(exists.getStatus());
        database.saveAndFlush(entertainmentEntity);
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody EntertainmentEntity entertainmentEntity) {
        if (entertainmentEntity.getEntertainmentId() == null) {
            return ResponseUtil.fail("Entertainment not exists.");
        }
        EntertainmentEntity exists = database.findByEntertainmentIdAndStatus(entertainmentEntity.getEntertainmentId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Entertainment not exists.");
        }
        exists.setStatus(ResourceStatus.DELETED.name());
        database.saveAndFlush(exists);
        return null;
    }
}
