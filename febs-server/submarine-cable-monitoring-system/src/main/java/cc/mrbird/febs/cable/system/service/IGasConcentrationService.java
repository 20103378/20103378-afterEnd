package cc.mrbird.febs.cable.system.service;


import cc.mrbird.febs.cable.system.entity.GasConcentration;
import cc.mrbird.febs.cable.system.entity.dto.GasConcentrationDTO;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-06-10 18:25:43
 */
public interface IGasConcentrationService extends IService<GasConcentration> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param gasConcentration gasConcentration
     * @return IPage<GasConcentration>
     */
    IPage<GasConcentration> findGasConcentrations(QueryRequest request, GasConcentration gasConcentration);

    /**
     * 查询（所有）
     *
     * @param gasConcentration gasConcentration
     * @return List<GasConcentration>
     */
    List<GasConcentration> findGasConcentrations(GasConcentration gasConcentration);

    /**
     * 新增
     *
     * @param gasConcentration gasConcentration
     */
    void createGasConcentration(GasConcentration gasConcentration);

    /**
     * 修改
     *
     * @param gasConcentration gasConcentration
     */
    void updateGasConcentration(GasConcentration gasConcentration);

    /**
     * 删除
     *
     * @param gasConcentration gasConcentration
     */
    void deleteGasConcentration(GasConcentration gasConcentration);

    /**
     * 查询在开始时间到结束时间到所有时间列表
     * @param startTime
     * @param endTime
     * @param deviceId
     * @return
     */
    List<GasConcentrationDTO> findGasConcentrations(String deviceId, String startTime, String endTime);
}
