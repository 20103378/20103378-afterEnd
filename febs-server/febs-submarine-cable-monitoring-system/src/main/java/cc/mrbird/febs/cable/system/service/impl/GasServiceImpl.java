package cc.mrbird.febs.cable.system.service.impl;

import cc.mrbird.febs.cable.system.entity.Gas;
import cc.mrbird.febs.cable.system.mapper.GasMapper;
import cc.mrbird.febs.cable.system.service.IGasService;
import cc.mrbird.febs.common.core.entity.constant.FebsConstant;
import cc.mrbird.febs.common.core.entity.system.SystemUser;
import cc.mrbird.febs.common.core.utils.SortUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.util.Arrays;
import java.util.List;

/**
 *  Service实现
 *
 * @author MrBird
 * @date 2020-06-10 17:46:12
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GasServiceImpl extends ServiceImpl<GasMapper, Gas> implements IGasService {

    private final GasMapper gasMapper;

    @Override
    public IPage<Gas> findGass(QueryRequest request, Gas gas) {
        LambdaQueryWrapper<Gas> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<Gas> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Gas> findGass(Gas gas) {
        LambdaQueryWrapper<Gas> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createGas(Gas gas) {
        this.save(gas);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGas(Gas gas) {
        this.saveOrUpdate(gas);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGas(Gas gas) {
        LambdaQueryWrapper<Gas> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGass(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }

    @Override
    public IPage<Gas> findGasDetailList(Gas gas, QueryRequest queryRequest) {
        Page<Gas> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize());
        SortUtil.handlePageSort(queryRequest, page, "update_time", FebsConstant.ORDER_DESC, false);
        return this.baseMapper.findGasDetailPage(page, gas);
    }

    @Override
    public Gas fingByGasName(String gasName) {
        LambdaQueryWrapper<Gas> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Gas::getGasName, gasName);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
