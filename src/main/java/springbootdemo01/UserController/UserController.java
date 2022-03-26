package springbootdemo01.UserController;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springbootdemo01.entity.StockInfo;
import springbootdemo01.entity.User;
import springbootdemo01.entity.UserStock;
import springbootdemo01.service.StockInfoService;
import springbootdemo01.service.UserService;
import springbootdemo01.service.UserStockService;
import springbootdemo01.utils.R;
import springbootdemo01.utils.VerificationCode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService service;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    StockInfoService infoService;
    @Autowired
    UserStockService stockService;



    /**
     * 用于生成带四位数字验证码的图片
     */
    @GetMapping(value = "/checkCode/{status}/{date}")
    public String imageCode(@PathVariable String status) throws Exception {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String simpleCaptcha =null;
        OutputStream os = response.getOutputStream();
        //返回验证码和图片的map
        Map<String,Object> map = VerificationCode.getImageCode(86, 37, os);
        if(status.equals("login")){
            simpleCaptcha = "checkCode";
        }else {
            simpleCaptcha = "checkCode1";
        }

        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());

        request.getSession().setAttribute("codeTime",new Date().getTime());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "jpg", os);
        } catch (IOException e) {
            return "";
        }   finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
        return null;
    }

    @PostMapping(value = "/login/{username}/{password}/{code}")
    public R login(@PathVariable String username,@PathVariable String password,@PathVariable String code){

        // 获得验证码对象
        Object cko = request.getSession().getAttribute("checkCode");
//        System.out.println(cko.toString()+"...");
        if (code.equals("")) {
            R r=new R(false,"code");
            return r;
        }
        String captcha = cko.toString();
        // 判断验证码输入是否正确
        if ( !(code.equalsIgnoreCase(captcha))) {
            R r=new R(false,"code");
            return r;

        }
        else {
            List<User> userList = service.findAll(username, password);
            if (!userList.isEmpty()){
                request.getSession().setAttribute("username",username);
                R r=new R(true,username);
                return r;

            }
            else {
                R r=new R(false,"wrong");
                return r;
            }

        }
    }
    @GetMapping(value = "/success")
    public String success() throws IOException {

        String username = null;
        try {
            username = request.getSession().getAttribute("username").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(username+"............................");
        return username;

    }
    @GetMapping(value = "/myFav/{username}")
    public R fav(@PathVariable String username)  {
        List<UserStock> byUsername = stockService.findByUsername(username);
        List<StockInfo> byId = infoService.findById(byUsername);
        int i = stockService.countByUsername(username);
        JSONObject object = new JSONObject();
        object.put("favList",byId);
        object.put("count",i);
        R r=new R(true,object);
        return r;

    }
    @PostMapping(value = "/register/{username}/{password}/{code}")
    public R register(@PathVariable String username,@PathVariable String password,@PathVariable String code)  {
        Object cko = request.getSession().getAttribute("checkCode1");
//        System.out.println(cko.toString()+"...");
        if (code.equals("")) {
            R r=new R(false,"code");
            return r;
        }
        String captcha = cko.toString();
        // 判断验证码输入是否正确
        if ( !(code.equalsIgnoreCase(captcha))) {
            R r=new R(false,"code");
            return r;

        }
        else {

            String result = service.add(username, password);
            if (result.equals("success")){
                R r=new R(true,"注册成功");
                return r;
            }else {
                R r=new R(true,"用户名已存在");
                return r;
            }

        }


    }
    @GetMapping(value = "/addFav/{username}/{code}")
    public R addFav(@PathVariable String username,@PathVariable String code)  {
        String add = infoService.add(username, code);
        if(add.equals("exist")){
            R r=new R(false,"已存在，请到收藏列表查看");
            return r;
        }else {
            R r=new R(true,"收藏成功");
            return r;
        }


    }
    @GetMapping(value = "/delFav/{username}/{code}")
    public R delFav(@PathVariable String username,@PathVariable String code)  {
        infoService.delete(username,code);
        R r=new R(false,"取消收藏成功！");
        return r;
    }


}
