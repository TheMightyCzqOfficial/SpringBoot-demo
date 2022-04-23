package springbootdemo01.UserController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import springbootdemo01.dao.StockInfoMapper;
import springbootdemo01.entity.News;
import springbootdemo01.entity.Price;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.Top;
import springbootdemo01.service.StockDataService;
import springbootdemo01.service.StockInfoService;
import springbootdemo01.utils.R;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController//@ResponseBody+@Controller
@RequestMapping(value = "/stock")
public class StockController {
    @Autowired
    StockInfoMapper stockInfoMapper;
    @Autowired
    StockInfoService service;
    @Autowired
    StockDataService dataService;
    @GetMapping("/list/{currentPage}/{pageSize}")//RequestMapping+method = RequestMethod.GET
    public R getStockList(@PathVariable Integer currentPage, @PathVariable Integer pageSize ){

//        List<StockInfo> all = service.findAll();
        System.out.println("get!");
        System.out.println(currentPage+"..."+pageSize);
        R r=new R(true,service.findByPage(currentPage, pageSize));
        return r;
    }
    @GetMapping("/search/{currentPage}/{pageSize}/{condition}")//RequestMapping+method = RequestMethod.GET
    public R searchStock(@PathVariable Integer currentPage, @PathVariable Integer pageSize,@PathVariable String condition ){
        int code=-1;
        String con=null;
        if(condition.length()<=6){
            try {
                code = Integer.parseInt(condition);
            } catch (Exception e) {
                con=condition;
            } finally {
                String code1=String.valueOf(code);
                R r=new R(true,service.searchStock(currentPage, pageSize,con,code1));
                return r;
            }
        }

//        List<StockInfo> all = service.findAll();

        R r=new R(true,service.searchStock(currentPage, pageSize,String.valueOf(code),condition));
        return r;
    }
    @GetMapping("/data/{currentPage}/{pageSize}/{code}")//RequestMapping+method = RequestMethod.GET
    public R getDataById(@PathVariable Integer currentPage, @PathVariable Integer pageSize ,@PathVariable String code){

//        List<StockInfo> all = service.findAll();

        R r=new R(true,dataService.findByCode(currentPage, pageSize,code));
        return r;
    }
    @GetMapping("/kline/{code}")//RequestMapping+method = RequestMethod.GET
    public R getKline(@PathVariable String code) throws IOException {

//        List<StockInfo> all = service.findAll();
        dataService.getKline(code);
//        FileOutputStream f=new FileOutputStream("C:\\Users\\CZQ\\IdeaProjects\\springbootdemo01\\src\\main\\resources\\PythonConfig.properties",true);//true:追加写入
//        f.write("黄显好Thao".getBytes() );
//        f.close();
        R r=new R(true,"done!");
        return r;
    }
    @GetMapping("/data/{code}/{startDate}")//RequestMapping+method = RequestMethod.GET
    public R updateData(@PathVariable String code,@PathVariable String startDate){
        dataService.getDataByCode(code,startDate);
//        List<StockInfo> all = service.findAll();

        R r=new R(true,"done!");
        return r;
    }
    @GetMapping("/data/download/{code}")//RequestMapping+method = RequestMethod.GET
    public R download(@PathVariable String code){
        dataService.saveData(code);
//        List<StockInfo> all = service.findAll();

        R r=new R(true,"done!");
        return r;
    }
    @GetMapping("/dp")//RequestMapping+method = RequestMethod.GET
    public R getDP() throws IOException {
        JSONObject json=new JSONObject();
        List<String> dpList = dataService.getDP();
        String s = dpList.get(1);
        String[] s1 = s.split(" ");
        double i =  Double.parseDouble(s1[0]);
        if (i>=0){
            json.put("color",1);
        }else{
            json.put("color",0);
        }
        json.put("dp",dpList.get(0));
        json.put("change",dpList.get(1));
        R r=new R(true,json);
        return r;
    }
    @GetMapping("/news/{name}")//RequestMapping+method = RequestMethod.GET
    public R getNews(@PathVariable String name) throws IOException {
        JSONObject json=new JSONObject();

        List<String> newList = dataService.getNews(name);
        List<News> newsList = new ArrayList<>();
        for (int i=0;i<newList.size();i+=2){
            News news=new News();
            news.setTitle(newList.get(i));
            news.setLink(newList.get(i+1));
            newsList.add(news);
        }
        json.put("newList",newsList);
        R r=new R(true,json);
        return r;
    }
    @GetMapping("/top")//RequestMapping+method = RequestMethod.GET
    public R getTop() throws ParseException {
        List<Top> topList=new ArrayList<>();
        List<String> list = dataService.getTop();
        System.out.println(list.size());
        for(int i=0;i<list.size();i+=5){
            Top top= new Top(list.get(i), list.get(i + 1), list.get(i + 2), list.get(i + 3), list.get(i + 4));
            topList.add(top);
        }
        Date date=null;
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("topList",topList);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        date = dateFormat.parse(topList.get(0).getDate());
        String format = sdf.format(date);
        jsonObject.put("date",format);
//        System.out.println(topList);
//        JSONObject json = JSONObject.parseObject(top);
        R r=new R(true,jsonObject);
        return r;
    }
    @GetMapping("/price/{name}")//RequestMapping+method = RequestMethod.GET
    public R getPrice(@PathVariable String name) throws ParseException {
        JSONObject jsonObject=new JSONObject();
        Price price = dataService.getPrice(name);
        String change = price.getChange();
        String[] s = change.split(" ");
        String s1 = s[0];
        double parseDouble = Double.parseDouble(s1);
        if (parseDouble>=0){
            jsonObject.put("color","red");
        }else {
            jsonObject.put("color","green");
        }

        jsonObject.put("time",price.getTime());
        jsonObject.put("price",price.getPrice());
        jsonObject.put("change",price.getChange());

//        System.out.println(topList);
//        JSONObject json = JSONObject.parseObject(top);
        R r=new R(true,jsonObject);
        return r;
    }
    @GetMapping("/fav/{name}/{code}")
    public R getAllByNameAndCode(@PathVariable String name,@PathVariable String code) throws ParseException {
        final JSONObject jsonObject=new JSONObject();
        ExecutorService es= Executors.newFixedThreadPool(4);
        es.submit(new Runnable() {
            @Override
            public void run() {
                Price price = dataService.getPrice(name);
                String change = price.getChange();
                String[] s = change.split(" ");
                String s1 = s[0];
                double parseDouble = Double.parseDouble(s1);
                if (parseDouble>=0){
                    jsonObject.put("color","red");
                }else {
                    jsonObject.put("color","green");
                }

                jsonObject.put("time",price.getTime());
                jsonObject.put("price",price.getPrice());
                jsonObject.put("change",price.getChange());
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                List<String> newList = dataService.getNews(name);
                List<News> newsList = new ArrayList<>();
                for (int i=0;i<newList.size();i+=2){
                    News news=new News();
                    news.setTitle(newList.get(i));
                    news.setLink(newList.get(i+1));
                    newsList.add(news);
                }
                jsonObject.put("newList",newsList);
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                dataService.getKline(code);
                jsonObject.put("kline","done");
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                String startDate = dataService.getDataByCode(code, "startDate");

                IPage byCode = dataService.findByCode(1, 6, code);
                jsonObject.put("Ipage",byCode);
            }
        });
        es.shutdown();
        while (true){
            if (es.isTerminated()){
                return new R(true,jsonObject);
            }
        }


    }
    @GetMapping("/getLowAndHigh")
    public R getStockByChange(){
        List<StockInfo> high = stockInfoMapper.getHigh();
        List<StockInfo> low = stockInfoMapper.getLow();
        List<StockInfo> morenka = stockInfoMapper.getMorenka();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("morenka",morenka);
        jsonObject.put("high",high);
        jsonObject.put("low",low);
        R r=new R(true,jsonObject);
        return r;
    }
    @GetMapping("/detail/{code}")
    public R getDetail(@PathVariable String code){
        JSONObject jsonObject=new JSONObject();
        List<String> detail = dataService.getDetail(code);
        String s1 = detail.get(0);
        jsonObject.put("detail",s1);
        String s2 = detail.get(1);
        jsonObject.put("suggestion",s2);
        return new R(true,jsonObject);
    }
    @GetMapping("/predict/{code}")
    public R predict(@PathVariable String code){
        JSONObject jsonObject=new JSONObject();
        List<String> detail = dataService.LSTM(code);
        ArrayList<String> strList=null;
        try {
            ArrayList<String> arrList = new ArrayList<>();
            strList = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new FileReader("D:\\kline\\predict.csv"));
            String line=null;
            while ((line = reader.readLine()) != null) {
//                System.out.println(Arrays.asList(reader.getValues()));
                arrList.add(line); // 按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
            System.out.println("读取的行数：" + arrList.size());
            System.out.println(arrList);
            Double price =Double.parseDouble(String.valueOf(arrList.get(arrList.size() - 1))) ;
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            jsonObject.put("price",df.format(price));
            Double prePrice =Double.parseDouble(String.valueOf(arrList.get(arrList.size() - 1))) ;
            if (prePrice-price>0){
                jsonObject.put("zoushi","下跌");
            }else {
                jsonObject.put("zoushi","上涨");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s1 = detail.get(0);
        jsonObject.put("rmse",s1);
        String s2 = detail.get(1);
        jsonObject.put("suggestion",s2);
        return new R(true,jsonObject);
    }
}
