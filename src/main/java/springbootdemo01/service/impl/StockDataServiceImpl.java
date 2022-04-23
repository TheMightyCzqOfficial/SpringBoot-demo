package springbootdemo01.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo01.dao.StockDataMapper;
import springbootdemo01.dao.StockInfoMapper;
import springbootdemo01.entity.Price;
import springbootdemo01.service.StockDataService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockDataServiceImpl implements StockDataService {
    public static Properties properties=new Properties();
    public static String pythonPath=null;
    public static String pyPath=null;
    static {
        try {
            properties.load(new FileReader("src/main/resources/PythonConfig.properties"));
            pythonPath = properties.getProperty("python");
            pyPath = properties.getProperty("pyPath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\get_kline.py",
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
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\main.py",
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
        return "OK";
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
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\downloadToCsv.py",
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
    public List<String> getDP(){
        List<String> dpList=new ArrayList<>();
        Process proc;
        try {
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\getDP.py"
            };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

            String line=null;
            while ((line = in.readLine()) != null) {
                dpList.add(line);

            }
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

    @Override
    public List<String> getNews(String name) {
        int i=0;
        List<String> newList=new ArrayList<>();

        Process proc;
        try {
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\get_news.py",name
            };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

            String line=null;
            while ((line = in.readLine()) != null) {

                    newList.add(line);


            }
            System.out.println(newList);
            in.close();
            proc.waitFor();
//            System.out.println("保存成功！ ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newList;
    }

    @Override
    public Price getPrice(String name) {
        System.out.println("------------------"+name);
        List<String> list=new ArrayList<>();
        Process proc;
        try {
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\get_price.py",name
            };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

            String line=null;
            while ((line = in.readLine()) != null) {
                list.add(line);

            }
            in.close();
            proc.waitFor();
//            System.out.println("保存成功！ ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Price price=new Price(list.get(0),list.get(1)+"元",list.get(2));

        return price;
    }

    @Override
    public List<String> getTop() {
        List<String> list=new ArrayList<>();
        Process proc;
        try {
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\get_top.py"
            };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

            String line=null;
            while ((line = in.readLine()) != null) {

                list.add(line);
            }
            in.close();
            proc.waitFor();
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String> getDetail(String code) {

        List<String> list=new ArrayList<>();
        Process proc;
        try {
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\get_detail.py",code
            };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

            String line=null;
            while ((line = in.readLine()) != null) {

                list.add(line);
            }
            in.close();
            proc.waitFor();
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String> LSTM(String code) {
        List<String> list=new ArrayList<>();
        Process proc;
        try {
            String[] args1 = new String[] { pythonPath,
                    pyPath+"\\LSTM.py",code
            };//python.exe处为你系统中python的安装位置；python.py为想要执行的python文件位置；test为想要传的参数
            //proc = Runtime.getRuntime().exec("python.exe python.py ");// 执行py文件 不传参数
            proc=Runtime.getRuntime().exec(args1);

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));

            String line=null;
            while ((line = in.readLine()) != null) {

                list.add(line);
            }
            in.close();
            proc.waitFor();
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}

