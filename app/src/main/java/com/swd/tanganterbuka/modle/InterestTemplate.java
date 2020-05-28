package com.swd.tanganterbuka.modle;

public class InterestTemplate {
    //借款金额
    private String loan_amount;
    //应还款总金额
    private String repay_amount;
    //利息
    private String interest_amount;
    //管理费
    private String admin_amount;
    //利息+管理费金额
    private String interest_admin;
    //借款最小周期
    private String cycle;
    //借款最大周期
    private String max_cycle;

    public void setLoan_amount(String loan_amount){
        this.loan_amount = loan_amount;
    }
    public String getLoan_amount(){
        return this.loan_amount;
    }
    public void setRepay_amount(String repay_amount){
        this.repay_amount = repay_amount;
    }
    public String getRepay_amount(){
        return this.repay_amount;
    }
    public void setInterest_amount(String interest_amount){
        this.interest_amount = interest_amount;
    }
    public String getInterest_amount(){
        return this.interest_amount;
    }
    public void setAdmin_amount(String admin_amount){
        this.admin_amount = admin_amount;
    }
    public String getAdmin_amount(){
        return this.admin_amount;
    }
    public void setInterest_admin(String interest_admin){
        this.interest_admin = interest_admin;
    }
    public String getInterest_admin(){
        return this.interest_admin;
    }
    public void setCycle(String cycle){
        this.cycle = cycle;
    }
    public String getCycle(){
        return this.cycle;
    }
    public void setMax_cycle(String max_cycle){
        this.max_cycle = max_cycle;
    }
    public String getMax_cycle(){
        return this.max_cycle;
    }
}
