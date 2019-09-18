package cn.yong.book.service.serviceimpl;

import cn.yong.book.mapper.SaveBookToSqlMapper;
import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import cn.yong.book.service.SaveBookToSqlService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Description:
 * Date: 14:34 2019/1/21
 *
 * @author yong
 * @see
 */
@Service
public class SaveBookToSqlServiceImpl implements SaveBookToSqlService {
    private final static Logger logger = LoggerFactory.getLogger(SaveBookToSqlServiceImpl.class);

    @Autowired
    private SaveBookToSqlMapper sqlMapper;

    @Override
    public int saveBook(List<Book> books) {
        return sqlMapper.saveBook(books);
    }

    @Override
    public int saveCatalogue(List<Catalogue> catalogues) {
        return sqlMapper.saveCatalogue(catalogues);
    }

    @Override
    public JSONObject getBookToSql(int page, int limit, String paiHang) {
//        PageHelper.startPage(page, limit);
//        String []paiHang1 = {"fantasy","martial","city","history","science","supernatural","online","girl","colleagues"};
//        String []paiHang2 = {"玄幻","武侠","都市","历史","科幻","灵异","网游","女生","其他"};
//        List<Book> books = null;
//        for(int i = 0; i < paiHang1.length; i++){
//            if(paiHang1[i].equals(paiHang)){
//                books = sqlMapper.getBookToSql(paiHang2[i]);
//            }
//        }
//
//        for(Book book : books){
//            File file = new File(book.getSynopsis());
//            if(!file.exists()){
//                logger.warn("文件不存在");
//                break;
//            }
//            try {
//                    FileReader reader = new FileReader(file);
//                    BufferedReader bufferedReader = new BufferedReader(reader);
//                    String str = null;
//                    while ((str=bufferedReader.readLine()) != null){
//                    str = str.replaceAll("<p>","");
//                    str = str.replaceAll("</p>","");
//                    str = str.replaceAll("&nbsp;","");
//                    book.setSynopsis(str);
//                    }
//                    }catch (Exception e){
//                    e.printStackTrace();
//                    }
//                    }PageInfo<Book> pageInfo = new PageInfo<Book>(books);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("code",0);
//        jsonObject.put("msg","book");
//        jsonObject.put("total",pageInfo.getTotal());
//        jsonObject.put("data",pageInfo.getList());
        return null;
    }      
}

