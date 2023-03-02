package com.kghdev.json;

import org.json.JSONArray;
import org.json.JSONObject;

public class jsonTest {

    public static void main(String[] args){

        /**
         * Json Array 만들기
         * */

        JSONObject jsonObj1 = new JSONObject();
        jsonObj1.put("이름", "가나다");
        jsonObj1.put("번호", "1");

        JSONObject jsonObj2 = new JSONObject();
        jsonObj2.put("이름", "라바사");
        jsonObj2.put("번호", "2");

        JSONArray jsonArray =  new JSONArray();
        jsonArray.put(jsonObj1);
        jsonArray.put(jsonObj2);

        /**
         * Json Object 색출!
         * */

       for(int i = 0; i<jsonArray.length(); i++){
           JSONObject jsonObject = jsonArray.getJSONObject(i); // jsonObject = jsonObject1
           String name = jsonObject.getString("이름"); //name = "가나다"

           if("가나다".equals(name)){

               /**
                * 찾았다 가나다 이놈!!
                * */
               System.out.println("희정아 가나다 번호 요깄쨩! ==== " + jsonObject.getString("번호"));
               break; //반복문 빠져나가보리깁!!!
           }
       }

    }


}
