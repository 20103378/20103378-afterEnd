package cc.mrbird.febs.cable.system.entity;

import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
*  Entity
*
* @author MrBird
* @date 2020-06-10 18:25:48
*/
@Data
@TableName("device")
public class Device {

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 1创建，0删除
     */
    @TableField("STATUS")
    private String status;

    /**
     * 删除时间
     */
    @TableField("delete_time")
    private Date deleteTime;

    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 维度
     */
    @TableField("latitude")
    private Double latitude;

    /**
     * 精度
     */
    @TableField("longitude")
    private Double longitude;

    /**
     * 采样周期
     */
    @TableField("sample_period")
    private Integer samplePeriod;

    /**
     * 主题
     */
    @TableField("topic")
    private String topic;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}