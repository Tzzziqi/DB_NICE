package com.edu.trip.controller.worker;

import com.edu.trip.jpa.StateroomRepository;
import com.edu.trip.model.StateroomEntity;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee/resource/stateroom")
@RestController
public class ResStateroomController {

    @Autowired
    private StateroomRepository database;


    @GetMapping("/read")
    public ResponseEntity read() {
        return ResponseEntity.ok(database.findAllByStatus(ResourceStatus.ACTIVE.name()));
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody StateroomEntity stateroomEntity) {
        stateroomEntity.setStateroomId(0L);
        if (StringUtils.isBlank(stateroomEntity.getStateroomType())) {
            return ResponseUtil.fail("Invalid stateroom parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        stateroomEntity.setStatus(ResourceStatus.ACTIVE.name());
        database.saveAndFlush(stateroomEntity);
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody StateroomEntity stateroomEntity) {
        if (stateroomEntity.getStateroomId() == null) {
            return ResponseUtil.fail("Invalid stateroom parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        StateroomEntity exists = database.findByStateroomIdAndStatus(stateroomEntity.getStateroomId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Stateroom not exists.");
        }
        stateroomEntity.setStatus(exists.getStatus());
        database.saveAndFlush(stateroomEntity);
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody StateroomEntity stateroomEntity) {
        if (stateroomEntity.getStateroomId() == null) {
            return ResponseUtil.fail("Stateroom not exists.");
        }
        StateroomEntity exists = database.findByStateroomIdAndStatus(stateroomEntity.getStateroomId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Stateroom not exists.");
        }
        exists.setStatus(ResourceStatus.DELETED.name());
        database.saveAndFlush(exists);
        return null;
    }
}
