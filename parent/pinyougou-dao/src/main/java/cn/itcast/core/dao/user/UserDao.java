package cn.itcast.core.dao.user;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import java.util.List;

import entity.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int countByExample(UserQuery example);

    int deleteByExample(UserQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserQuery example);

    User selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserQuery example);

    int updateByExample(@Param("record") User record, @Param("example") UserQuery example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByUserName(String username);

    List<User> selectActiveUser();

    List<User> selectUnActiveUser();


    List<UserInfo> selectUserInfo();

    List<String> selectSexfromUser();

    List<String> selectIdfromUser();

    List<String> selectUserNamefromUser();

    List<String> selectOrderIdfromUser();

    List<String> selectGoodsNamefromUser();
}