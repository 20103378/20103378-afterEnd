package cc.mrbird.febs.cable.system.service;

import cc.mrbird.febs.cable.system.entity.Gas;

import cc.mrbird.febs.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-06-10 17:46:12
 */
public interface IGasService extends IService<Gas> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param gas gas
     * @return IPage<Gas>
     */
    IPage<Gas> findGass(QueryRequest request, Gas gas);

    /**
     * 查询（所有）
     *
     * @param gas gas
     * @return List<Gas>
     */
    List<Gas> findGass(Gas gas);

    /**
     * 新增
     *
     * @param gas gas
     */
    void createGas(Gas gas);

    /**
     * 修改
     *
     * @param gas gas
     */
    void updateGas(Gas gas);

    /**
     * 删除
     *
     * @param gas gas
     */
    void deleteGas(Gas gas);

    void deleteGass(String[] ids);

    /**
     * 分页查询
     * @param gas
     * @param queryRequest
     * @return
     */
    IPage<Gas> findGasDetailList(Gas gas, QueryRequest queryRequest);

    /**
     * 查询气体通过气体名称
     * @param gasName
     * @return
     */
    Gas fingByGasName(String gasName);
}
