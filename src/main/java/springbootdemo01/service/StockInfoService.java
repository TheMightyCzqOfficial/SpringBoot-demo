package springbootdemo01.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.UserStock;

import java.util.List;

public interface StockInfoService  {
    List<StockInfo> findAll();
    String add(String name,String code);
    void delete(String name,String code);
    List<StockInfo> findById(List<UserStock> idList);
    IPage findByPage(int currentPage, int pageSize);
    IPage searchStock(int currentPage, int pageSize,String con,String code);

}
