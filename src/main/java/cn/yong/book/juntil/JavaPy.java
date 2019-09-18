package cn.yong.book.juntil;

import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * java爬虫，爬取地点顶点书店
 * Date: 21:39 2018/10/6
 *
 * @author yong
 * @see
 */
public class JavaPy {
    /**
     * 日志文件
     */
    private static final Logger logger = LoggerFactory.getLogger(JavaPy.class);
    /**
     * 爬取地址
     */
    private final static String URL = "https://www.23us.so/";
    /**
     * 目录 地址的正则表达式1.0
     */
    private final static String bookMu_Path = "[https://www.23us.so/files/article/html/0/0-9.html\\\">]+[a-zA-Z+_=正文\\s]*第[\\u4e00-\\u9fa50-9]+?章+?[\\[\\]【】A-z0-9()（）\\s:：，,\\u4e00-\\u9fa5！？。]*[https://www.23us.so/files/article/html/0/0-9.html\\\">]+[a-zA-Z+_=正文\\s]*第[\\u4e00-\\u9fa50-9]+?章+?[\\[\\]【】A-z0-9()（）\\s:：，,\\u4e00-\\u9fa5！？。]*";
    /**
     * bookMu_Path1 目录 地址的正则表达式1.2(匹配目录td标签)
     * bookMu_Href 目录链接地址
     */
    private final static String bookMu_Path1 = "<td class=\"L\">(?:.|[\\r\\n])*?</td>";
    private final static String bookMu_Href = "[https://www.23us.so/files/article/html/0/0-9]*.html";
    private final static String bookMu_Title = "html\">[\\s\\S]*</a>";
    /**
     * 匹配小说章节的内容
     */
    private final static String CONTENTS = "<dd id=\"contents\">+.+?</dd>";
    /**
     * 书名
     */
    private final static String BOOK_NAME = "<h1>[^>]+</h1>";
    /**
     * 获取小说类型、作者、小说状态、收藏、字数、更新时间、总点击数、月点击数、周点击数、总推荐数、月推荐数、周推荐数
     */
    private final static String AUTHOR_STATUS = "<td>(?:.|[\\r\\n])*?</td>";
    /**
     * 获取简介
     */
    private final static String SYNOPSIS = "<p>[^>]*<br /></p>";
    /**
     * 书id
     */
    private static int bookId;
    /**
     * 目录id
     */
    private static int catalogueId;

    /**
     * 通过GET请求抓取整张页面
     *
     * @param url
     * @return
     */
    public static String senGet(String url) {
        //定义一个字符串来返回页面内容
        String result = "";
        //定义一个缓冲字符输入流
        BufferedReader in = null;

        try {
            //将String url转换成url对象
            URL realUrl = new URL(url);
            //初始化一个链接到url那个连接
            URLConnection connection = realUrl.openConnection();
            //开始实际的连接
            connection.connect();
            //初始化BufferedReader输入流
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf8"));
            //用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求失败：" + e);
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 通过正则表达式抓取指定内容
     *
     * @param targetStr
     * @param patternStr
     * @return
     */
    public static List<String> RegexString(String targetStr, String patternStr) {
        //定义一个样式模板，此中使用正则表达式，括号中是要抓取的内容
        Pattern pattern = Pattern.compile(patternStr);

        //定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(targetStr);
        List<String> result = new ArrayList<String>();
        //如果找到了
        while (matcher.find()) {
            //打印输出结果
            result.add(matcher.group());
        }

        if (result != null) {
            return result;
        }

        return null;
    }

    /**
     * 获取图书基本信息
     * id:主键
     * number:书号
     * name:书名
     * author:作者
     * status:更新状态
     * wordNumber:字数
     * img:图片地址
     * synopsis:简介
     *
     * @param start 书籍编号起始位置
     * @param end   书籍编号结束位置
     * @return
     */
    public static List<Book> getBook(int start, int end) {
        //设置一个列表来接收匹配到的数据
        List<String> list = null;
        //存储book信息
        List<Book> books = new ArrayList<Book>();
        //定义即将访问的链接
        String url = "https://www.23us.so/xiaoshuo/00";
        logger.info("start-----------------------------");
        for (int i = start; i <= end; i++) {
            logger.info("开始抓取第" + i + "本书籍信息");
            //重新定义发送地址
            String url1 = url + i + ".html";
            //获取目标网页
            String result = senGet(url1);
            if (result == null) {
                continue;
            }
            //初始化一本书
            Book book = new Book();
            bookId++;
            book.setId(bookId);
            //设置书号
            book.setNumber(i);
            list = RegexString(result, BOOK_NAME);
            String name = list.get(0).replaceAll("<h1>", "");
            name = name.replaceAll("</h1>", "");
            name = name.replaceAll(" 全文阅读", "");

            book.setName(name);       list = RegexString(result, AUTHOR_STATUS);
            for (int k = 0; k < list.size(); k++) {
                list.set(k, list.get(k).replaceAll("<td>&nbsp;", ""));
                list.set(k, list.get(k).replaceAll("</td>", ""));
            }
            List<String> category = RegexString(list.get(0), "[^\\x00-\\xff]+");
            //小说类型
            book.setCategory(category.get(0));
            //作者
            book.setAuthor(list.get(1));
            //状态
            book.setStatus(list.get(2));
            //收藏
            book.setCollection(Integer.parseInt(list.get(3)));
            //字数
            book.setWordNumber(list.get(4));
            //更新时间
            book.setData(list.get(5));
            //总点击数
            book.setTotalHits(Integer.parseInt(list.get(6)));
            //月点击数
            book.setMonthlyClicks(Integer.parseInt(list.get(7)));
            //周点击数
            book.setWeeklyClicks(Integer.parseInt(list.get(8)));
            //总推荐数
            book.setTotalRecommendedNumber(Integer.parseInt(list.get(9)));
            //月推荐数
            book.setMonthlyRecommendedNumber(Integer.parseInt(list.get(10)));
            //周推荐书
            book.setWeekRecommendedNumber(Integer.parseInt(list.get(11)));
            //设置图片地址
            book.setImg("https://www.23us.so/files/article/image/" + (i / 1000) + "/" + i + "/" + i + "s.jpg");
            //清空list
            list = null;
            list = RegexString(result, SYNOPSIS);
            //设置简介
            //将字符串写到文件里
            try {
                File dirFile = new File(ResourcePath.resourcePath + "/book/" + i);
                if (!dirFile.exists()) {
                    try {
                        //创建书的目录
                        dirFile.mkdirs();
                    } catch (Exception e) {
                        logger.error("创建文件目录失败");
                    }
                }
                File file = new File(ResourcePath.resourcePath + "/book/" + i + "/" + 0 + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(list.get(0).getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            book.setSynopsis(ResourcePath.resourcePath + "/book/" + i + "/" + 0 + ".txt");
            //把书籍都添加到图书列表中去
            books.add(book);
            logger.info("第" + i + "本书籍抓取成功");
        }
        logger.info("end--------------------------");
        return books;
    }

    /**
     * 获取小说目录及章节
     * id:主键
     * number:书号
     * name；章节名
     * url；章节地址
     *
     * @param start 书籍编号起始位置
     * @param end   书籍编号结束位置
     * @return
     */
    public static List<Catalogue> getPathMulu(int start, int end) {
        logger.info("start-------------------------------------");
        //创建图书目录列表
        List<Catalogue> catalogues = new ArrayList<Catalogue>();
        for (int i = start; i <= end; i++) {
            logger.info("开始抓取书籍编号为 " + i + " 的目录");
            //访问页面并获取页面内容
            String result = senGet("https://www.23us.so/files/article/html/1/" + i + "/index.html");
            if (result == null) {
                continue;
            }
            //截取html中关于文章目录解器地址信息
            List<String> names = RegexString(result, bookMu_Path1);
            //章节名索引
            int count = 1;
            //获取章节名和章节地址
            for (String name : names) {
                //创建一个图书目录
                Catalogue catalogue = new Catalogue();
                name = name.replaceAll("/0/", "/1/");
                List<String> href = RegexString(name, bookMu_Href);
                href.set(0, href.get(0).replaceAll("html/", "html/0"));
                //设置id
                catalogueId++;
                catalogue.setId(catalogueId);
                //小说章节原有在线地址
                catalogue.setUrl(href.get(0));
                List<String> title = RegexString(name, bookMu_Title);
                title.set(0, title.get(0).replaceAll("'", ""));
                title.set(0, title.get(0).replaceAll("html\">", ""));
                title.set(0, title.get(0).replaceAll("</a>", ""));
                //设置书名
                catalogue.setName(title.get(0));
                //设置书号
                catalogue.setNumber(i);
                //设置索引
                catalogue.setCount(count);
                count++;
                catalogues.add(catalogue);
            }
            logger.info("抓取成功，正在把" + names.size() + "章章节保存到文件");
            //url：url列表 i：书号
//            catalogueSaveFile(url,i);
        }
        logger.info("end-------------------------------------");
        return catalogues;
    }


    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                bookId = 0;
                catalogueId = 0;
                String className = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://172.31.147.213:3306/bam?rewriteBatchedStatements=true";
                String user = "root";
                String password = "yong12345";
                Connection connection = null;
                try {
                    Class.forName(className);
                    //获得一个Connection对象
                    connection = DriverManager.getConnection(url, user, password);
                    saveBookToSql(connection);
                    connection.close();
                } catch (Exception e) {
                    logger.error("数据库驱动加载失败：" + e.getMessage());
                }
            }
        };

        timer.schedule(task, 1000, 86400000);
    }

    /**
     * 把书籍和目录信息保存到数据库中
     *
     * @param connection
     */
    public static void saveBookToSql(Connection connection) {
        //每五十一组往数据库插入数据
        int start = 1;
        int end = 50;
        try {
            while (true) {
                //book数据
                List<Book> books = getBook(start, end);
                //目录数据
                List<Catalogue> catalogues = getPathMulu(start, end);
                //插入book数据
                insertBook(books, connection);
                //插入目录信息
                insertCatalogue(catalogues, connection);
                start += 50;
                end += 50;
                if (start >= 34000) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入book数据
     *
     * @param books
     * @param connection
     */
    public static void insertBook(List<Book> books, Connection connection) {
        try {
            //插入book数据
            String insertBookSql = "REPLACE INTO book(`id`,`number`,`category`,`name`,`author`,`status`,`collection`,`wordNumber`,`data`,`totalHits`,`monthlyClicks`,`weeklyClicks`,`totalRecommendedNumber`,`monthlyRecommendedNumber`,`weekRecommendedNumber`,`img`,`synopsis`)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //手动提交
            connection.setAutoCommit(false);
            PreparedStatement pst1 = connection.prepareStatement(insertBookSql);
            for (Book book : books) {
                pst1.setInt(1, book.getId());
                pst1.setInt(2, book.getNumber());
                pst1.setString(3, book.getCategory());
                pst1.setString(4, book.getName());
                pst1.setString(5, book.getAuthor());
                pst1.setString(6, book.getStatus());
                pst1.setInt(7, book.getCollection());
                pst1.setString(8, book.getWordNumber());
                pst1.setString(9, book.getData());
                pst1.setInt(10, book.getTotalHits());
                pst1.setInt(11, book.getMonthlyClicks());
                pst1.setInt(12, book.getWeeklyClicks());
                pst1.setInt(13, book.getTotalRecommendedNumber());
                pst1.setInt(14, book.getMonthlyRecommendedNumber());
                pst1.setInt(15, book.getWeekRecommendedNumber());
                pst1.setString(16, book.getImg());
                pst1.setString(17, book.getSynopsis());
                pst1.addBatch();
            }
            pst1.executeBatch();
            //批量插入到数据库
            connection.commit();
            pst1.clearBatch();
            pst1.close();
        } catch (Exception e) {
            logger.error("插入book数据失败" + e.getMessage());
        }
    }

    /**
     * 插入catalogue数据失败
     *
     * @param catalogues
     * @param connection
     */
    public static void insertCatalogue(List<Catalogue> catalogues, Connection connection) {
        //插入catalogue数据
        try {
            String insertCatalogueSql = "REPLACE INTO catalogue(`id`,`number`,`name`,`count`,`url`) VALUES(?,?,?,?,?)";
            PreparedStatement pst2 = connection.prepareStatement(insertCatalogueSql);
            //设置提交方式为手动提交
            connection.setAutoCommit(false);
            for (Catalogue catalogue : catalogues) {
                pst2.setInt(1, catalogue.getId());
                pst2.setInt(2, catalogue.getNumber());
                pst2.setString(3, catalogue.getName());
                pst2.setInt(4, catalogue.getCount());
                pst2.setString(5, catalogue.getUrl());
                pst2.addBatch();
            }
            pst2.executeBatch();
            //批量提交数据到数据库中
            connection.commit();
            pst2.clearBatch();
            pst2.close();
        } catch (Exception e) {
            logger.error("插入catalogue数据失败：" + e.getMessage());
        }
    }
}
