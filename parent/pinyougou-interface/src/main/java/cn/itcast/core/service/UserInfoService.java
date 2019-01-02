package cn.itcast.core.service;

import entity.PageResult;
import entity.UserInfo;

public interface UserInfoService {
    PageResult search(Integer page, Integer rows, UserInfo userInfo);

    void outExcel();
}
