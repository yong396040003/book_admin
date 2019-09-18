package cn.yong.book.service.serviceimpl;

import cn.yong.book.mapper.LoginMapper;
import cn.yong.book.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Date: 16:28 2018/12/30
 *
 * @author yong
 * @see
 */
@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private LoginMapper loginMapper;

    public Boolean checkout(String username, String password) {
        //获取查询到的返回值。有大于0，无小于等于0
        int count = loginMapper.checkout(username,password);
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }

    public String getIcon(String username) {
        return loginMapper.getIcon(username);
    }
}

