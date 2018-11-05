package com.corner.user.server.service;

import com.corner.user.server.dataobject.UserInfo;

public interface UserService {
    UserInfo findByOpenid(String openid);
}
