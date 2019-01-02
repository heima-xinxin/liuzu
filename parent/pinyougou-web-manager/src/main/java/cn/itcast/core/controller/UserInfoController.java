package cn.itcast.core.controller;

import cn.itcast.core.service.UserInfoService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import entity.UserInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userDetail")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody UserInfo userInfo){
       return userInfoService.search(page,rows,userInfo);
    }

    @RequestMapping("/outExcel")
    public Result outExcel(){
        try {
            userInfoService.outExcel();
            return new Result(true,"导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"导入失败");
        }

    }
}
