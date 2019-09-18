package cn.yong.book.service.serviceimpl;

import cn.yong.book.mapper.UserMapper;
import cn.yong.book.pojo.User;
import cn.yong.book.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Date: 0:53 2019/1/4
 *
 * @author yong
 * @see
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询所有用户数据
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JSONObject getAll(int page, int limit) {
        //设定一页显示多少数据，默认limit为10
        PageHelper.startPage(page, limit);
        List<User> managers = userMapper.getAll();
        PageInfo<User> managerPageInfo = new PageInfo<User>(managers);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",managerPageInfo.getList());
        jsonObject.put("count",managerPageInfo.getTotal());
        jsonObject.put("code",0);
        return jsonObject;
    }

    @Override
    public JSONObject getMsg(User user) {
        //判断是否是新数据
        int code = 0;
        String msg = "未知错误";
        //新数据 0
        if (user.getUserId() == 0){
            user.setUserStatus("0");
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setUserEndTime(df.format(new Date()));
            code = userMapper.insertUser(user);
            if (code > 0){
                msg = "数据插入成功";
            }else {
                msg = "数据插入失败";
            }
        }else {
            code = userMapper.updateUser(user);
            if (code > 0){
                msg = "数据修改成功";
            }else {
                msg = "数据修改失败";
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject;
    }

    @Override
    public JSONObject isDelete(int id) {
        int code = userMapper.deleteUserById(id);
        String msg = "删除失败";
        if (code > 0){
            msg = "删除成功";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject;
    }

    @Override
    public JSONObject bathDeleteUserById(int[] data) {
        int code = userMapper.bathDeleteUserById(data);
        String msg = "删除失败";
        if (code > 0){
            msg = "删除成功";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        return jsonObject;
    }
}
