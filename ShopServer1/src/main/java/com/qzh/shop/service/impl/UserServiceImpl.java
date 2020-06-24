package com.qzh.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qzh.shop.POJO.DO.Users;
import com.qzh.shop.mapper.UsersMapper;
import com.qzh.shop.service.UsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @Author Ruoany
 * @Description
 * @Date 2020/5/14 19:25
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UsersService {


    @Resource
    private UsersMapper usersMapper;


    @Override
    public IPage<Users> getAllUsers(Integer page, Integer size) throws ParseException {

       IPage<Users> iPage = usersMapper.selectPage(new Page<Users>(page,size),new QueryWrapper<Users>());
       for (int i = 0; i < iPage.getTotal(); i++) {
           String date = iPage.getRecords().get(i).getCreateTime();
           iPage.getRecords().get(i).setCreateTime(date);
       }

        return iPage;
    }



    @Override
    public Users getUsersById(Integer id) {
        return usersMapper.selectById(id);
    }

    @Override
    public IPage<Users> getAll(Integer page, Integer size, String query) {
        return  usersMapper.selectPage(new Page<Users>(page,size),
                new QueryWrapper<Users>()
                        .or().like("username",query)
                        .or().like("mobile",query)
                        .or().like("email",query)
                        .or().like("role_name",query));
    }


    @Override
    public int updateUsers(String username, String email, String mobile) {
        Users users = new Users();
        users.setEmail(email);
        users.setMobile(mobile);

        return usersMapper.update(users,new UpdateWrapper<Users>().eq("username",username));
    }

    @Override
    public Users getUsersByUsername(String username) {
        return usersMapper.selectOne(new QueryWrapper<Users>().eq("username",username));
    }

    @Override
    public int deleteUsersById(Integer id) {
       return usersMapper.deleteById(id);
    }
}
