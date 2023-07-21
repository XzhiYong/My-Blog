package com.site.blog.my.core.util;

import cn.hutool.http.HttpUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * 必应壁纸爬虫
 */
public class Reptile {
    public static void main(String[] args) {
        //调用工具类在桌面创建一个文件夹
        String directoryName = "photo";
        File photo = SpiderUtil.createDirectoryOnDesktop(directoryName);
        for (int i = 1; i <= 770; i++) {
            try {
                //通过for循环将页面
                Connection connect = Jsoup.connect("https://wallhaven.cc/latest?page=" + i);
//                connect.proxy("127.0.0.1", 7890);
                Document tempdocument = connect.timeout(10 * 1000).get();
                //获取当前页面装有图片类名为card的div容器
                Elements imgList = tempdocument.select(".item-container > img >");
                System.out.println("---------------第" + i + "页开始下载---------------");
                System.out.println("------------当前页共有：" + imgList.size() + "张图片------------");
                for (Element img : imgList) {
                    //获取图片链接地址
                    //获取类名为card的div容器下的第一个a标签的属性href值
                    String img_href = img.attr("href");// photo/LuciolaCruciata_ZH-CN9063767400?force=ranking_1
                    // 获取img_href页面中的.progressive--not-loaded标签
                    Elements elements = SpiderUtil.getElementsbyURL(img_href, "#wallpaper");
                    //http://h1.ioliu.cn/bing/ChefchaouenMorocco_ZH-CN6127993429_1920x1080.jpg?imageslim
                    String imgUrl = elements.attr("src");
                    HttpUtil.downloadFile(imgUrl, "C:\\Users\\BaiCai\\Desktop\\photo");

//                    // 休眠3秒 防反爬
                    Thread.sleep(3000);
                }
                System.out.println("--------------第" + i + "页已下载完成--------------");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
