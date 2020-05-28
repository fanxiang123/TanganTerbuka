package com.swd.tanganterbuka.modle;

public class AppModle {
    //产品id
    private int id;
    //appid 目前无用
    private String appid;
    //产品名称
    private String productName;
    //利率
    private String interestRate;
    //利率单位：天days, 月month
    private String interestRateUnit;
    //放款时间（单位天）
    private String loanDay;
    //放款最小金额
    private String priceMin;
    //放款最大金额
    private String priceMax;
    //放款单位金额
    private String priceUnit;

    private String icon;
    //综合评分
    private String totalScore;
    //一句话说明
    private String review;
    //产品包名
    private String packageName;
    //api状态
    private String api_status;
    //cap，导量上限
    private String capLimit;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setAppid(String appid){
        this.appid = appid;
    }
    public String getAppid(){
        return this.appid;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public String getProductName(){
        return this.productName;
    }
    public void setInterestRate(String interestRate){
        this.interestRate = interestRate;
    }
    public String getInterestRate(){
        return this.interestRate;
    }
    public void setInterestRateUnit(String interestRateUnit){
        this.interestRateUnit = interestRateUnit;
    }
    public String getInterestRateUnit(){
        return this.interestRateUnit;
    }
    public void setLoanDay(String loanDay){
        this.loanDay = loanDay;
    }
    public String getLoanDay(){
        return this.loanDay;
    }
    public void setPriceMin(String priceMin){
        this.priceMin = priceMin;
    }
    public String getPriceMin(){
        return this.priceMin;
    }
    public void setPriceMax(String priceMax){
        this.priceMax = priceMax;
    }
    public String getPriceMax(){
        return this.priceMax;
    }
    public void setPriceUnit(String priceUnit){
        this.priceUnit = priceUnit;
    }
    public String getPriceUnit(){
        return this.priceUnit;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setTotalScore(String totalScore){
        this.totalScore = totalScore;
    }
    public String getTotalScore(){
        return this.totalScore;
    }
    public void setReview(String review){
        this.review = review;
    }
    public String getReview(){
        return this.review;
    }
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }
    public String getPackageName(){
        return this.packageName;
    }
    public void setApi_status(String api_status){
        this.api_status = api_status;
    }
    public String getApi_status(){
        return this.api_status;
    }
    public void setCapLimit(String capLimit){
        this.capLimit = capLimit;
    }
    public String getCapLimit(){
        return this.capLimit;
    }
}
