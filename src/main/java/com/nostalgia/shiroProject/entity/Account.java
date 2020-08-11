package com.nostalgia.shiroProject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author nostalgia
 * @since 2020-07-20
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Account implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String username;

    private String password;

    private String salt;

    private String roles;

    private String perms;


}
