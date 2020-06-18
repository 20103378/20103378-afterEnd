package cc.mrbird.febs.cable.system.controller;

import cc.mrbird.febs.cable.system.entity.Device;
import cc.mrbird.febs.cable.system.service.IDeviceService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.constant.StringConstant;
import cc.mrbird.febs.common.core.entity.system.Role;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author MrBird
 * @date 2020-06-10 18:25:48
 */
@Slf4j
@Validated
@RestController
@RequestMapping("device")
@RequiredArgsConstructor
public class DeviceController {

    private final IDeviceService deviceService;

    @GetMapping
    @PreAuthorize("hasAuthority('device:view')")
    public FebsResponse deviceList(QueryRequest queryRequest, Device device) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(deviceService.finDeviceDetailList(queryRequest,device));
        return new FebsResponse().data(dataTable);
    }
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('device:view')")
    public FebsResponse findDevices(Device device) {
        return new FebsResponse().data(this.deviceService.findDevices(device));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('device:add')")
    public void addDevice(@Valid Device device) throws FebsException {
        try {
            this.deviceService.createDevice(device);
        } catch (Exception e) {
            String message = "新增Device失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('device:delete')")
    public void deleteDevice(Device device) throws FebsException {
        try {
            this.deviceService.deleteDevice(device);
        } catch (Exception e) {
            String message = "删除Device失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping("/{deviceIds}")
    @PreAuthorize("hasAuthority('device:delete')")
    public void deleteGass(@NotBlank(message = "{required}") @PathVariable String deviceIds) {
        String[] ids = deviceIds.split(StringConstant.COMMA);
        this.deviceService.deleteDevices(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('device:update')")
    public void updateDevice(Device device) throws FebsException {
        try {
            this.deviceService.updateDevice(device);
        } catch (Exception e) {
            String message = "修改Device失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
