package cc.mrbird.febs.cable.system.service.impl;

import cc.mrbird.febs.cable.system.entity.GasConcentration;
import cc.mrbird.febs.cable.system.entity.dto.GasConcentrationDTO;
import cc.mrbird.febs.cable.system.mapper.GasConcentrationMapper;
import cc.mrbird.febs.cable.system.service.IGasConcentrationService;
import cc.mrbird.febs.common.core.entity.system.Dept;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Service实现
 *
 * @author MrBird
 * @date 2020-06-10 18:25:43
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GasConcentrationServiceImpl extends ServiceImpl<GasConcentrationMapper, GasConcentration> implements IGasConcentrationService {

    private final GasConcentrationMapper gasConcentrationMapper;

    @Override
    public IPage<GasConcentration> findGasConcentrations(QueryRequest request, GasConcentration gasConcentration) {
        LambdaQueryWrapper<GasConcentration> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<GasConcentration> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<GasConcentration> findGasConcentrations(GasConcentration gasConcentration) {
        LambdaQueryWrapper<GasConcentration> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createGasConcentration(GasConcentration gasConcentration) {
        this.save(gasConcentration);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGasConcentration(GasConcentration gasConcentration) {
        this.saveOrUpdate(gasConcentration);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGasConcentration(GasConcentration gasConcentration) {
        LambdaQueryWrapper<GasConcentration> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }

    @Override
    public List<GasConcentrationDTO> findGasConcentrations( String deviceId,String startTime, String endTime) {
         return  gasConcentrationMapper.findGasConcentrationBySearch(deviceId,startTime,endTime);
    }
}
