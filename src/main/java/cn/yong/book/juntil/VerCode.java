package cn.yong.book.juntil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author yong
 * @since 1.0.0
 */
public class VerCode {
    /**
     * 验证码的宽和高
     */
    private int width,height;

    /**
     * 验证码字典
     */
    private String code;

    /**
     * 验证码个数
     */
    private int num;

    /**
     * 随机在字典中取验证码
     */
    private static final Random random=new Random();

    /**
     * 单例模式
     */
    private static VerCode verCode;

    /**
     * 构造私有
     */
    private VerCode(){
        code="0123456789qwertyuiopasdfghjklzxcvbnm";
        num=4;

        //默认验证码width
        this.width = 100;
        this.height = 40;
    }

    /**
     * 单列模式获取类对象
     * @return
     */
    public static VerCode getInstance(){
        if (verCode==null) {
            verCode = new VerCode();
        }
        return verCode;
    }

    public void set(int width,int height,String code,int num){
        this.width=width;
        this.height=height;
        this.code=code;
        this.num=num;
    }

    public void set(int width,int height){
        this.width=width;
        this.height=height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 随机生成验证码字符
     * @return
     */
    public String generationCheckCode(){
        StringBuffer cc=new StringBuffer();
        for (int i=0;i<num;i++){
            cc.append(code.charAt(random.nextInt(code.length())));
        }
        return cc.toString();
    }

    /**
     * 画验证码图
     * @param checkcode
     * @return
     */
    public BufferedImage generationCheckImg(String checkcode){

        //创建一个图片对象
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取图片对象的画笔
        Graphics2D graphics2D=image.createGraphics();

        //保存画笔原来的颜色
        Color color=graphics2D.getColor();

        //设置颜色为白色，填充整个举行。相当于背景颜色
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0,0,width,height);

        //画一个矩形边框
        graphics2D.setColor(Color.blue);
        graphics2D.drawRect(0,0,width-1,height-1);

        //设置验证码字体格式
        Font font=new Font("宋体",Font.BOLD+Font.ITALIC,(int)(height*0.8));
        graphics2D.setFont(font);

        //加一些点
        for (int i=0;i<(width+height);i++){
            graphics2D.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            graphics2D.drawOval(random.nextInt(width),random.nextInt(height),1,1);
        }

        //加一些线
        for (int i=0;i<(int)(num/2);i++){
            graphics2D.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            graphics2D.drawLine(0,random.nextInt(height),width,random.nextInt(height));
        }

        //画出验证码
        for (int i=0;i<num;i++){
            AffineTransform affine = new AffineTransform();
            //affine.setToRotation(Math.PI / 90 * random.nextInt(360), (width / num) * i+(height*0.8)/2-10, height/2);
            graphics2D.setTransform(affine);
            graphics2D.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            graphics2D.drawString(String.valueOf(checkcode.charAt(i)),i*(width/num)+4,(int)(height*0.8));
        }

        //把画笔原本的颜色返回
        graphics2D.setColor(color);

        return image;
    }
}
