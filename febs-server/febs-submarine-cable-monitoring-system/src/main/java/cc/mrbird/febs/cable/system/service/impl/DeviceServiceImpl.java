package cc.mrbird.febs.cable.system.service.impl;

import cc.mrbird.febs.cable.system.entity.Device;
import cc.mrbird.febs.cable.system.entity.Gas;
import cc.mrbird.febs.cable.system.mapper.DeviceMapper;
import cc.mrbird.febs.cable.system.service.IDeviceService;
import cc.mrbird.febs.common.core.entity.constant.FebsConstant;
import cc.mrbird.febs.common.core.entity.system.Role;
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
 * @date 2020-06-10 18:25:48
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    private final DeviceMapper deviceMapper;

    @Override
    public IPage<Device> findDevices(QueryRequest request, Device device) {
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<Device> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Device> findDevices(Device device) {
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDevice(Device device) {
        this.save(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevice(Device device) {
        this.saveOrUpdate(device);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDevice(Device device) {
        LambdaQueryWrapper<Device> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }

    @Override
    public IPage<Device> finDeviceDetailList(QueryRequest queryRequest, Device device) {
        Page<Gas> page = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize());
        SortUtil.handlePageSort(queryRequest, page, "update_time", FebsConstant.ORDER_DESC, false);
        return this.baseMapper.findDeviceDetailPage(page, device);    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDevices(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }

    @Override
    public Device findByDeviceId(String deviceID) {
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Device::getDeviceId, deviceID);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
