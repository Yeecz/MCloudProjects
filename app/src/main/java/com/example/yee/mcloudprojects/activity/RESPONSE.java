package com.example.yee.mcloudprojects.activity;

public enum RESPONSE {

    ADD_MESSAGE("添加消息",1),
    SELECT_MESSAGE("查询消息",2),
    NEW_FRIEND("好友请求",3),
    OK_FRIEND("同意好友请求",4),
    SELECT_DATA("查資料",5);
    ;
    private String name;
    private int flag;

    RESPONSE(String name,int flag){
        this.name=name;
        this.flag=flag;
    }

    public static RESPONSE queryFlag(int flag){
        for(RESPONSE p:RESPONSE.values()){
            if(p.flag==flag){
                return p;
            }
        }
        return null;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
