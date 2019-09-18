package cn.yong.book.service.serviceimpl;

import cn.yong.book.mapper.NavMapper;
import cn.yong.book.pojo.Nav;
import cn.yong.book.service.NavService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Date: 16:55 2018/12/10
 *
 * @author yong
 * @see
 */
@Service
public class NavsServiceImpl implements NavService {
    @Autowired
    private NavMapper navsMapper;


    /**
     * 左边导航栏遍历
     *
     * @return
     */
    @Override
    public JSONObject getAllNavList() {
        JSONObject jsonObject = new JSONObject();
        //获取内容管理导航栏数据
        List<Nav> navList = navsMapper.getAllContentManagement();
        //获取内容管理树节点
        List<Nav> navTree = navsMapper.getAllContentManagementTree();
        ergodicNav(navTree, navList);

        //获取用户中心导航栏数据
        List<Nav> userList = navsMapper.getAllUserCenter();

        List<Nav> systemSetting = navsMapper.getAllSystemSetting();
        List<Nav> systemSettingTree = navsMapper.getAllSystemSettingTree();
        ergodicNav(systemSettingTree, systemSetting);

        List<Nav> seraphApi = navsMapper.getAllSeraphApi();
        jsonObject.put("contentManagement", navList);
        jsonObject.put("userCenter", userList);
        jsonObject.put("systemSetting", systemSetting);
        jsonObject.put("seraphApi", seraphApi);
        return jsonObject;
    }

    /**
     * 遍历利nav根节点
     * @param navTree
     * @param navList
     */
    public void ergodicNav(List<Nav> navTree, List<Nav> navList) {
        //当根节点不为空时
        if (navTree != null) {
            //遍历树节点
            for (Nav c : navList) {
                List<Nav> c2 = new ArrayList<Nav>();
                //遍历根节点
                for (Nav c1 : navTree) {
                    if (c.getId() == c1.getPid()) {
                        c2.add(c1);
                    }
                }
                c.setChildren(c2);
            }
        }
    }


}
