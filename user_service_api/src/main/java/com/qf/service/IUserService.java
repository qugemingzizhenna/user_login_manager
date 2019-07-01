package com.qf.service;

import com.qf.entity.User;

import java.util.List;

public interface IUserService {
    int register(User user);
    List<User> queryAll();
   List<User> queryBy(User user);
   List<User> queryByName(String username);
   User queryById(Integer id);

}
