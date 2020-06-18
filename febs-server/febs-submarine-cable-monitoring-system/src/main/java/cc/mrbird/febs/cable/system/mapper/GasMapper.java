package cc.mrbird.febs.cable.system.mapper;

import cc.mrbird.febs.cable.system.entity.Gas;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-06-10 17:46:12
 */
public interface GasMapper extends BaseMapper<Gas> {

    <T> IPage<Gas> findGasDetailPage(Page<T> page, @Param("gas") Gas gas);

}
