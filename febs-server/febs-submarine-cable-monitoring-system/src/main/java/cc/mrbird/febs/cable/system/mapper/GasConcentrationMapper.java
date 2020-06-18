package cc.mrbird.febs.cable.system.mapper;

import cc.mrbird.febs.cable.system.entity.GasConcentration;
import cc.mrbird.febs.cable.system.entity.dto.GasConcentrationDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-06-10 18:25:43
 */
public interface GasConcentrationMapper extends BaseMapper<GasConcentration> {

    List<GasConcentrationDTO> findGasConcentrationBySearch(@Param("deviceId") String deviceId, @Param("startTime") String startTime, @Param("endTime") String endTime);

}
