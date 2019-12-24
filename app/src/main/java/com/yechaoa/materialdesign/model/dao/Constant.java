package com.yechaoa.materialdesign.model.dao;

public class Constant {
    private final static String BASE_URL = "http://120.27.244.230:8000/CRUD/";
    public final static String LISTUSERBYNAME = BASE_URL + "ListUserByname";
    public final static String ADDUSER = BASE_URL+"insertUser";
    public final static String LISTBAGBYNAME = BASE_URL + "ListBagByname";
    public final static String INSERTBOOK = BASE_URL + "Bag/insert";

//    public final static String LISTCARDBYBAG = BASE_URL + "ListCardByBag";
    public final static String LISTCARDBYBAG = BASE_URL + "ListDescribeCardByBag";

    public final static String FINDCARDBYNAME = BASE_URL+"FindCardByname";
    public final static String FINDCARDBYWORD = BASE_URL+"FindCardByword";
//    public final static String GET = BASE_URL+"get";
//    public final static String GETALL = BASE_URL+"getAll";
    public final static String DELETEBOOK = BASE_URL+"bag/delete";
    public final static String LISTBAGWITHOUTCARD = BASE_URL+"ListBagWithoutCard";
    public final static String INSERTCARD = BASE_URL+"Card/insert2bagName";
    public final static String UPDATEBAG = BASE_URL+"updateBag";

    public final static String UPDATEUSERDES = BASE_URL+"updateUserDescribe";
    public final static String UPDATEPSW= BASE_URL+"updatePassword";

//    public final static String MODIFY = BASE_URL+"modify";
}
