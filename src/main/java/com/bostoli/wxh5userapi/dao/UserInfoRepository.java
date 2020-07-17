package com.bostoli.wxh5userapi.dao;
import com.bostoli.wxh5userapi.model.internal.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    public UserInfo findByOpenId(String openId);

}
