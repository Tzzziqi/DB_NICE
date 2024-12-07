package com.edu.trip.controller.user;

import com.edu.trip.jpa.PackageRepository;
import com.edu.trip.po.ResourceStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customer/resource/package")
@RestController
public class CusResPackageController {

    @Autowired
    private PackageRepository database;


    @GetMapping("/read")
    public ResponseEntity read(@RequestParam(value = "keyword", required = false) String keyword) {
        return ResponseEntity.ok(
                StringUtils.isBlank(keyword) ?
                        database.findAllByStatus(ResourceStatus.ACTIVE.name()) :
                        database.findAllByNameContainingAndStatus(keyword.strip(), ResourceStatus.ACTIVE.name())
        );
    }


}
