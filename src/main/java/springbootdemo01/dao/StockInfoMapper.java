package springbootdemo01.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import springbootdemo01.entity.Data;
import springbootdemo01.entity.StockInfo;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface StockInfoMapper extends BaseMapper<StockInfo> {

//    public List<StockInfo> findAll();
//
//    public List<Data> findByCode(String code);
//
//    public StockInfo findInfoByCode(String code);
//
//    public List<StockInfo> searchStock(HashMap<String,Object> map);

}
