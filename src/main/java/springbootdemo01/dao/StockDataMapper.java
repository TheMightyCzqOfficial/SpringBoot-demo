package springbootdemo01.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import springbootdemo01.entity.Data;

@Mapper
public interface StockDataMapper extends BaseMapper<Data> {
    @Delete("DELETE FROM stockdata WHERE 1=1")
    public void delete();
}
