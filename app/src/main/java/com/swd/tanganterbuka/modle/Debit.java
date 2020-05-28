package com.swd.tanganterbuka.modle;

public class Debit {
    private  String icon;
    private  String name;
    private  String num;
    private  Double money;
    private  String interest;
    private  String time;

    public Debit(){}

    public Debit(String icon,
                 String name,
                 String num,
                 Double money,
                 String interest,
                 String time){
        this.icon=icon;
        this.name=name;
        this.num=num;
        this.money=money;
        this.interest=interest;
        this.time=time;
    }

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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
