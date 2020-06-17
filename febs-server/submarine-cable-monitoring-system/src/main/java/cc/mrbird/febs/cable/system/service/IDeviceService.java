package cc.mrbird.febs.cable.system.service;


import cc.mrbird.febs.cable.system.entity.Device;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.system.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-06-10 18:25:48
 */
public interface IDeviceService extends IService<Device> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param device device
     * @return IPage<Device>
     */
    IPage<Device> findDevices(QueryRequest request, Device device);

    /**
     * 查询（所有）
     *
     * @param device device
     * @return List<Device>
     */
    List<Device> findDevices(Device device);

    /**
     * 新增
     *
     * @param device device
     */
    void createDevice(Device device);

    /**
     * 修改
     *
     * @param device device
     */
    void updateDevice(Device device);

    /**
     * 删除
     *
     * @param device device
     */
    void deleteDevice(Device device);

    /**
     * 查询分页
     * @param queryRequest
     * @param device
     * @return
     */
    IPage<Device> finDeviceDetailList(QueryRequest queryRequest, Device device);

    /**
     * 批量删除设备
     * @param ids
     */
    void deleteDevices(String[] ids);

    /**
     * 通过设备id查询设备
     * @param deviceID
     * @return
     */
    Device findByDeviceId(String deviceID);
}
