package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user yzb
 * @date 2019-06-30 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {


    private Integer id;
    private String emailName;
}
