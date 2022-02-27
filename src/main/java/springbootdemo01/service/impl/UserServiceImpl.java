package springbootdemo01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo01.dao.UserMapper;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.User;
import springbootdemo01.entity.UserStock;
import springbootdemo01.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findAll(String username,String pwd) {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("username",username);
        qw.eq("password",pwd);
        List list = userMapper.selectList(qw);
        System.out.println(list);
        return userMapper.selectList(qw);
    }

    @Override
    public String add(String username, String pwd) {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("username",username);
        List list = userMapper.selectList(qw);
        if (list.isEmpty()){
            User user=new User();
            user.setUsername(username);
            user.setPassword(pwd);
            userMapper.insert(user);
            return "success";
        }else {
            return "exist";
        }

    }
}
