package com.example;

import lombok.Data;
import lombok.ToString;

/**
 * @author Cnfn
 * @date 2017/11/19
 */
@Data
public class IpInfo {
    private String ip;
    private String hostname;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;
    private String postal;
    private String phone;
}
