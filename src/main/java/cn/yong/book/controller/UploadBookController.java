package cn.yong.book.controller;

import cn.yong.book.juntil.ReadExcel;
import cn.yong.book.pojo.Book;
import cn.yong.book.service.BookService;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Date: 16:32 2019/7/7
 * 上传格式 excel csv
 *
 * @author yong
 * @see
 */
@Controller
public class UploadBookController {
    @Autowired
    private BookService bookService;

    private final String excel[] = {".xls", ".csv", ".xlsx"};

    /**
     * 解析excel
     *
     * @param mf
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/uploadBookExcel.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadBookExcel(@RequestParam MultipartFile mf, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到文件名
        String fileName = mf.getOriginalFilename();
        //得到输入流
        InputStream inputStream = mf.getInputStream();

        //code msg code:0上传成功
        JSONObject jsonObject = new JSONObject();
        //如果获取到的文件名为空 证明未获取到上传的文件
        if (fileName == null) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "后台未接受到上传的文件");
            return jsonObject;
        }

        List<Book> bookList = new ArrayList<>();

        //xls csv xlsx分别解析
        if (fileName.endsWith(excel[0])) {
            try {
                bookList = ReadExcel.xls(inputStream);
            } catch (Exception e) {
                jsonObject.put("code", 1);
                jsonObject.put("msg", "你使用的xls文件有误" + e.getMessage());
                return jsonObject;
            }
        } else if (fileName.endsWith(excel[1])) {
            bookList = ReadExcel.csv(inputStream);
        } else if (fileName.endsWith(excel[2])) {
            bookList = ReadExcel.xlsx(inputStream);
        } else {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "未知错误");
        }
        inputStream.close();

        for (Book book : bookList) {
            try {
                book.setId(0);
                jsonObject = bookService.bookUpdate(book);
            } catch (Exception e) {
                jsonObject = new JSONObject();
                jsonObject.put("code", 1);
                jsonObject.put("msg", "数据异常" + e.getMessage());
                continue;
            }
        }

        return jsonObject;
    }

}
