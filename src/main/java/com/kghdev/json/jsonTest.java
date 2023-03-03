package com.kghdev.json;

import org.json.JSONArray;
import org.json.JSONObject;

public class jsonTest {

    public static void main(String[] args){

        /**
         * Json Array 만들기
         * */

        JSONObject GANADA = new JSONObject();
        GANADA.put("이름", "가나다");
        GANADA.put("번호", "1");

        JSONObject RABASA = new JSONObject();
        RABASA.put("이름", "라바사");
        RABASA.put("번호", "2");

        JSONArray jsonArray =  new JSONArray();
        jsonArray.put(GANADA);
        jsonArray.put(RABASA);

        /**
         * Json Object 색출!
         * */

       for(int i = 0; i<jsonArray.length(); i++){
           JSONObject jsonObject = jsonArray.getJSONObject(i); // jsonObject = GANADA
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
