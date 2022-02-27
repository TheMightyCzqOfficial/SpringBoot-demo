package springbootdemo01.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

public interface StockDataService {
    public IPage findByCode(int currentPage, int pageSize,String Code);
    public void getKline(String code);
    public String getDataByCode(String code);
}
