package com.swd.tanganterbuka.modle;

public class AppDetailsModle {
    //产品id
    private String pid;
    //产品名称
    private String product_name;
    //最小借款金额
    private String price_min;
    //最大借款金额
    private String price_max;
    //产品icon
    private String icon;
    //产品跳转链接
    private String jump_url;
    //通过率评分
    private String pass_rate_score;
    //放款速度评分
    private String speed_score;
    //催收评分
    private String dunning_score;
    //综合评分
    private String total_score;
    //产品包名
    private String package_name;
    //是否是apk产品，如果是apk产品，使用jump_url直接唤起手机内的浏览器打开下载
    private String apk_status;
    //借款额外描述
    private String loan_description;
    //借款利率参数集合
    private InterestTemplate interest_template;

    public void setPid(String pid){
        this.pid = pid;
    }
    public String getPid(){
        return this.pid;
    }
    public void setProduct_name(String product_name){
        this.product_name = product_name;
    }
    public String getProduct_name(){
        return this.product_name;
    }
    public void setPrice_min(String price_min){
        this.price_min = price_min;
    }
    public String getPrice_min(){
        return this.price_min;
    }
    public void setPrice_max(String price_max){
        this.price_max = price_max;
    }
    public String getPrice_max(){
        return this.price_max;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setJump_url(String jump_url){
        this.jump_url = jump_url;
    }
    public String getJump_url(){
        return this.jump_url;
    }
    public void setPass_rate_score(String pass_rate_score){
        this.pass_rate_score = pass_rate_score;
    }
    public String getPass_rate_score(){
        return this.pass_rate_score;
    }
    public void setSpeed_score(String speed_score){
        this.speed_score = speed_score;
    }
    public String getSpeed_score(){
        return this.speed_score;
    }
    public void setDunning_score(String dunning_score){
        this.dunning_score = dunning_score;
    }
    public String getDunning_score(){
        return this.dunning_score;
    }
    public void setTotal_score(String total_score){
        this.total_score = total_score;
    }
    public String getTotal_score(){
        return this.total_score;
    }
    public void setPackage_name(String package_name){
        this.package_name = package_name;
    }
    public String getPackage_name(){
        return this.package_name;
    }
    public void setApk_status(String apk_status){
        this.apk_status = apk_status;
    }
    public String getApk_status(){
        return this.apk_status;
    }
    public void setLoan_description(String loan_description){
        this.loan_description = loan_description;
    }
    public String getLoan_description(){
        return this.loan_description;
    }
    public void setInterest_template(InterestTemplate interest_template){
        this.interest_template = interest_template;
    }
    public InterestTemplate getInterest_template(){
        return this.interest_template;
    }
}
