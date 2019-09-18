package cn.yong.book.juntil;

import cn.yong.book.pojo.Book;
import cn.yong.book.service.serviceimpl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Description:
 * Date: 18:19 2019/8/20
 *
 * @author yong
 * @see
 */
public class ReadWriteFile {
    private final static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    /**
     * 从文件夹里读取目录
     *
     * @param book
     * @return
     */
    public static void readSynopsis(Book book) {
        String path = book.getSynopsis();
        File file = new File(path);
        if (!file.exists()) {
            logger.warn("文件不存在");
        }
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                str = str.replaceAll("<p>", "");
                str = str.replaceAll("</p>", "");
                str = str.replaceAll("&nbsp;", "");
                book.setSynopsis(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将简介写到文件里
     *
     * @param synopsis
     * @param number
     */
    public static void writeSynopsis(String synopsis, int number) {
        //设置简介
        //将字符串写到文件里
        try {
            File dirFile = new File(ResourcePath.resourcePath + "/book/" + number);
            if (!dirFile.exists()) {
                try {
                    //创建书的目录
                    dirFile.mkdirs();
                } catch (Exception e) {
                    logger.error("创建文件目录失败");
                }
            }
            File file = new File(ResourcePath.resourcePath + "/book/" + number + "/" + 0 + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(synopsis.getBytes());
            outputStream.close();
        } catch (Exception e) {

        }
    }
}
