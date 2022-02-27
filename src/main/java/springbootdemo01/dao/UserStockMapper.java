package springbootdemo01.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import springbootdemo01.entity.UserStock;

@Mapper
public interface UserStockMapper extends BaseMapper<UserStock> {
}
