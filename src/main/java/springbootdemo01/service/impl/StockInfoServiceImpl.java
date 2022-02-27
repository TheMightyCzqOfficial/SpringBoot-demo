package springbootdemo01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo01.dao.StockInfoMapper;
import springbootdemo01.dao.UserStockMapper;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.UserStock;
import springbootdemo01.service.StockInfoService;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
@Service
public class StockInfoServiceImpl  implements StockInfoService {
    @Autowired
    StockInfoMapper stockInfoMapper;
    @Autowired
    UserStockMapper userStockMapper;
    @Override
    public List<StockInfo> findAll() {
        return stockInfoMapper.selectList(null);
    }

    @Override
    public String add(String name, String code) {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("code",code);
        qw.eq("username",name);
        List list = userStockMapper.selectList(qw);
        if (list.isEmpty()){
            UserStock userStock=new UserStock();
            userStock.setUsername(name);
            userStock.setCode(code);
            userStockMapper.insert(userStock);
            return "success";
        }else {
            return "exist";
        }

    }

    @Override
    public void delete(String name, String code) {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("username",name);
        qw.eq("code",code);
        userStockMapper.delete(qw);
    }

    @Override
    public List<StockInfo> findById(List<UserStock> idList) {
        List<StockInfo> list=new ArrayList<>();
        for (int i=0 ;i<idList.size();i++) {
            QueryWrapper qw1=new QueryWrapper();
            String code = idList.get(i).getCode();
            qw1.eq("code",code);
            List<StockInfo> selectList = stockInfoMapper.selectList(qw1);
//            System.out.println(selectList);
//            System.out.println(list.size());
            list.add(selectList.get(0));

        }
        System.out.println(list);

        return list;
    }

    @Override
    public IPage findByPage(int currentPage, int pageSize) {
        IPage page=new Page(currentPage,pageSize);
        IPage selectPage = stockInfoMapper.selectPage(page, null);
        return selectPage;
    }

    @Override
    public IPage searchStock(int currentPage, int pageSize,String name,String code) {
        IPage page=new Page(currentPage,pageSize);
        QueryWrapper qw=new QueryWrapper();
        if(code.length()>6){
            qw.like("code",code);
        }
        else if(!code.equals("-1")){
            qw.like("code",code);
        }else if(name!=null){
            qw.like("name",name);
        }else {
            return findByPage(currentPage, pageSize);

        }
        return stockInfoMapper.selectPage(page,qw);
    }
}
