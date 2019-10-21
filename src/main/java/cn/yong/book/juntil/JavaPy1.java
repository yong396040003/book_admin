package cn.yong.book.juntil;

import cn.yong.book.pojo.Book;
import cn.yong.book.pojo.Catalogue;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.Executors.*;

/**
 * Description:
 * java爬虫，爬取地点顶点书店
 * Date: 21:39 2018/10/6
 *
 * @author yong
 * @see
 */
public class JavaPy1 {
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
    static Connection connection;
    /**
     * 每五十一组往数据库插入数据
     */
    static int start = 1;
    static int end = 50;
    static int start1 = 1;
    static int end1 = 50;
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
        List<String> list = new ArrayList<>();
        int j;
        //存储book信息
        List<Book> books = new ArrayList<>();
        logger.info("start-----------------------------");
        for (int i = start; i <= end; i++) {
            logger.info("开始抓取第" + i + "本书籍信息");
            Document document = null;
            try {
                j = i + 999;
                document = Jsoup.connect("https://www.x23us.com/book/" + j)
                        .userAgent("Mozilla")
                        .cookie("auth", "token")
                        .timeout(10000)
                        .get();
            } catch (IOException e) {
                logger.error("网页连接失败" + e.getMessage());
                e.printStackTrace();
                books.add(null);
                continue;
            }
            assert document != null;
            Element element = document.body();
            Element elementById = element.getElementById("content");
            //书名
            Elements bookName = elementById.select("h1");
            //img图片地址
            Elements elements = elementById.select("div.fl");
            Elements img = elements.get(0).select("img");
            String imgUrl = img.attr("src");
            imgUrl = "https://www.x23us.com" + imgUrl;
            //书籍后面所有信息
            Elements bookList = elements.get(1).select("td");
            for (Element book : bookList) {
                list.add(book.text().replaceAll(" ", ""));
            }
            //简介
            Element synopsis = elementById.select("dd").get(3);
            Element synopsis1 = synopsis.select("p").get(1);
            //初始化一本书
            Book book = new Book();
            //设置书号
            book.setNumber(j);
            book.setName(bookName.text().replaceAll(" 全文阅读", ""));
            //小说类型
            switch (list.get(0)) {
                case "武侠修真":
                    book.setCategory("武侠仙侠");
                    break;
                case "侦探推理":
                    book.setCategory("恐怖灵异");
                    break;
                case "网游小说":
                    book.setCategory("网游竞技");
                    break;
                case "散文诗词":
                    book.setCategory("女生小说");
                    break;
                case "其他类型":
                    book.setCategory("其他小说");
                default:
                    book.setCategory(list.get(0));
                    break;
            }
            //作者
            book.setAuthor(list.get(1));
            //状态
            switch (list.get(2)) {
                case "已完成":
                    book.setStatus("完本");
                    break;
                default:
                    book.setStatus(list.get(2));
                    break;
            }

            //收藏
            if (list.get(3).length() > 8) {
                String collection = list.get(3).substring(list.get(3).length() - 8);
                System.err.println(collection);
                book.setCollection(Integer.parseInt(collection));
            } else {
                book.setCollection(Integer.parseInt(list.get(3)));
            }
            //字数
            book.setWordNumber(list.get(4));
            //更新时间
            book.setData(list.get(5));
            //总点击数
            if (list.get(6).length() > 8) {
                String wordNumber = list.get(6).substring(list.get(3).length() - 8);
                System.err.println(wordNumber);
                book.setTotalHits(Integer.parseInt(wordNumber));
            } else {
                book.setTotalHits(Integer.parseInt(list.get(6)));
            }
            //月点击数
            if (list.get(7).length() > 8) {
                String wordNumber = list.get(7).substring(list.get(3).length() - 8);
                System.err.println(wordNumber);
                book.setMonthlyClicks(Integer.parseInt(wordNumber));
            } else {
                book.setMonthlyClicks(Integer.parseInt(list.get(7)));
            }
            //周点击数
            if (list.get(8).length() > 8) {
                String wordNumber = list.get(8).substring(list.get(3).length() - 8);
                System.err.println(wordNumber);
                book.setWeeklyClicks(Integer.parseInt(wordNumber));
            } else {
                book.setWeeklyClicks(Integer.parseInt(list.get(8)));
            }
            //总推荐数
            if (list.get(9).length() > 8) {
                String wordNumber = list.get(8).substring(list.get(3).length() - 8);
                System.err.println(wordNumber);
                book.setTotalRecommendedNumber(Integer.parseInt(wordNumber));
            } else {
                book.setTotalRecommendedNumber(Integer.parseInt(list.get(9)));
            }
            //月推荐数
            if (list.get(10).length() > 8) {
                String wordNumber = list.get(10).substring(list.get(3).length() - 8);
                System.err.println(wordNumber);
                book.setMonthlyRecommendedNumber(Integer.parseInt(wordNumber));
            } else {
                book.setMonthlyRecommendedNumber(Integer.parseInt(list.get(10)));
            }
            //周推荐书
            if (list.get(11).length() > 8) {
                String wordNumber = list.get(11).substring(list.get(3).length() - 8);
                System.err.println(wordNumber);
                book.setWeekRecommendedNumber(Integer.parseInt(wordNumber));
            } else {
                book.setWeekRecommendedNumber(Integer.parseInt(list.get(11)));
            }
            //设置图片地址
            book.setImg(imgUrl);
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
                outputStream.write(synopsis1.text().getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            book.setSynopsis(ResourcePath.resourcePath + "/book/" + i + "/" + 0 + ".txt");
            //把书籍都添加到图书列表中去
            books.add(book);
            logger.error(book.toString());
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
        String URL = null;
        int j;
        //创建图书目录列表
        List<Catalogue> catalogues = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            logger.info("开始抓取书籍编号为 " + i + " 的目录");
            //访问页面并获取页面内容
            Document document = null;
            try {
                j = 999 + i;
                URL = "https://www.x23us.com/html/" + j / 1000 + "/" + j + "/";
                document = Jsoup.connect(URL)
                        .userAgent("Mozilla")
                        .cookie("auth", "token")
                        .timeout(10000)
                        .get();
            } catch (IOException e) {
                logger.error("网页连接失败" + e.getMessage());
                e.printStackTrace();
                catalogues.add(null);
                continue;
            }
            assert document != null;
            Element element = document.body();
            Element element1 = element.getElementById("at");
            Elements elements = element1.getElementsByClass("L");
            //章节名索引
            int count = 1;
            for (Element element2 : elements) {
                //章节地址
                String url = element2.select("a[href]").attr("href");
                url = URL + url;
                //章节名
                String name = element2.text();
                if (" ".equals(name)) {
                    break;
                }
                //创建一个图书目录
                Catalogue catalogue = new Catalogue();
                //设置章节名
                catalogue.setName(name);
                //设置书号
                catalogue.setNumber(j);
                //设置索引
                catalogue.setCount(count);
                //设置url
                catalogue.setUrl(url);
                count++;
                catalogues.add(catalogue);
            }
            logger.info("抓取成功，正在把" + elements.size() + "章章节保存到文件");
        }
        logger.info("end-------------------------------------");
        return catalogues;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("你的起始位置:");
        start = scanner.nextInt();
        end = start + 50;
        System.out.println("你的间距为:" + end);
        System.out.println("目标:" + start + " 到 " + 100000);

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                connection = null;
                try {
                    //获得一个Connection对象
                    connection = DruidUtil.getConnection();
                    saveBookToSql();
                    DruidUtil.close(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(task, 1000, 86400000);
    }

    /**
     * 把书籍和目录信息保存到数据库中
     */
    public static void saveBookToSql() {
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
            if (start >= 100000) {
                break;
            }
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
            String insertBookSql = "REPLACE INTO book(`number`,`category`,`name`,`author`,`status`,`collection`,`wordNumber`,`data`,`totalHits`,`monthlyClicks`,`weeklyClicks`,`totalRecommendedNumber`,`monthlyRecommendedNumber`,`weekRecommendedNumber`,`img`,`synopsis`)" +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //手动提交
            connection.setAutoCommit(false);
            PreparedStatement pst1 = connection.prepareStatement(insertBookSql);
            for (Book book : books) {
                if (book == null) {
                    continue;
                }
                pst1.setInt(1, book.getNumber());
                pst1.setString(2, book.getCategory());
                pst1.setString(3, book.getName());
                pst1.setString(4, book.getAuthor());
                pst1.setString(5, book.getStatus());
                pst1.setInt(6, book.getCollection());
                pst1.setString(7, book.getWordNumber());
                pst1.setString(8, book.getData());
                pst1.setInt(9, book.getTotalHits());
                pst1.setInt(10, book.getMonthlyClicks());
                pst1.setInt(11, book.getWeeklyClicks());
                pst1.setInt(12, book.getTotalRecommendedNumber());
                pst1.setInt(13, book.getMonthlyRecommendedNumber());
                pst1.setInt(14, book.getWeekRecommendedNumber());
                pst1.setString(15, book.getImg());
                pst1.setString(16, book.getSynopsis());
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
            String insertCatalogueSql = "REPLACE INTO catalogue(`number`,`name`,`count`,`url`) VALUES(?,?,?,?)";
            PreparedStatement pst2 = connection.prepareStatement(insertCatalogueSql);
            //设置提交方式为手动提交
            connection.setAutoCommit(false);
            for (Catalogue catalogue : catalogues) {
                if (catalogue == null) {
                    continue;
                }
                pst2.setInt(1, catalogue.getNumber());
                pst2.setString(2, catalogue.getName());
                pst2.setInt(3, catalogue.getCount());
                pst2.setString(4, catalogue.getUrl());
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

    /**
     * 获取并插入操作
     */
    public static class InsertBookThread implements Runnable {

        @Override
        public void run() {
            //book数据
            List<Book> books = getBook(start, end);
            //插入book数据
            insertBook(books, connection);
            start += 50;
            end += 50;
        }
    }

    /**
     * 获取并插入操作
     */
    public static class InsertCatalogueThread implements Runnable {

        @Override
        public void run() {
            //book数据
            List<Catalogue> catalogues = getPathMulu(start1, end1);
            //插入book数据
            insertCatalogue(catalogues, connection);
            start1 += 50;
            end1 += 50;
        }
    }
}
