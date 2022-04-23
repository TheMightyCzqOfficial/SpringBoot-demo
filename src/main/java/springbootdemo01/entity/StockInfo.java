package springbootdemo01.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("stockinfo")
public class StockInfo {

    private String code;
    private String name;
    private String area;
    private String type;
    private String pchange;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPchange() {
        return pchange;
    }

    public void setPchange(String pchange) {
        this.pchange = pchange;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", type='" + type + '\'' +
                ", pchange='" + pchange + '\'' +
                '}';
    }
}
