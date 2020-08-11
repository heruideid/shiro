package com.nostalgia.shiroProject.service.impl;

import com.nostalgia.shiroProject.entity.Account;
import com.nostalgia.shiroProject.mapper.AccountMapper;
import com.nostalgia.shiroProject.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nostalgia
 * @since 2020-07-20
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
