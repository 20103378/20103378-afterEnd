package cc.mrbird.febs.cable.system.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : najiang
 * create at:  2020-04-16  19:27
 * @description:
 */
@Data
public class GasConcentrationDTO implements Serializable {


    private static final long serialVersionUID = -495032026866405502L;




    /**
     * 创建时间
     */
    private Date time;

    /**
     * 气体名称
     */
    private String gasName;


    /**
     * 设备浓度
     */
    private Double concentration;


}