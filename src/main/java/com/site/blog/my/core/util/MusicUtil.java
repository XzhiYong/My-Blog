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
public class MusicUtil {

    public static JSONObject GET_Date(String DetailId, String userKey) {
        String Url = "https://music.163.com/api/playlist/detail?id=" + DetailId;
        String date = httpURLConnection(Url, userKey);
        return JSON_Processing(date);
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
        String userkey = "__csrf=865e7ed0119362517e6579b4d6f2830d;MUSIC_U=00402C0C87D49BD4D564D36A3C31BCAE22FAB53EF6802FD54E26ED96D72EA82D3A72441A0CB1F456D5EB5EC78EBC241244BC00693230A4558607F45F3599068B07B8A07A48215C86E9BD6780B1BBCD7118D84E5612B347ADB487F4EB1E26FBD092C9572453F6F8B5366FF7E1EA8EA74F76B81D3D22660B20B7F6CB9DC9A79FC5CF441735C8D001A31A637ABBAA5EB314DE47B4DDD832BA0AD33329E625E4267B9F9564BC05191A7ED925C803B1DEF809EC58D23BEF2E3AEABF74EECF8BD8716C243F6310663A91F681C5C7B5C8FEC0FC01D985A0A94BC9423DFFBFE2FBE33081E038DE2F5DAD244E7DDA56BB0170CA39FE598A0BBA4BB14E0B85DB388A4492E8F925AA2601A64119E2F076624847C34426F5F2D7167F046CE7878DDB8F52C33248B251CD2E2EC8045CF995C85252F4A23E777C9C58C46005F803F777E41FEE7F7B504177BFD3CB8924B72AD609DB6B5A08FE635D68006EC4CA4902D8243A758DF9;";
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

