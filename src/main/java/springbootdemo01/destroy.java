package springbootdemo01;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbootdemo01.dao.StockDataMapper;

import javax.annotation.PreDestroy;

@Component
public class destroy {
    @Autowired
    StockDataMapper stockDataMapper;
        @PreDestroy
        public void destroy() throws Exception {
            //你的代码
         stockDataMapper.delete();
        }
    }

