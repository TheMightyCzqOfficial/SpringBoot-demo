package springbootdemo01.entity;

public class Top {
    private String code;
    private String name;
    private String date;
    private String value;
    private String percent;

    @Override
    public String toString() {
        return "Top{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                ", percent='" + percent + '\'' +
                '}';
    }

    public Top(String code, String name, String date, String value, String percent) {
        this.code = code;
        this.name = name;
        this.date = date;
        this.value = value;
        this.percent = percent;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
