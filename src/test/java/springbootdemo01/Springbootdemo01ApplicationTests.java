package springbootdemo01;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class Springbootdemo01ApplicationTests {

    @Test
    void contextLoads() {
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
//        calendar.add(Calendar.MONTH, -1);//当前时间减去一个月，即一个月前的时间
        calendar.add(Calendar.MONTH,-1); //当前时间减去一天，即一天前的时间
        calendar.getTimeInMillis();//返回当前时间的毫秒数
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String date1 = fmt.format(calendar.getTime());
        System.out.println(date1);
    }
    @Test
    void desktop(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File path=fsv.getHomeDirectory();
        path.mkdir();
        System.out.println(path.toString());
    }

}
