package com.edu.trip.controller.worker;

import com.edu.trip.data_view.ShipConfView;
import com.edu.trip.jpa.EntertainmentRepository;
import com.edu.trip.jpa.PortRepository;
import com.edu.trip.jpa.RestaurantRepository;
import com.edu.trip.model.PortEntity;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee/resource/port")
@RestController
public class ResPortController {

    @Autowired
    private EntertainmentRepository entertainmentRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PortRepository portRepository;

    @GetMapping("/read")
    public ResponseEntity read(@RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok(
                StringUtils.isBlank(keyword) ?
                        portRepository.findAllByStatus(ResourceStatus.ACTIVE.name()) : portRepository.findAllByKeyword(keyword.strip())
        );
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PortEntity portEntity) {
        portEntity.setPortId(0L);
        if (
                StringUtils.isBlank(portEntity.getName()) ||
                        StringUtils.isBlank(portEntity.getCountry()) ||
                        StringUtils.isBlank(portEntity.getState()) ||
                        StringUtils.isBlank(portEntity.getAddress()) ||
                        StringUtils.isBlank(portEntity.getNearestAirport()) ||
                        portEntity.getParkingSpots() < 0) {
            return ResponseUtil.fail("Invalid port parameters.");
        }
        portEntity.setStatus(ResourceStatus.ACTIVE.name());
        PortEntity exists = portRepository.findByNameAndStatus(portEntity.getName(), ResourceStatus.ACTIVE.name());
        if (exists != null) {
            return ResponseUtil.fail("Port with same name exists.");
        }
        portRepository.saveAndFlush(portEntity);
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody PortEntity portEntity) {
        if (portEntity.getPortId() == null ||
                StringUtils.isBlank(portEntity.getName()) ||
                        StringUtils.isBlank(portEntity.getCountry()) ||
                        StringUtils.isBlank(portEntity.getState()) ||
                        StringUtils.isBlank(portEntity.getAddress()) ||
                        StringUtils.isBlank(portEntity.getNearestAirport()) ||
                        portEntity.getParkingSpots() < 0) {
            return ResponseUtil.fail("Invalid port parameters.");
        }
        PortEntity exists = portRepository.findByPortIdAndStatus(portEntity.getPortId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Port not exists.");
        }
        PortEntity sameName = portRepository.findByNameAndStatus(portEntity.getName(), ResourceStatus.ACTIVE.name());
        if (sameName != null) {
            return ResponseUtil.fail("Port with same name exists.");
        }
        portEntity.setStatus(exists.getStatus());
        portRepository.saveAndFlush(portEntity);
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody PortEntity portEntity) {
        if (portEntity.getPortId() == null) {
            return ResponseUtil.fail("Port not exists.");
        }
        PortEntity exists = portRepository.findByPortIdAndStatus(portEntity.getPortId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Port not exists.");
        }
        exists.setStatus(ResourceStatus.DELETED.name());
        portRepository.saveAndFlush(exists);
        return null;
    }
}
