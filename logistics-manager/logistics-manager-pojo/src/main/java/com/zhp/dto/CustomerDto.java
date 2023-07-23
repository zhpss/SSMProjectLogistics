package com.zhp.dto;

import com.zhp.pojo.Customer;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-18 21:48
 **/
public class CustomerDto extends Customer {
    String userName;//业务员名称

    String intervalName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIntervalName() {
        return intervalName;
    }

    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "userName='" + userName + '\'' +
                ", intervalName='" + intervalName + '\'' +
                '}';
    }
}
