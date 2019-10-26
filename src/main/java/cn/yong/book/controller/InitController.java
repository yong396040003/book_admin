package cn.yong.book.controller;

import cn.yong.book.juntil.CheckoutSession;
import cn.yong.book.juntil.ResourcePath;
import cn.yong.book.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description:
 * Date: 15:47 2018/12/29
 *
 * @author yong
 * @see
 */
@Controller
public class InitController {
    @Autowired
    private LoginService loginService;

    /**
     * 默认进入页面，login页面
     *
     * @return
     */
    @RequestMapping("/")
    public String login(Model model, HttpServletRequest request) {
        //判断session是否存在
        String username = CheckoutSession.checkoutSession(request);
        if (username != null) {
            model.addAttribute("manager", username);
            model.addAttribute("icon", loginService.getIcon(username));
            return "index/index";
        }
        return "login/login";
    }

    /**
     * 后台默认首页
     *
     * @return
     */
    @RequestMapping("/main.ac")
    public String main() {
        return "index/page/main";
    }

    /**
     * 管理员个人资料
     *
     * @return
     */
    @RequestMapping("/managerInfo.ac")
    public String managerInfo(Model model, HttpServletRequest request) {
        String username = CheckoutSession.checkoutSession(request);
        if (username != null) {
            model.addAttribute("manager", username);
            model.addAttribute("icon", loginService.getIcon(username));
            return "index/page/userCenter/manager/managerInfo";
        } else {
            return "login/login";
        }

    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("changePwd.ac")
    public String changePwd(){
        return "index/page/userCenter/manager/changePwd";
    }


//    ###########################用户中心############################################

    /**
     * 管理员中心
     *
     * @return
     */
    @RequestMapping("/manager.ac")
    public String manager(HttpServletRequest request) {
        String username = CheckoutSession.checkoutSession(request);
        if (username != null) {
            return "index/page/userCenter/manager/manager";
        } else {
            return "login/login";
        }
    }

    /**
     * 用户中心
     *
     * @return
     */
    @RequestMapping("/user.ac")
    public String user(HttpServletRequest request) {
        String username = CheckoutSession.checkoutSession(request);
        if (username != null) {
            return "index/page/userCenter/user/user";
        } else {
            return "login/login";
        }
    }

//    #################################################################################

//    ###########################内容管理#####################################################

    /**
     * 玄幻魔法
     *
     * @return
     */
    @RequestMapping("/fantasy.ac")
    public String fantasy() {
        return "index/page/contentManagement/book/fantasy";
    }

    /**
     * 武侠仙侠
     *
     * @return
     */
    @RequestMapping("/martial.ac")
    public String martial() {
        return "index/page/contentManagement/book/martial";
    }

    /**
     * 都市言情
     *
     * @return
     */
    @RequestMapping("/city.ac")
    public String city() {
        return "index/page/contentManagement/book/city";
    }

    /**
     * 历史军事
     *
     * @return
     */
    @RequestMapping("/history.ac")
    public String history() {
        return "index/page/contentManagement/book/history";
    }

    /**
     * 科幻小说
     *
     * @return
     */
    @RequestMapping("/science.ac")
    public String science() {
        return "index/page/contentManagement/book/science";
    }

    /**
     * 恐怖灵异
     *
     * @return
     */
    @RequestMapping("/supernatural.ac")
    public String supernatural() {
        return "index/page/contentManagement/book/supernatural";
    }

    /**
     * 网游竞技
     *
     * @return
     */
    @RequestMapping("/online.ac")
    public String online() {
        return "index/page/contentManagement/book/online";
    }

    /**
     * 女生小说
     *
     * @return
     */
    @RequestMapping("/girl.ac")
    public String girl() {
        return "index/page/contentManagement/book/girl";
    }

    /**
     * 其他小说
     *
     * @return
     */
    @RequestMapping("/colleagues.ac")
    public String colleagues() {
        return "index/page/contentManagement/book/colleagues";
    }

    /**
     * 完本小说
     *
     * @return
     */
    @RequestMapping("/finish.ac")
    public String finish() {
        return "index/page/contentManagement/book/finish";
    }

//    #################################################################################

//    ###########################公共类#####################################################


    /**
     * 添加图书
     *
     * @return
     */
    @RequestMapping("/addBook.ac")
    public String addBook()
    {
        return "index/page/contentManagement/book/addBook";
    }

    /**
     * 文件批量导入
     *
     * @return
     */
    @RequestMapping("/batchAdd.ac")
    public String batchAdd() {
        return "index/page/contentManagement/book/batchAdd";
    }

    /**
     * 文件批量导出
     *
     * @return
     */
    @RequestMapping("/batchExport.ac")
    public String batchExport(String url, String category, String sort, String status, Model model) {
        System.out.println(url + "  " + category + "  " + sort + "    " + status);
        model.addAttribute("url", url);
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);
        model.addAttribute("status", status);
        return "index/page/contentManagement/book/batchExport";
    }

    /**
     * 图书预览
     *
     * @return
     */
    @RequestMapping("/preview.ac")
    public String preview(int number, Model model) {
        model.addAttribute("number", number);
        return "index/page/contentManagement/book/preview";
    }

    /**
     * 新添书籍
     *
     * @return
     */
    @RequestMapping("/newBook.ac")
    public String newBook() {
        return "index/page/contentManagement/newBook";
    }

    /**
     * 书架
     *
     * @return
     */
    @RequestMapping("/bookshelf.ac")
    public String bookshelf() {
        return "index/page/contentManagement/bookshelf";
    }

    /**
     * 排行榜
     *
     * @return
     */
    @RequestMapping("/list.ac")
    public String list() {
        return "index/page/contentManagement/list";
    }

    /**
     * 帮助
     *
     * @return
     */
    @RequestMapping("/help.ac")
    public String help() {
        return "index/page/contentManagement/help";
    }


    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping("/addUser.ac")
    public String addUser(String sex,Model model) {
        model.addAttribute("sex",sex);
        return "index/page/userCenter/user/addUser";
    }

    /**
     * Nav 内容管理
     *
     * @return
     */
    @RequestMapping("/contentManagement.nav")
    public String contentManagement() {
        return "index/page/systemSetting/nav/contentManagement";
    }

    /**
     * Nav 用户中心
     *
     * @return
     */
    @RequestMapping("/userCenter.nav")
    public String userCenter() {
        return "index/page/systemSetting/nav/userCenter";
    }

    /**
     * Nav 系统设置
     *
     * @return
     */
    @RequestMapping("/systemSetting.nav")
    public String systemSetting() {
        return "index/page/systemSetting/nav/systemSetting";
    }

    /**
     * Nav 使用文档
     *
     * @return
     */
    @RequestMapping("/seraphApi.nav")
    public String seraphApiNav() {
        return "index/page/systemSetting/nav/seraphApi";
    }

    /**
     * Nav 使用文档
     *
     * @return
     */
    @RequestMapping("/addNav.ac")
    public String addNav() {
        return "index/page/systemSetting/nav/addNav";
    }

    /**
     * 系统参数
     *
     * @return
     */
    @RequestMapping("/system.ac")
    public String system() {
        return "index/page/systemSetting/system";
    }

    /**
     * 使用文档
     *
     * @return
     */
    @RequestMapping("/seraphApi.ac")
    public String seraphApi() {
        return "index/page/seraphApi/seraphApi";
    }
}
