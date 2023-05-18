package com.site.blog.my.core.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.site.blog.my.core.entity.IpRegion;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 修改自：<a href="https://github.com/lionsoul2014/ip2region/tree/master/binding/java">Ip2region</a>
 *
 * @author codecrab
 * @since 2022年07月07日 11:18
 */
@Slf4j
@Component
public class IpRegionUtil {

    private static final String dbPath = "prod".equals(SpringUtil.getActiveProfile()) ? "/ipRegion/ip2region.xdb" : ResourceUtil.getResource("ip2region.xdb").toString().substring(6);

    /** 矢量索引 */
    private static volatile byte[] vectorIndex;

    /** xdb文件缓存的搜索器 */
    private static volatile Searcher searcher;

    public IpRegionUtil() {
        initVectorIndex();
        initXdb();
    }

    /**
     * 完全基于文件的查询
     * 并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
     */
    public static String searchByDbFile(String ip) {
        IpRegion ipRegion = searchRegionByDbFile(ip);
        if (ipRegion == null) {
            return "本地局域网，无法获取位置";
        }

        return ipRegion.toString();
    }

    /**
     * 完全基于文件的查询
     * 并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
     */
    public static IpRegion searchRegionByDbFile(String ip) {
        if (StrUtil.isBlank(ip)) {
            return null;
        }

        if (Ipv4Util.LOCAL_IP.equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return null;
        }

        // 1、创建 searcher 对象
        Searcher searcher;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            throw new RuntimeException(StrUtil.format("无法创建搜索器 `{}`: {}", dbPath, e));
        }

        // 并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
        return IpRegion.of(search(ip, searcher));
    }

    /**
     * 缓存矢量索引
     * 每个线程需要单独创建一个独立的 Searcher 对象，但是都共享全局的制度 vIndex 缓存。
     */
    public static String searchByVectorIndex(String ip) {
        IpRegion ipRegion = searchRegionByVectorIndex(ip);
        if (ipRegion == null) {
            return "本地局域网，无法获取位置";
        }

        return ipRegion.toString();
    }

    /**
     * 缓存矢量索引
     * 每个线程需要单独创建一个独立的 Searcher 对象，但是都共享全局的制度 vIndex 缓存。
     */
    public static IpRegion searchRegionByVectorIndex(String ip) {
        if (StrUtil.isBlank(ip)) {
            return null;
        }

        if (Ipv4Util.LOCAL_IP.equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return null;
        }

        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
        Searcher searcher;
        try {
            searcher = Searcher.newWithVectorIndex(dbPath, vectorIndex);
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("无法创建矢量索引缓存搜索器 `{}`: {}", dbPath, e));
        }

        // 每个线程需要单独创建一个独立的 Searcher 对象，但是都共享全局的制度 vIndex 缓存。
        return IpRegion.of(search(ip, searcher));
    }

    /**
     * 缓存整个 xdb 数据
     * 并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
     */
    public static String searchByXdb(String ip) {
        IpRegion ipRegion = searchRegionByXdb(ip);
        if (ipRegion == null) {
            return "本地局域网，无法获取位置";
        }

        return ipRegion.toString();
    }


    /**
     * 缓存整个 xdb 数据
     * 并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
     */
    public static IpRegion searchRegionByXdb(String ip) {
        if (StrUtil.isBlank(ip)) {
            return null;
        }

        if (Ipv4Util.LOCAL_IP.equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return null;
        }

        // 并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
        return IpRegion.of(search(ip, searcher));
    }

    /**
     * 根据IP地址获取地理位置
     */
    public static String searchByBaiDu(String ip) {
        if (StrUtil.isBlank(ip)) {
            return "";
        }

        if (Ipv4Util.LOCAL_IP.equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return "本地局域网，无法获取位置";
        }

        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
        HttpResponse res = HttpRequest.get(url).execute();
        if (HttpStatus.HTTP_OK != res.getStatus()) {
            return searchByXdb(ip);
        }

        JSONObject jsonObject = JSONUtil.parseObj(res.body());
        JSONArray resArr = JSONUtil.parseArray(jsonObject.getStr("data"));
        JSONObject resJson = JSONUtil.parseObj(resArr.get(0).toString());
        return resJson.getStr("location");
    }

    public static void initVectorIndex() {
        // 1、从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用
        try {
            vectorIndex = Searcher.loadVectorIndexFromFile(dbPath);
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("未能从文件中加载矢量索引 `{}`: {}", dbPath, e));
        }
    }

    public static void initXdb() {
        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] xdbBuff;
        try {
            xdbBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("无法从XDB文件中加载内容 `{}`: {}", dbPath, e));
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        try {
            searcher = Searcher.newWithBuffer(xdbBuff);
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("创建内容缓存搜索器失败: {}", e));
        }
    }

    /**
     * @return 国家|区域|省份|城市|ISP
     */
    private static String search(String ip, Searcher searcher) {
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            log.info("地区: {}, io计数: {}, 耗时: {} μs", region, searcher.getIOCount(), cost);
            return region;
        } catch (Exception e) {
            throw new RuntimeException(StrUtil.format("搜索失败({}): {}", ip, e));
        }
    }

}
