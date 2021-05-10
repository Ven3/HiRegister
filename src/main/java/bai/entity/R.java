package bai.entity;

public class R {
    private Integer state;      //状态
    private String message;     //信息
    private Object data;        //数据

    public R() {

    }

    public R(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public R(Integer state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public R(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
