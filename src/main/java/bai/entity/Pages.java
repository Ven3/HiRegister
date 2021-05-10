package bai.entity;

public class Pages {
    private Integer c;//当前页
    private Integer t;//数据总量
    private Integer n;//每页数据
    private Object data;//数据

    public Pages(Integer c, Integer n) {
        this.c = c;
        this.n = n;
    }

    public Pages(Integer c, Integer t, Integer n, Object data) {
        this.c = c;
        this.t = t;
        this.n = n;
        this.data = data;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
