package cn.yong.book.juntil;

import cn.yong.book.pojo.Book;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * java读取excel文件(.xls,xlsx,csv)工具类
 * Date: 16:20 2019/7/8
 *
 * @author yong
 * @see
 */
public class ReadExcel {
    /**
     * 解析xls
     */
    public static List<Book> xls(InputStream inputStream) throws IOException {
        List<Book> bookList = new ArrayList<>();
        //解析工作簿
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        //解析工作表
        int size = workbook.getNumberOfSheets();
        //循环处理读取的每一个工作表的数据
        for (int i = 0; i < size; i++) {
            HSSFSheet hssfSheet = workbook.getSheetAt(i);
            //获取有效行数
            int rowCount = hssfSheet.getLastRowNum();
            for (int j = 0; j <= rowCount; j++) {
                //获取每一行
                HSSFRow row = hssfSheet.getRow(j);
                if (row == null) {
                    break;
                }
                Book book = new Book();
                //获取每一行有效列数
                int colCount = row.getLastCellNum();
                for (int k = 0; k < colCount; k++) {
                    String par;
                    HSSFCell cell = row.getCell(k);
                    if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    }
                    par = cell.getStringCellValue();
                    setBookPar(k, book, par);
                }

                bookList.add(book);
            }
        }

        workbook.close();

        return bookList;
    }

    /**
     * 解析xlsx
     */
    public static List<Book> xlsx(InputStream inputStream) throws IOException {
        List<Book> bookList = new ArrayList<>();
        //解析工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        //解析工作表
        int size = workbook.getNumberOfSheets();
        //循环处理读取的每一个工作表的数据
        for (int i = 0; i < size; i++) {
            XSSFSheet hssfSheet = workbook.getSheetAt(i);
            //获取有效行数
            int rowCount = hssfSheet.getLastRowNum();
            for (int j = 1; j <= rowCount; j++) {
                //获取每一行
                XSSFRow row = hssfSheet.getRow(j);
                if (row == null) {
                    break;
                }
                //获取每一行有效列数
                int colCount = row.getLastCellNum();
                Book book = new Book();
                for (int k = 0; k < colCount; k++) {
                    String par;
                    XSSFCell cell = row.getCell(k);
                    if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    }
                    par = cell.getStringCellValue();
                    setBookPar(k, book, par);
                }
                bookList.add(book);
            }
        }

        workbook.close();

        return bookList;
    }

    /**
     * 解析csv
     */
    public static List<Book> csv(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"GBK");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        List<Book> bookList = new ArrayList<>();
        Boolean isOne = true;
        while ((str = bufferedReader.readLine()) != null) {
            if (isOne) {
                isOne = false;
                continue;
            }
            Book book = new Book();
            String[] par = str.split(",");
            for (int i = 0; i < par.length; i++) {
                setBookPar(i, book, par[i]);
            }
            bookList.add(book);
        }
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();

        System.err.println(bookList.toString());

        return bookList;
    }

    public static void setBookPar(int k, Book book, String par) {
        switch (k) {
            case 0:
                book.setNumber(Integer.parseInt(par));
                break;
            case 1:
                book.setName(par);
                break;
            case 2:
                book.setImg(par);
                break;
            case 3:
                book.setAuthor(par);
                break;
            case 4:
                book.setStatus(par);
                break;
            case 5:
                book.setWordNumber(par);
                break;
            case 6:
                book.setData(par);
                break;
            case 7:
                book.setCategory(par);
                break;
            case 8:
                book.setCollection(Integer.parseInt(par));
                break;
            case 9:
                book.setTotalHits(Integer.parseInt(par));
                break;
            case 10:
                book.setMonthlyClicks(Integer.parseInt(par));
                break;
            case 11:
                book.setWeeklyClicks(Integer.parseInt(par));
                break;
            case 12:
                book.setTotalRecommendedNumber(Integer.parseInt(par));
                break;
            case 13:
                book.setMonthlyRecommendedNumber(Integer.parseInt(par));
                break;
            case 14:
                book.setWeekRecommendedNumber(Integer.parseInt(par));
                break;
            case 15:
                book.setSynopsis(par);
                break;
            default:
                break;
        }
    }
}
