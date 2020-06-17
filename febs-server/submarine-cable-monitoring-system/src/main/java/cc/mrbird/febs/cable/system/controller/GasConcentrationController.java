package cc.mrbird.febs.cable.system.controller;

import cc.mrbird.febs.cable.system.entity.GasConcentration;
import cc.mrbird.febs.cable.system.entity.dto.GasConcentrationDTO;
import cc.mrbird.febs.cable.system.service.IGasConcentrationService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.common.redis.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  Controller
 *
 * @author MrBird
 * @date 2020-06-10 18:25:43
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gasConcentration")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GasConcentrationController {

    private final IGasConcentrationService gasConcentrationService;

    private final RedisService redisService;

    @GetMapping("/getGasConcentrationByDeviceId")
    public FebsResponse getGasConcentrationByDeviceId(String deviceId) {
        JSONObject jsonObject = new JSONObject(16);
        Map<Object, Object> resultMap = redisService.hmget(deviceId+"data");
        TreeSet<String> set = new TreeSet<>();
        List<String> list = new ArrayList<>();
        resultMap.forEach((key,value) -> {
            list.add((String) key);
            List<String[]> tmp = JSON.parseArray(JSONArray.parseObject(JSON.toJSONString(value),List.class).toString(), String[].class);
            for(int i=0;i<tmp.size();i++){
                set.add(tmp.get(i)[0]);
            }
        });
        set.comparator();
        jsonObject.put("time",set);
        jsonObject.put("legend",list);
        jsonObject.put("data",resultMap);
        return new FebsResponse().data(jsonObject);
    }
    @GetMapping("/getRecentGasConcentrationByDeviceId")
    public FebsResponse getRecentGasConcentrationByDeviceId(String deviceId) {
        Map<Object,Object> resultMap = redisService.hmget(deviceId+"data");
        Map<String, String> dataMap = new HashMap<>();
        resultMap.forEach((key,value) -> {
            List<String[]> tmp = JSON.parseArray(JSONArray.parseObject(JSON.toJSONString(value),List.class).toString(), String[].class);
            dataMap.put((String) key,tmp.get(tmp.size()-1)[1]);
        });
        return new FebsResponse().data(dataMap);
    }

    @GetMapping("/getGasConcentrationTrafficByParam")
    public FebsResponse getGasConcentrationTrafficByParam(String startTime, String endTime, String deviceId) {
        if(startTime==null && endTime==null && deviceId==null) {
            return new FebsResponse().data(null);
        }
        JSONObject jsonObject = new JSONObject(16);
        List<GasConcentrationDTO> list  =  gasConcentrationService.findGasConcentrations(deviceId,startTime,endTime);
        List<Date> time = list.stream().map(GasConcentrationDTO::getTime).distinct().collect(Collectors.toList());
        List<String> legend = list.stream().map(GasConcentrationDTO::getGasName).distinct().collect(Collectors.toList());
        Map<String, List<GasConcentrationDTO>> stringListMap = list.stream().collect(Collectors.groupingBy(GasConcentrationDTO::getGasName));
        Map<String,List<String[]>> resultMap = new HashMap<>();
        stringListMap.forEach((key,value) -> {
            List<String[]> tmpList = new ArrayList<>();
            for(int i=0;i<value.size();i++){
                String[] data = new String[0];
                try {
                    data = new String[]{DateUtil.formatCstTime((value.get(i)).getTime().toString(), DateUtil.FULL_TIME_SPLIT_PATTERN)
                            ,(value.get(i)).getConcentration().toString()};
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tmpList.add(data);
            }
            resultMap.put(key,tmpList);
        });
        jsonObject.put("time",time);
        jsonObject.put("legend",legend);
        jsonObject.put("data",resultMap);
        jsonObject.put("count",time.size()/20);
        return new FebsResponse().data(jsonObject);
    }



    @GetMapping
    @PreAuthorize("hasAuthority('gasConcentration:list')")
    public FebsResponse getAllGasConcentrations(GasConcentration gasConcentration) {
        return new FebsResponse().data(gasConcentrationService.findGasConcentrations(gasConcentration));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('gasConcentration:list')")
    public FebsResponse gasConcentrationList(QueryRequest request, GasConcentration gasConcentration) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.gasConcentrationService.findGasConcentrations(request, gasConcentration));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gasConcentration:add')")
    public void addGasConcentration(@Valid GasConcentration gasConcentration) throws FebsException {
        try {
            this.gasConcentrationService.createGasConcentration(gasConcentration);
        } catch (Exception e) {
            String message = "新增GasConcentration失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('gasConcentration:delete')")
    public void deleteGasConcentration(GasConcentration gasConcentration) throws FebsException {
        try {
            this.gasConcentrationService.deleteGasConcentration(gasConcentration);
        } catch (Exception e) {
            String message = "删除GasConcentration失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('gasConcentration:update')")
    public void updateGasConcentration(GasConcentration gasConcentration) throws FebsException {
        try {
            this.gasConcentrationService.updateGasConcentration(gasConcentration);
        } catch (Exception e) {
            String message = "修改GasConcentration失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
