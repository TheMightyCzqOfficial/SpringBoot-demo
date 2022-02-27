package springbootdemo01.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("userstock")

public class UserStock {
    @Override
    public String toString() {
        return "UserStock{" +
                "username='" + username + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String username;
    private String code;
}
