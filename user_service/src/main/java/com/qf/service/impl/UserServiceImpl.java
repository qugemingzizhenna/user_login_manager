package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @user yzb
 * @date 2019-06-30 13:00
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.selectList(null);
    }

    @Override
    public List<User> queryBy(User user) {
        Map<String ,Object> columnMap = new HashMap<>();
        columnMap.put("username",user.getUsername());
        columnMap.put("password",user.getPassword());
        List<User> users = userMapper.selectByMap(columnMap);
        return users;
    }

    @Override
    public List<User> queryByName(String username) {
        Map<String ,Object> columnMap = new HashMap<>();
        columnMap.put("username",username);
        return userMapper.selectByMap(columnMap);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.selectById(id);
    }
}
