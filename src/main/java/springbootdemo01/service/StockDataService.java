package springbootdemo01.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import springbootdemo01.entity.Price;

import java.util.List;

public interface StockDataService {
    public IPage findByCode(int currentPage, int pageSize,String Code);
    public void getKline(String code);
    public String getDataByCode(String code,String startDate);
    public void saveData(String code);

    List<String> getDP();
    List<String> getNews(String name);
    Price getPrice(String name);
    List<String> getTop();
    List<String> getDetail(String code);
    List<String> LSTM(String code);
}
