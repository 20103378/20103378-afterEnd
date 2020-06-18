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
* @date 2020-06-10 17:46:12
*/
@Data
@TableName("gas")
public class Gas {

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 删除时间
     */
    @TableField("delete_time")
    private Date deleteTime;

    /**
     * 气体名称
     */
    @TableField("gas_name")
    private String gasName;

    /**
     * 气体id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 1创建，0删除
     */
    @TableField("STATUS")
    private String status;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}