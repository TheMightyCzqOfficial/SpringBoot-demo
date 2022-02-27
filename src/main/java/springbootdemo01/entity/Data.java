package springbootdemo01.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;
@TableName("stockdata")
public class Data {
    private int id;
    private String code;
    private Date tradeDate;
    private String open;
    private String high;
    private String low;
    private String close;
    private String preClose;
    private String changeAmount;
    private String pctChg;
    private String vol;
    private String amount;

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", tradeDate=" + tradeDate +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", preClose='" + preClose + '\'' +
                ", changeAmount='" + changeAmount + '\'' +
                ", pctChg='" + pctChg + '\'' +
                ", vol='" + vol + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getPctChg() {
        return pctChg;
    }

    public void setPctChg(String pctChg) {
        this.pctChg = pctChg;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
