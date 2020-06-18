package cc.mrbird.febs.cable.system.controller;

import cc.mrbird.febs.cable.system.entity.Gas;
import cc.mrbird.febs.cable.system.service.IGasService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.constant.StringConstant;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 *  Controller
 *
 * @author MrBird
 * @date 2020-06-10 17:46:12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gas")
@RequiredArgsConstructor
public class GasController {

    private final IGasService gasService;

    @GetMapping
    @PreAuthorize("hasAuthority('gas:view')")
    public FebsResponse gasList(QueryRequest queryRequest, Gas gas) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(gasService.findGasDetailList(gas, queryRequest));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gas:add')")
    public void addGas(@Valid Gas gas) throws FebsException {
        try {
            this.gasService.createGas(gas);
        } catch (Exception e) {
            String message = "新增Gas失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping("/{gasIds}")
    @PreAuthorize("hasAuthority('gas:delete')")
    public void deleteGass(@NotBlank(message = "{required}") @PathVariable String gasIds) {
        String[] ids = gasIds.split(StringConstant.COMMA);
        this.gasService.deleteGass(ids);
    }
    @PutMapping
    @PreAuthorize("hasAuthority('gas:update')")
    public void updateGas(Gas gas) throws FebsException {
        try {
            this.gasService.updateGas(gas);
        } catch (Exception e) {
            String message = "修改Gas失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
