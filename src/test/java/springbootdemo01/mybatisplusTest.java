package springbootdemo01;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springbootdemo01.dao.StockDataMapper;
import springbootdemo01.dao.StockInfoMapper;
import springbootdemo01.dao.UserMapper;
import springbootdemo01.dao.UserStockMapper;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.User;
import springbootdemo01.entity.UserStock;
import springbootdemo01.service.StockInfoService;
import springbootdemo01.service.impl.StockInfoServiceImpl;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class mybatisplusTest {
    @Resource
    UserStockMapper stockMapper;
    @Resource
    StockInfoMapper stockInfoMapper;

    @Resource
    StockDataMapper stockDataMapper;

    @Resource
    UserMapper userMapper;
    @Test
    void test(){
        List<StockInfo> stockInfoList=new ArrayList<>();
        QueryWrapper qw=new QueryWrapper();
        qw.select("code").eq("username","czq");
        List<UserStock> list = stockMapper.selectList(qw);
        System.out.println(list.size());
        for (int i=0 ;i<list.size();i++) {
            QueryWrapper qw1=new QueryWrapper();
            String code = list.get(i).getCode();
            qw1.eq("code",code);
            List<StockInfo> selectList = stockInfoMapper.selectList(qw1);
            System.out.println(selectList);
            System.out.println(list.size());
            stockInfoList.add(selectList.get(0));


        }
        System.out.println(stockInfoList);
        }

    @Test
    void page(){
        IPage page=new Page(1,5);
        IPage iPage = stockInfoMapper.selectPage(page, null);
        List records = iPage.getRecords();
        System.out.println(records);

    }
    @Test
    void add(){
        User user=new User();
        user.setUsername("username");
        user.setPassword("pwd");
        userMapper.insert(user);
    }
    @Test
    public List<String> getDP(){
            List<String> dpList=new ArrayList<>();
            Process proc;
            try {
                String[] args1 = new String[] { "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\venv\\Scripts\\python.exe",
                        "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\7\\stockDemo1\\getDP.py"
                        };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
                //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
                proc=Runtime.getRuntime().exec(args1);

                //用输入输出流来截取结果
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

                String line=null;
                while ((line = in.readLine()) != null) {
                    dpList.add(line);

                }
                dpList.remove(2);
                System.out.println(dpList);
                in.close();
                proc.waitFor();
//            System.out.println("保存成功！ ");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return dpList;
        }
    }


