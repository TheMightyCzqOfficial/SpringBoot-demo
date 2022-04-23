package springbootdemo01.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import springbootdemo01.entity.Data;
import springbootdemo01.entity.StockInfo;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface StockInfoMapper extends BaseMapper<StockInfo> {
    @Select("SELECT * FROM stockinfo ORDER BY pchange DESC limit 0,200")
    List<StockInfo> getHigh();
    @Select("SELECT * FROM stockinfo ORDER BY pchange ASC limit 0,200")
    List<StockInfo> getLow();
    @Select("SELECT * from stockinfo WHERE code in(SELECT code from morenka) ORDER BY pchange DESC")
    List<StockInfo> getMorenka();
//    public List<StockInfo> findAll();
//
//    public List<Data> findByCode(String code);
//
//    public StockInfo findInfoByCode(String code);
//
//    public List<StockInfo> searchStock(HashMap<String,Object> map);

}
