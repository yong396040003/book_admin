package cn.yong.book.pojo;

/**
 * Description:
 * Date: 0:46 2019/1/4
 *
 * @author yong
 * @see
 */
public class User {
    /**
     * userId
     */
    private int userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 用户状态
     */
    private String userStatus;
    /**
     * 用户等级
     */
    private String userGrade;
    /**
     * 最后登陆时间
     */
    private String userEndTime;
    /**
     * 用户电话
     */
    private String userPhone;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getUserEndTime() {
        return userEndTime;
    }

    public void setUserEndTime(String userEndTime) {
        this.userEndTime = userEndTime;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", userGrade='" + userGrade + '\'' +
                ", userEndTime='" + userEndTime + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
