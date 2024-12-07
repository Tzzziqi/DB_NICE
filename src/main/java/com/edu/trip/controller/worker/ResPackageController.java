package com.edu.trip.controller.worker;

import com.edu.trip.jpa.PackageRepository;
import com.edu.trip.model.PackageEntity;
import com.edu.trip.po.ChargeType;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee/resource/package")
@RestController
public class ResPackageController {

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

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PackageEntity packageEntity) {
        packageEntity.setPackageId(0L);
        if (
                StringUtils.isBlank(packageEntity.getName()) ||
                        StringUtils.isBlank(packageEntity.getChargeType()) ||
                        StringUtils.isBlank(packageEntity.getDescription()) ||
                        packageEntity.getPrice() <= 0 || packageEntity.getPrice() > 2000) {
            return ResponseUtil.fail("Invalid package parameters. All details should be specified. Price should be > 0 && <= 2000$");
        }
        if (!ChargeType.TRIP.name().equals(packageEntity.getChargeType()) && !ChargeType.NIGHT.name().equals(packageEntity.getChargeType())) {
            return ResponseUtil.fail("Invalid charge type.");
        }
        packageEntity.setStatus(ResourceStatus.ACTIVE.name());
        PackageEntity exists = database.findByNameAndStatus(packageEntity.getName(),ResourceStatus.ACTIVE.name());
        if (exists != null) {
            return ResponseUtil.fail("There is already a package with the same name.");
        }
        database.saveAndFlush(packageEntity);
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody PackageEntity packageEntity) {
        if (packageEntity.getPackageId() == null ||
                StringUtils.isBlank(packageEntity.getName()) ||
                StringUtils.isBlank(packageEntity.getChargeType()) ||
                StringUtils.isBlank(packageEntity.getDescription()) ||
                packageEntity.getPrice() <= 0 || packageEntity.getPrice() > 2000) {
            return ResponseUtil.fail("Invalid package parameters. All details should be specified. Floor should be > 0 && <= 20");
        }
        if (!ChargeType.TRIP.name().equals(packageEntity.getChargeType()) && !ChargeType.NIGHT.name().equals(packageEntity.getChargeType())) {
            return ResponseUtil.fail("Invalid charge type.");
        }

        PackageEntity exists = database.findByPackageIdAndStatus(packageEntity.getPackageId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Package not exists.");
        }
        PackageEntity sameName = database.findByNameAndStatus(packageEntity.getName(), ResourceStatus.ACTIVE.name());
        if (sameName != null) {
            return ResponseUtil.fail("There is already a package with the same name.");
        }
        packageEntity.setStatus(exists.getStatus());
        database.saveAndFlush(packageEntity);
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody PackageEntity packageEntity) {
        if (packageEntity.getPackageId() == null) {
            return ResponseUtil.fail("Package not exists.");
        }
        PackageEntity exists = database.findByPackageIdAndStatus(packageEntity.getPackageId(), ResourceStatus.ACTIVE.name());
        if (exists == null) {
            return ResponseUtil.fail("Package not exists.");
        }
        exists.setStatus(ResourceStatus.DELETED.name());
        database.saveAndFlush(exists);
        return null;
    }
}
