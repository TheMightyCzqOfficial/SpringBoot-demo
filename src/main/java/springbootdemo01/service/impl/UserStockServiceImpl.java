package springbootdemo01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo01.dao.UserStockMapper;
import springbootdemo01.entity.UserStock;
import springbootdemo01.service.UserStockService;

import java.util.List;
@Service
public class UserStockServiceImpl implements UserStockService {
    @Autowired
    UserStockMapper stockMapper;
    @Override
    public List findByUsername(String username) {
        QueryWrapper qw=new QueryWrapper();
        qw.select("code").eq("username",username);
        List<UserStock> list = stockMapper.selectList(qw);
        return list;
    }

    @Override
    public int countByUsername(String username) {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("username",username);
        Integer count = stockMapper.selectCount(qw);
        System.out.println(count);
        return count;
    }
}
