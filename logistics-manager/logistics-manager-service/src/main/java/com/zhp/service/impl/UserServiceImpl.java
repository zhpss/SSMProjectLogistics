package com.zhp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhp.dto.UserDto;
import com.zhp.mapper.RoleMapper;
import com.zhp.mapper.UserMapper;
import com.zhp.mapper.UserRoleMapper;
import com.zhp.pojo.*;
import com.zhp.service.IRoleService;
import com.zhp.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: SSMProjectYangCheng
 * @description:
 * @author: Mr.zhang
 * @create: 2023-07-09 14:25
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    private IRoleService roleService;
    @Override
    public List<User> query(User user) throws Exception {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if (user != null){
            if (user.getUserName() != null && !"".equals(user.getUserName())){
                criteria.andUserNameEqualTo(user.getUserName());
            }
        }
        //查询u2不为1的记录
        criteria.andU2IsNull();
        return userMapper.selectByExample(userExample);
    }

    @Override
    public Integer addUser(User user) throws Exception {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer updateUser(User user) throws Exception {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer deleteUser(Integer id) throws Exception {
        User user = new User();
        user.setUserId(id);
        user.setU2("1");
        return userMapper.updateByPrimaryKeySelective(user);
    }
    /*
    * 1.添加用户信息
    * 2.和更新用户信息
    *
    * */
    @Override
    public Integer saveOrUpdate(UserDto dto) throws Exception {
        //1.添加用户信息UserRoleMapper.xml
        User user = dto.getUser();
        if (user.getUserId() != null){
            //表示是进行用户的更新操作
            this.updateUser(user);
        }else {
            // 需要对密码加密 MD5加密
            String salt = UUID.randomUUID().toString();
            Md5Hash passwordHash = new Md5Hash(user.getPassword(), salt,1024);
            user.setPassword(passwordHash.toString());
            user.setU1(salt);
            System.out.println(user);

            this.addUser(user);
        }
        System.out.println(user);
        //根据用户编号删除对应的角色信息
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(user.getUserId());
        userRoleMapper.deleteByExample(example);
        //2.分配用户和角色的关联关系
        //获取分配给当前用户
        List<Integer> roleIds = dto.getRoleIds();
        if (roleIds != null && roleIds.size() > 0){
            //表示分配的有角色信息
            for (Integer roleId : roleIds) {
                //将用户和角色的关联关系保存到t_user_role 中
                UserRoleKey userRoleKey = new UserRoleKey();
                userRoleKey.setUserId(user.getUserId());
                userRoleKey.setRoleId(roleId);
                userRoleMapper.insertSelective(userRoleKey);
            }
        }
        return null;
    }

    @Override
    public User queryById(Integer userid) throws Exception {
        return userMapper.selectByPrimaryKey(userid);
    }

    @Override
    public List<Integer> queryUserRoleIds(Integer userId) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserRoleKey> userRoleKeys = userRoleMapper.selectByExample(example);
        List<Integer> ids = new ArrayList<>();
        for (UserRoleKey userRoleKey : userRoleKeys) {
            ids.add(userRoleKey.getRoleId());
        }
        return ids;
    }
    /*
    * 分页查询的方法
    *
    * */
    @Override
    public PageInfo<User> queryBypage(UserDto userDto) throws Exception {
        PageHelper.startPage(userDto.getPageNum(),userDto.getPageSize());
        List<User> query = this.query(userDto.getUser());
        PageInfo<User> pageInfo = new PageInfo<>(query);
        for (User user : query) {
            System.out.println(user);
        }
        return pageInfo;
    }
    /*
    *  完成登录认证的方法
    * */
    @Override
    public User login(String userName) {
        UserExample example = new UserExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<User> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0){
            return list.get(0);
        }

        return null;
    }
    /*
    * userId -->List<roleId> -->list<Role>
    * */
    @Override
    public List<Role> queryUserHaveRole(User user) {
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andUserIdEqualTo(user.getUserId());
        List<UserRoleKey> list = userRoleMapper.selectByExample(example);
        if (list != null && list.size() > 0){
            List<Role> roles = new ArrayList<>();
            for (UserRoleKey userRoleKey : list) {
                Role role = roleMapper.selectByPrimaryKey(userRoleKey.getRoleId());
                roles.add(role);

            }
            return roles;
        }
        return null;
    }

    /**
     * 根据角色的名称查询用户信息 t_role t_uuser_role -->userId --> User
     * @param rolename
     * @return
     */

    @Override
    public List<User> queryUserByRoleName(String rolename) throws Exception {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleNameEqualTo(rolename);
        List<Role> roles = roleMapper.selectByExample(roleExample);
        if (roles != null && roles.size() > 0){
            Role role = roles.get(0);
            UserRoleExample userRoleExample = new UserRoleExample();
            userRoleExample.createCriteria().andRoleIdEqualTo(role.getRoleId());
            List<UserRoleKey> userRoleKeys = userRoleMapper.selectByExample(userRoleExample);
            if (userRoleKeys != null && userRoleKeys.size() > 0){
                List<User> list = new ArrayList<>();
                for (UserRoleKey userRoleKey : userRoleKeys) {
                    User user = this.queryById(userRoleKey.getUserId());
                    list.add(user);
                }

                System.out.println("----------------------------------");
                System.out.println(list);
                System.out.println("----------------------------------");
                System.out.println("----------------------------------");
                System.out.println("----------------------------------");
                System.out.println("----------------------------------");
                System.out.println("----------------------------------");
                return list;
            }
        }
        return null;
    }
}
