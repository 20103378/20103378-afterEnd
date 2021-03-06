package cc.mrbird.febs.cable.system.mapper;

import cc.mrbird.febs.cable.system.entity.Device;
import cc.mrbird.febs.cable.system.entity.Gas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-06-10 18:25:48
 */
public interface DeviceMapper extends BaseMapper<Device> {

    IPage<Device> findDeviceDetailPage(Page<Gas> page, Device device);
}
