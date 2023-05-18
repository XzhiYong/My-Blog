package com.site.blog.my.core.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author codecrab
 * @since 2022年07月07日 12:37
 */
@Data
public class IpRegion {

    /** 国家 */
    private String country;

    /** 区域 */
    private String area;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 互联网服务提供商（运营商） */
    private String isp;

    /**
     * 把 "国家|区域|省份|城市|ISP" 格式数据转为对象
     *
     * @param ipRegionStr 国家|区域|省份|城市|ISP 字符串
     * @return IP区域对象
     */
    public static IpRegion of(String ipRegionStr) {
        if (StrUtil.isBlank(ipRegionStr)) {
            return new IpRegion();
        }

        String[] split = ipRegionStr.split("\\|");
        IpRegion ipRegion = new IpRegion();
        ipRegion.setCountry("0".equals(split[0]) ? null : split[0]);
        ipRegion.setArea("0".equals(split[1]) ? null : split[1]);
        ipRegion.setProvince("0".equals(split[2]) ? null : split[2]);
        ipRegion.setCity("0".equals(split[3]) ? null : split[3]);
        ipRegion.setIsp("0".equals(split[4]) ? null : split[4]);
        return ipRegion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.country != null) {
            sb.append(this.country).append(" ");
        }
        if (this.area != null) {
            sb.append(this.area);
        }
        if (this.province != null) {
            if (this.city == null || !this.city.contains(this.province)) {
                sb.append(this.province);
            }
        }
        if (this.city != null) {
            sb.append(this.city);
        }
        if (this.isp != null) {
            sb.append(" ").append(this.isp);
        }
        return sb.toString();
    }

}
