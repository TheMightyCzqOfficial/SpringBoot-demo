package springbootdemo01.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo01.dao.StockDataMapper;
import springbootdemo01.dao.StockInfoMapper;
import springbootdemo01.service.StockDataService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class StockDataServiceImpl implements StockDataService {

    @Autowired
    StockDataMapper stockDataMapper;
    @Override
    public IPage findByCode(int currentPage, int pageSize, String code) {
        IPage page=new Page(currentPage,pageSize);
        QueryWrapper qw=new QueryWrapper();
        qw.eq("code",code);
        qw.orderByDesc("trade_date");
        IPage selectPage = stockDataMapper.selectPage(page, qw);
        System.out.println(selectPage.getRecords());
        return selectPage;
    }
    @Override
    public void getKline(String code) {
        Process proc;
        try {
            String[] args1 = new String[] { "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\venv\\Scripts\\python.exe",
                    "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\7\\stockDemo1\\get_kline.py",
                    code};//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line=null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
//            System.out.println("保存成功！ ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDataByCode(String code,String startDate) {

        Date date=new Date();
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        if ("half".equals(startDate)){
            calendar.add(Calendar.MONTH,-6); //月份-6
        }else if ("one".equals(startDate)){
            calendar.add(Calendar.YEAR,-1);
        }else if ("five".equals(startDate)){
            calendar.add(Calendar.YEAR,-5);
        }else if ("ten".equals(startDate)){
            calendar.add(Calendar.YEAR,-10);
        }else {
            calendar.add(Calendar.DAY_OF_MONTH,-15);
        }
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
//        calendar.add(Calendar.MONTH, -1);//当前时间减去一个月，即一个月前的时间

        calendar.getTimeInMillis();//返回当前时间的毫秒数
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String start = fmt.format(calendar.getTime());
        //替换字符
        String today = new SimpleDateFormat("yyyyMMdd").format(date);
        System.out.println("最新日期："+today);
        System.out.println("开始执行python ");
        Process proc;
        try {
            String[] args1 = new String[] { "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\venv\\Scripts\\python.exe",
                    "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\7\\stockDemo1\\main.py",
                    code,start,today};//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
            System.out.println("添加完毕");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "查询成功！";
    }

    @Override
    public void saveData(String code) {
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
        calendar.add(Calendar.YEAR,-40);
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
//        calendar.add(Calendar.MONTH, -1);//当前时间减去一个月，即一个月前的时间
        calendar.getTimeInMillis();//返回当前时间的毫秒数
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String start = fmt.format(calendar.getTime());
        //替换字符
        System.out.println("开始执行python ");
        Process proc;
        try {
            String[] args1 = new String[] { "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\venv\\Scripts\\python.exe",
                    "C:\\Users\\CZQ\\PycharmProjects\\PythonDemo\\7\\stockDemo1\\downloadToCsv.py",
                    code,start};//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
            System.out.println("添加完毕");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
