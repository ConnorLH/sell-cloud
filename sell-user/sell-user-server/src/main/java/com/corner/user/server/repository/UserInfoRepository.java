package com.corner.user.server.repository;

import com.corner.user.server.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    UserInfo findByOpenid(String openid);
}
