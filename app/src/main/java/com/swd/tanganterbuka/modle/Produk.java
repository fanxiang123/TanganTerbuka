package com.swd.tanganterbuka.modle;

public class Produk {
    private  String icon;
    private  String name;
    private  String num;
    private  Double minMoney;
    private  Double maxMoney;

    public Produk(){}



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }
    public Produk(String icon,
                  String name,
                  String num,
                  double minMoney,
                  double maxMoney){
        this.icon=icon;
        this.name=name;
        this.num=num;
        this.minMoney=minMoney;
        this.maxMoney=maxMoney;
    }



}
