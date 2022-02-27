package springbootdemo01.service;

import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll(String username,String pwd);
    String add(String username,String pwd);
}
