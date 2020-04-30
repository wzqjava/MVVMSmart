package com.wzq.sample.bean;

/**
 * <p>作者：wzq<p>
 * <p>创建时间：2019-11-13<p>
 * <p>文件描述：<p>
 *
 */
public class User {
    /**
     * userType
     * 0:学生
     * 1:家长
     * 2:教师
     * 3:代理商
     * 4:后台
     */
    /**
     * nickname : 张三
     * userType : 0
     * headerUrl : https://stepover-example.oss-cn-beijing.aliyuncs.com/image/0.jpg
     * coverUrl : https://stepover-example.oss-cn-beijing.aliyuncs.com/image/cover.jpg
     * currentSkillName : 四年级
     * nextSkillName : 五年级
     * skillUpgradeProcess : 60
     * readTime : 10012
     * goldNum : 1123
     * runningDays : 10
     * surpassPercent : 70
     * unreadMailNum : 3
     * unreadMessageNum : 12
     * unreadReportNum : 1
     */

    private String nickname;
    private int userType;
    private String headerUrl;
    private String coverUrl;
    private String currentSkillName;
    private String nextSkillName;
    private int skillUpgradeProcess;
    private int readTime;
    private int goldNum;
    private int runningDays;
    private int surpassPercent;
    private int unreadMailNum;
    private int unreadReportNum;
    private int unreadMessageNum;
    private int voucherNumber;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCurrentSkillName() {
        return currentSkillName;
    }

    public void setCurrentSkillName(String currentSkillName) {
        this.currentSkillName = currentSkillName;
    }

    public String getNextSkillName() {
        return nextSkillName;
    }

    public void setNextSkillName(String nextSkillName) {
        this.nextSkillName = nextSkillName;
    }

    public int getSkillUpgradeProcess() {
        return skillUpgradeProcess;
    }

    public void setSkillUpgradeProcess(int skillUpgradeProcess) {
        this.skillUpgradeProcess = skillUpgradeProcess;
//        notifyPropertyChanged(com.reading.win.personal.BR.currentSkillName);
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(int goldNum) {
        this.goldNum = goldNum;
    }

    public int getRunningDays() {
        return runningDays;
    }

    public void setRunningDays(int runningDays) {
        this.runningDays = runningDays;
    }

    public int getSurpassPercent() {
        return surpassPercent;
    }

    public void setSurpassPercent(int surpassPercent) {
        this.surpassPercent = surpassPercent;
    }

    public int getUnreadMailNum() {
        int formatNum = 0;
        if (unreadMailNum < 0) {
            formatNum = 0;
        } else {
            formatNum = unreadMailNum;
        }
        return formatNum;
    }

    public void setUnreadMailNum(int unreadMailNum) {
        this.unreadMailNum = unreadMailNum;
    }

    public int getUnreadMessageNum() {
        int formatNum ;
        if (unreadMessageNum < 0) {
            formatNum = 0;
        } else {
            formatNum = unreadMessageNum;
        }
        return formatNum;
    }

    public void setUnreadMessageNum(int unreadMessageNum) {
        this.unreadMessageNum = unreadMessageNum;
    }

    public int getUnreadReportNum() {
        int formatNum = 0;
        if (unreadReportNum < 0) {
            formatNum = 0;
        } else {
            formatNum = unreadReportNum;
        }
//        MLog.e("unreadReportNum:" + unreadReportNum);
//        MLog.e("formatNum:" + formatNum);
        return formatNum;
    }

    public void setUnreadReportNum(int unreadReportNum) {
        this.unreadReportNum = unreadReportNum;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", userType=" + userType +
                ", headerUrl='" + headerUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", currentSkillName='" + currentSkillName + '\'' +
                ", nextSkillName='" + nextSkillName + '\'' +
                ", skillUpgradeProcess=" + skillUpgradeProcess +
                ", readTime=" + readTime +
                ", goldNum=" + goldNum +
                ", runningDays=" + runningDays +
                ", surpassPercent=" + surpassPercent +
                ", unreadMailNum=" + unreadMailNum +
                ", unreadMessageNum=" + unreadMessageNum +
                ", unreadReportNum=" + unreadReportNum +
                '}';
    }


    public int getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(int voucherNumber) {
        this.voucherNumber = voucherNumber;
    }
}
