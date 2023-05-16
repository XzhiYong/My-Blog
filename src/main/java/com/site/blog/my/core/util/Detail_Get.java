package com.site.blog.my.core.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 阿里云音乐id
 */
@Slf4j
public class Detail_Get {

    public static JSONObject GET_Date(String DetailId, String userKey) {
        String Url = "https://music.163.com/api/playlist/detail?id=" + DetailId;
        String date = httpURLConnection(Url, userKey);
        JSONObject jsonObject = JSON_Processing(date);
        return jsonObject;
    }

    private static String httpURLConnection(String Url, String userKey) {
        StringBuilder sBuilder = null;
        try {
            URL url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置请求头，带上cookie参数
            connection.setRequestProperty("cookie", userKey);
            //一定要先设置，再开始连接
            connection.connect();

            //读取接口响应的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            sBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sBuilder.append(line);
            }
            br.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sBuilder.toString();
    }


    //对响应的歌单数据进行处理
    private static JSONObject JSON_Processing(String date) {
        if (JSONUtil.parseObj(date).getInt("code") != 200) {
            log.debug(JSONUtil.parseObj(date).getStr("msg"));
            return null;
        }
        String DetailName = JSONUtil.parseObj(date).getJSONObject("result").getStr("name");
        JSONArray Detail_Array = JSONUtil.parseObj(date).getJSONObject("result").getJSONArray("tracks");
        JSONObject Music_JsonObject;
        JSONArray list = new JSONArray();
        int play_time, minute, second;
        System.out.println(Detail_Array.size());
        for (int i = 0; i < Detail_Array.size(); ++i) {
            JSONObject map = new JSONObject();
            Music_JsonObject = (JSONObject) Detail_Array.get(i);
            map.set("name", Music_JsonObject.getStr("name"));//歌曲的歌名
            map.set("id", Music_JsonObject.getStr("id"));//歌曲的ID
            //歌曲歌手的姓名
            map.set("Singer", ((JSONObject) (Music_JsonObject.getJSONArray("artists").get(0))).getStr("name"));
            //歌曲的播放时长，初始时间为毫秒，将其处理转为（分：秒）形式
            play_time = (Music_JsonObject.getInt("duration"));
            minute = play_time / 60000; //分钟
            second = play_time / 1000 % 60; //秒
            if (second >= 10) {
                map.set("time", minute + ":" + second);
            } else {
                map.set("time", minute + ":0" + second);
            }
            //歌曲的封面图片url地址
            map.set("image", Music_JsonObject.getJSONObject("album").getStr("picUrl"));
            //判断歌曲是否为付费歌曲，主要看
            if (Music_JsonObject.getInt("fee") == 1) {
                map.set("vip", "true");
            } else {
                map.set("vip", "false");
            }
            list.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DetailName", DetailName);
        jsonObject.put("DetailMusic", list);
        return jsonObject;
    }

    public static void main(String[] args) {
        //userKey的形式为：__csrf=**********;MUSIC_U=*************;
        String DetailId = "2965698531";
        String userkey = "__csrf=a0fa34372b6009743dda0df9d96bacbe;MUSIC_U=00204EE388460C08598F3DB7C8C3A23432DE4A647559D2E42B75C37F4BDF8D5FB5D1BF0712E8EE64657FDA6F4A5DC2453085931E9929570EB9C76E30675C08E45E9B0C437D014A8F2E108B5B54677E89E1E3D89362CB71BB2805382E81C66A137A8C0F3254BD232E8169E0EE0344972A09AB6D498B6217DB9D004AA96A6776E5AE5CD284513B785F87CA033099E184C91823A2FC8D18A98D164C4BBF9E35F915E342919CB4F34EEC1648EA15E1F1B8E7A45E06AE7E6FE8A179FA515005A5DBED5CCAB5CD4795A9B6020E6666CE661778D38131697A185AF97DF023315FBF4E3BD7C6E4E690A5564E5655BBE3F5251737D085F8F91C30AD4792A2857A063CC55859BB9DD91B6245A4902108E3620EF59132457237C02BC19BA9CB25215E571CEC09123249302C5C0C940A01B38608AF457F3989BFB34DAB1A1DC1C0E2A657022D6441BA9EA10EA3C46BE4A36670B08C488B;";
        JSONObject jsonObject = null;
        for (int i = 0; i < 100; i++) {
            try {
                jsonObject = GET_Date(DetailId, userkey);
                if (jsonObject != null) {
                    break;
                }
                System.out.println(jsonObject);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(jsonObject);

    }
}

