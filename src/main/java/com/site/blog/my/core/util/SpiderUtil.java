package com.site.blog.my.core.util;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
 
public class SpiderUtil {
    private static String UserAgent = "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";
    private static int TIMEOUT = 30000;//设置访问超时时间（ms）
 
    /**
     * 在桌面创建一个名为directoryName的文件夹
     *
     * @param directoryName
     */
    public static File createDirectoryOnDesktop(String directoryName) {
        javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
        File home = fsv.getHomeDirectory();
        File file = new File(home + "/" + directoryName);
        if (!file.exists())
            file.mkdir();//创建文件夹
        return file;
    }
 
    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static File createDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
 
    /**
     * 在指定路径下创建一个名为directoryName的文件夹
     *
     * @param path
     * @param directoryName
     * @return
     */
    public static File createDirectory(String path, String directoryName) {
        File file = new File(path, directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
 
    /**
     * 在指定路径下创建一个名为directoryName的文件夹
     *
     * @param path
     * @param directoryName
     * @return
     */
    public static File createDirectory(File path, String directoryName) {
        File file = new File(path, directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
 
    /**
     * 根据url以及xpath获取页面的Elements并将其返回
     *
     * @param url
     * @param xpath
     * @return
     */
    public static Elements getElementsbyURL(String url, String xpath) {
        try {
            Connection connect = Jsoup.connect(url);
            connect.proxy("127.0.0.1",7890);
            Document tempdocument = connect.timeout(10 * 10000).get();
 
           return tempdocument.select(xpath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("访问" + url.toString() + "超时");
        }
        return null;
    }
 
    /**
     * 获取URL的输入流
     *
     * @param url
     * @return
     */
    public static InputStream getInpuStream(String url) {
        URL imgurl = null;
        try {
            imgurl = new URL(url);
            //通过URL获取URLConnection对象
            URLConnection imgconnect = imgurl.openConnection();
            //然后添加请求头信息（模糊网站对爬虫的识别，将爬虫看作浏览器访问处理）
            imgconnect.setRequestProperty("User-Agent", UserAgent);
            //返回URLConnection输入流
            return imgconnect.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 下载文件
     *
     * @param is
     * @param os
     * @return true下载成功 false下载失败
     */
    public static boolean downloadFile(InputStream is, OutputStream os) {
        try {
            byte[] b = new byte[2048];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                //写入
                os.write(b, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
