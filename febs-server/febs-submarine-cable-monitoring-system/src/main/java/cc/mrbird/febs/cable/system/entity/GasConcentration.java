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
* @date 2020-06-10 18:25:43
*/
@Data
@TableName("gas_concentration")
public class GasConcentration {

    /**
     * 浓度
     */
    @TableField("concentration")
    private Double concentration;

    /**
     * 设备ID
     */
    @TableField("device_id")
    private Integer deviceId;

    /**
     * 气体ID
     */
    @TableField("gas_id")
    private Integer gasId;

    /**
     * 气体浓度逐渐id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("time")
    private Date time;

}