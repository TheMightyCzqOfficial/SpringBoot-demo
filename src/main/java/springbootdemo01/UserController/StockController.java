package springbootdemo01.UserController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import springbootdemo01.Springbootdemo01Application;
import springbootdemo01.domain.MyDataSource;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.service.StockDataService;
import springbootdemo01.service.StockInfoService;
import springbootdemo01.utils.R;

import java.io.*;
import java.util.List;

@RestController//@ResponseBody+@Controller
@RequestMapping(value = "/stock")
public class StockController {
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
//    @GetMapping("/data/kline/{name}")//RequestMapping+method = RequestMethod.GET
//    public R readKline(@PathVariable String name) {
//        try {
//            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\CZQ\\IdeaProjects\\springbootdemo01\\src\\main\\resources\\static\\pages\\KLine\\"+name+"K线.html"));
//            BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\CZQ\\IdeaProjects\\springbootdemo01\\src\\main\\resources\\static\\pages\\KLine\\"+name+"1K线.html"));
//            String line = null;
//            StringBuilder sb=new StringBuilder();
//            while ((line=br.readLine())!=null){
//                bw.write(line);
//                bw.newLine();
//                bw.flush();
////                sb.append(line);
//            }System.out.println("OK");
//            bw.close();
//            br.close();
//            R r=new R(true,sb.toString());
//            return r;
//        } catch (Exception e) {
//            R r=new R(true,"file do not exist!");
//            return r;
//        }
//
//    }

}
