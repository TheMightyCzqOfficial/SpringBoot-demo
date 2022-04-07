package springbootdemo01.entity;

public class Price {
    private String time;
    private String price;
    private String change;

    public Price(String time, String price, String change) {
        this.time = time;

        this.price = price;
        this.change = change;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
