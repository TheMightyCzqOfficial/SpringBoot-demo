package springbootdemo01.service;

import java.util.List;

public interface UserStockService {
    public List findByUsername(String username);
    public int countByUsername(String username);
}
