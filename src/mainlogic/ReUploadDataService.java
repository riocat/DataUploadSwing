package mainlogic;

import entity.DataStruct;
import entity.Info;
import util.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by rio on 2018/10/24.
 */
public class ReUploadDataService {

    private DataStruct dataStruct;

    private JTextArea textArea;

    /**
     * 要补传的数据队列
     */
    private ConcurrentLinkedQueue<Info> cInfoQueue = new ConcurrentLinkedQueue<Info>();

    // 发送的数据包编号
    private int num = 0;

    public static boolean runflag = true;

    /**
     * 实时转发套接字连接
     */
    public static Socket socket = null;

    /**
     * 实时转发输入流
     */
    public static InputStream reader;

    /**
     * 实时转发输出流
     */
    public static PrintStream ps;

    /**
     * 补发套接字连接
     */
    public static Socket socketc = null;
    /**
     * 补发输入流
     */
    public static InputStream readerc;
    /**
     * 补发输出流
     */
    public static PrintStream psc;

    private int sleepmillis = 900000;

    private int retrytime = 10000;

    private int times = 1000;

    // 北京服务器分配的ID号
    private static String serverid = "10000000058";

    public int cInfoNum = 0;

    public ReUploadDataService() {

    }

    public ReUploadDataService(JTextArea textArea, DataStruct dataStruct) {
        this.dataStruct = dataStruct;
        this.textArea = textArea;
    }

    public void reUploadData() {
        Map<String, Object> dataMap = dataStruct.getDataMap();

        String BjIp = (String) dataMap.get("BjIp");
        Integer BjPort = (Integer) dataMap.get("BjPort");
        String Cpterminalid = (String) dataMap.get("Cpterminalid");
        Date Startinfodatetime = (Date) dataMap.get("Startinfodatetime");
        Date Endinfodatetime = (Date) dataMap.get("Endinfodatetime");
        String DBUrl = (String) dataMap.get("DBUrl");
        String tableName = (String) dataMap.get("tableName");

        TextAreaScrollUtil.TextAreaScroll(textArea, "启动北京数据服务商数据上传线程：服务器的IP：" + BjIp
                + "  PORT： " + BjPort);

        TextAreaScrollUtil.TextAreaScroll(textArea, "启用往北京补传数据信息，车台号："
                + Cpterminalid
                + " 起始时间："
                + Startinfodatetime
                .toString() + " 结束时间："
                + Endinfodatetime.toString());

        ResultSet rs = null;
        PreparedStatement pscgps = null;
        Connection conn = null;
        SimpleDateFormat dtformatNo808 = new SimpleDateFormat(
                "ddMMyyHHmmss");

        if (Cpterminalid != null) {
            try {
                conn = DBPool.getInstance(DBUrl).getConnection();
                System.out.println(conn.toString());
                TextAreaScrollUtil.TextAreaScroll(textArea, "获取GPS_LOG数据库连接，向北京特定客户补发数据");

                pscgps = conn
                        .prepareStatement("select * from "
                                + tableName
                                + " where terminalid = ? and infodatetime >= ? and infodatetime <= ?  "
                                + "ORDER BY infodatetime");

                pscgps.setString(1, Cpterminalid);
                pscgps.setTimestamp(2, new java.sql.Timestamp(
                        Startinfodatetime.getTime()));
                pscgps.setTimestamp(3, new java.sql.Timestamp(
                        Endinfodatetime.getTime()));

                System.out.println(pscgps.toString());

                rs = pscgps.executeQuery();

                while (rs.next()) {
                    Info info = new Info();
                    try {
                        // 数据格式解析
                        info.setXX(SYSTEMPARA.P808PROTOCOL);
                        info.setYYYYYYYYYY(Cpterminalid);
                        info.setType("V1");
                        Calendar ca = Calendar.getInstance();
                        ca.setTime(rs.getTimestamp("infodatetime"));
                        ca.add(Calendar.HOUR_OF_DAY, -8);
                        String dt = dtformatNo808.format(ca
                                .getTime());
                        info.setHHMMSS(dt.substring(6));
                        info.setS("A");
                        info.setD("N");
                        info.setG("E");
                        info.setLatitude(String.valueOf(rs
                                .getDouble("latitude") * 100));
                        info.setLongitude(String.valueOf(rs
                                .getDouble("longitude") * 100));
                        info.setSpeed(String.valueOf(rs.getDouble("speed")
                                / SYSTEMPARA.NM2KM));
                        info.setDirection(rs.getString("direction"));
                        info.setDDMMYY(dt.substring(0, 6));
                        info.setVehicle_statusstr("FFFFFBFF");
                        info.setData("*" + info.getXX() + ","
                                + info.getYYYYYYYYYY() + ","
                                + info.getType() + "," + info.getHHMMSS()
                                + "," + info.getS() + ","
                                + info.getLatitude() + "," + info.getD()
                                + "," + info.getLongitude() + ","
                                + info.getG() + "," + info.getSpeed() + ","
                                + info.getDirection() + ","
                                + info.getDDMMYY() + ","
                                + info.getVehicle_statusstr() + "#");
                        // 随意填的，没什么用，但是为了保持统一性留下
                        info.setLiuliang(68);
                        cInfoQueue.add(info);
                    } catch (Exception e) {
                        TextAreaScrollUtil.TextAreaScroll(textArea, "获取补传数据出现错误");
                    }
                }

                TextAreaScrollUtil.TextAreaScroll(textArea, "补传数据包总数cInfoQueue::::" + cInfoQueue.size());
            } catch (SQLException e) {
                e.printStackTrace();
                TextAreaScrollUtil.TextAreaScroll(textArea, "UpdataToBJServer 36: ");
            } catch (Exception e) {
                TextAreaScrollUtil.TextAreaScroll(textArea, e.getMessage());
            } finally {
                DBPool.closeDB(conn, pscgps, rs);
                TextAreaScrollUtil.TextAreaScroll(textArea, "归还GPS_LOG数据库连接，向北京特定客户补发数据");
            }
        }

        try {
            initSocket(dataMap);

            sendToBeijing();

        } catch (IOException ex) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "创建UpdataToBJServer的Socket时候出现IO异常");
        } catch (Exception ex) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "创建UpdataToBJServer的Socket时候未知异常");
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    socket = null;
                    TextAreaScrollUtil.TextAreaScroll(textArea, "关闭UpdataToBJServer的Socket");
                }
            } catch (IOException e) {
                TextAreaScrollUtil.TextAreaScroll(textArea, "关闭UpdataToBJServer的Socket时候出现异常");
            }
        }
    }


    /**
     * 初始化Socket信息
     *
     * @throws IOException
     */
    public void initSocket(Map<String, Object> dataMap) throws IOException {
        try {
            socket = new Socket("158.161.12.14", 33211);

            // 这个必须写上，如果不写，对于那种只连接，没有发送任何TCP数据包的线程就没法立刻结束了。
            // 设置关闭socket的时候等待FIN_ACK的时间，第一参数表示是否等待，第二个表示等待多久（单位：百分之一秒）
            socket.setSoLinger(true, 0);
            // 设置端口重用，表示以前使用的连接端口是否可用
            socket.setReuseAddress(true);
            // 设置超时连接
            socket.setSoTimeout(sleepmillis);
            reader = socket.getInputStream();// 得到输入流

            ps = new PrintStream(socket.getOutputStream());// 得到输出流
        } catch (IOException ex) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "创建UpdataToBJServer的实时转发Socket时候出现IO异常");
            throw ex;
        }
        try {
            socketc = new Socket("158.161.12.14", 33211);
            // 这个必须写上，如果不写，对于那种只连接，没有发送任何TCP数据包的线程就没法立刻结束了。
            // 设置关闭socket的时候等待FIN_ACK的时间，第一参数表示是否等待，第二个表示等待多久（单位：百分之一秒）
            socketc.setSoLinger(true, 0);
            // 设置端口重用，表示以前使用的连接端口是否可用
            socketc.setReuseAddress(true);
            // 设置超时连接
            socketc.setSoTimeout(sleepmillis);
            readerc = socketc.getInputStream();// 得到输入流

            psc = new PrintStream(socketc.getOutputStream());// 得到输出流
        } catch (IOException ex) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "创建UpdataToBJServer的补传Socket时候出现IO异常");
            throw ex;
        }

    }

    /**
     * @return the num
     */
    public int getNum() {
        if (num > 1000000) {
            num = 0;
        }
        return num++;
    }

    /**
     * 根据V结构生成一个北京的GPS数据包
     *
     * @return
     */
    private byte[] buildBJPackage(String terminlid, Info info) {
        byte[] bjpackage = new byte[61];
        bjpackage[0] = (byte) 0xAA;
        bjpackage[1] = (byte) 0x55;
        bjpackage[2] = (byte) 0x05;
        bjpackage[3] = (byte) 0x00;
        // byte[] tempTerminalid = info.getYYYYYYYYYY().getBytes();
        if (terminlid.length() > 10) {
            terminlid = terminlid.substring(terminlid.length() - 10);
        }
        byte[] tempTerminalid = terminlid.getBytes();
        bjpackage[4] = (byte) 0x30;
        int i = 1;
        for (byte b : tempTerminalid) {
            bjpackage[4 + i] = b;
            i++;
        }
        int numtemp = getNum();
        bjpackage[15] = (byte) ((numtemp >> 24) & 0xFF);
        bjpackage[16] = (byte) ((numtemp >> 16) & 0xFF);
        bjpackage[17] = (byte) ((numtemp >> 8) & 0xFF);
        bjpackage[18] = (byte) (numtemp & 0xFF);
        bjpackage[19] = (byte) 0x00;
        bjpackage[20] = (byte) 0x00;
        bjpackage[21] = (byte) 0x00;
        bjpackage[22] = (byte) 0x22;
        bjpackage[23] = (byte) 0x00;
        bjpackage[24] = (byte) 0x3B;
        bjpackage[25] = (byte) 0x20;
        bjpackage[26] = (byte) ((Byte.parseByte(info.getDDMMYY()
                .substring(4, 5)) << 4) + Byte.parseByte(info.getDDMMYY()
                .substring(5, 6)));
        bjpackage[27] = (byte) ((Byte.parseByte(info.getDDMMYY()
                .substring(2, 3)) << 4) + Byte.parseByte(info.getDDMMYY()
                .substring(3, 4)));
        bjpackage[28] = (byte) ((Byte.parseByte(info.getDDMMYY()
                .substring(0, 1)) << 4) + Byte.parseByte(info.getDDMMYY()
                .substring(1, 2)));
        bjpackage[29] = (byte) ((Byte.parseByte(info.getHHMMSS()
                .substring(0, 1)) << 4) + Byte.parseByte(info.getHHMMSS()
                .substring(1, 2)));
        bjpackage[30] = (byte) ((Byte.parseByte(info.getHHMMSS()
                .substring(2, 3)) << 4) + Byte.parseByte(info.getHHMMSS()
                .substring(3, 4)));
        bjpackage[31] = (byte) ((Byte.parseByte(info.getHHMMSS()
                .substring(4, 5)) << 4) + Byte.parseByte(info.getHHMMSS()
                .substring(5, 6)));
        bjpackage[32] = (byte) 0x00;
        if (info.getD().equals("S")) {
            bjpackage[32] |= (byte) 0x80;
        }
        if (info.getD().equals("W")) {
            bjpackage[32] |= (byte) 0x40;
        }
        // 纬度
        String templatitude = FormatLL.formatLLForUploadToBJ(info
                .getLatituded());
        int templen = templatitude.length();
        for (int h = 0; h < 8 - templen; h++) {
            templatitude = "0" + templatitude;
        }
        // 经度
        String templongitude = FormatLL.formatLLForUploadToBJ(info
                .getLongituded());
        templen = templongitude.length();
        for (int h = 0; h < 9 - templen; h++) {
            templongitude = "0" + templongitude;
        }
        bjpackage[32] |= Byte.valueOf(templatitude.substring(0, 1));
        bjpackage[33] = (byte) ((Byte.valueOf(templatitude.substring(1, 2)) << 4) + Byte
                .valueOf(templatitude.substring(2, 3)));
        bjpackage[34] = (byte) ((Byte.valueOf(templatitude.substring(3, 4)) << 4) + Byte
                .valueOf(templatitude.substring(4, 5)));
        bjpackage[35] = (byte) ((Byte.valueOf(templatitude.substring(5, 6)) << 4) + Byte
                .valueOf(templatitude.substring(6, 7)));
        bjpackage[36] = (byte) ((Byte.valueOf(templatitude.substring(7, 8)) << 4) + Byte
                .valueOf(templongitude.substring(0, 1)));
        bjpackage[37] = (byte) ((Byte.valueOf(templongitude.substring(1, 2)) << 4) + Byte
                .valueOf(templongitude.substring(2, 3)));
        bjpackage[38] = (byte) ((Byte.valueOf(templongitude.substring(3, 4)) << 4) + Byte
                .valueOf(templongitude.substring(4, 5)));
        bjpackage[39] = (byte) ((Byte.valueOf(templongitude.substring(5, 6)) << 4) + Byte
                .valueOf(templongitude.substring(6, 7)));
        bjpackage[40] = (byte) ((Byte.valueOf(templongitude.substring(7, 8)) << 4) + Byte
                .valueOf(templongitude.substring(8, 9)));
        String speedtemp = String
                .valueOf((int) (Double.valueOf(info.getSpeed())
                        * SYSTEMPARA.NM2KM * 10));
        templen = speedtemp.length();
        for (int h = 0; h < 4 - templen; h++) {
            speedtemp = "0" + speedtemp;
        }
        bjpackage[41] = (byte) ((Byte.valueOf(speedtemp.substring(0, 1)) << 4) + Byte
                .valueOf(speedtemp.substring(1, 2)));
        bjpackage[42] = (byte) ((Byte.valueOf(speedtemp.substring(2, 3)) << 4) + Byte
                .valueOf(speedtemp.substring(3, 4)));
        String directtemp = String
                .valueOf(Integer.parseInt(info.getDirection()) * 10);
        templen = directtemp.length();
        for (int h = 0; h < 4 - templen; h++) {
            directtemp = "0" + directtemp;
        }
        bjpackage[43] = (byte) ((Byte.valueOf(directtemp.substring(0, 1)) << 4) + Byte
                .valueOf(directtemp.substring(1, 2)));
        bjpackage[44] = (byte) ((Byte.valueOf(directtemp.substring(2, 3)) << 4) + Byte
                .valueOf(directtemp.substring(3, 4)));
        bjpackage[45] = 0x00;
        bjpackage[46] = 0x00;
        bjpackage[47] = 0x00;
        bjpackage[48] = 0x00;
        bjpackage[49] = 0x00;
        short[] Vehicle_status = info.getVehicle_status();
        // 紧急报警/劫警
        if ((Vehicle_status[3] & 0x02) != 0x02) {
            bjpackage[49] += 0x01;
        }
        // 超速报警
        if ((Vehicle_status[3] & 0x04) != 0x04) {
            bjpackage[49] += 0x02;
        }
        // 区域报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[49] += 0x01;
        // }
        // 设备故障报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[49] += 0x01;
        // }
        // 求助报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[49] += 0x01;
        // }
        // 防盗报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[49] += 0x01;
        // }
        // 偏航报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[49] += 0x01;
        // }
        // GPS数据未定位
        if (!info.getS().equals("A")) {
            bjpackage[49] += 0x80;
        }
        bjpackage[50] = 0x00;
        // 车机电源断电报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[50] += 0x01;
        // }
        // 超时行使报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[50] += 0x01;
        // }
        // 超时停车报警
        // if((Vehicle_status[3] & 0x04) == 0x04){
        // bjpackage[50] += 0x01;
        // }
        bjpackage[51] = 0x00;
        bjpackage[52] = 0x00;
        // 开、熄火（ACC）0bit位 0为熄火1为开火
        if ((Vehicle_status[2] & 0x04) == 0x04) {
            bjpackage[52] += 0x01;
        }
        // 货箱门开关状态 1bit位 0为车门关1为开
        if ((Vehicle_status[2] & 0x01) != 0x01) {
            bjpackage[52] += 0x02;
        }
        // GPRS信号状态
        if ((Vehicle_status[0] & 0x04) != 0x04) {
            bjpackage[52] += 0x04;
        } else {
            bjpackage[52] += 0x0C;
        }
        bjpackage[53] = 0x00;
        bjpackage[54] = 0x00;
        bjpackage[55] = 0x00;
        bjpackage[56] = 0x00;
        bjpackage[57] = (byte) 0xFF;
        bjpackage[58] = (byte) 0xFF;
        bjpackage[59] = (byte) 0xFF;
        bjpackage[60] = (byte) 0xFF;
        return bjpackage;
    }

    /**
     * 将一个byte数组通过socket发送出去
     *
     * @param info 要被发送的字节数组
     */
    public void sendBytes(String terminalid, Info info) {
        try {
            if (socket != null && !socket.isClosed() && ps != null) {
                byte[] msg = buildBJPackage(terminalid, info);
                ps.write(msg, 0, msg.length); // 发送命令
                ps.flush();
            }
        } catch (NumberFormatException e) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "在UpdateToBJServer的sendBytes 278函数出现异常  Direction："
                    + info.getDirection());
        } catch (Exception e) {
            TextAreaScrollUtil.TextAreaScroll(textArea, " 在UpdateToBJServer的sendBytes 315函数出现异常  ：");
        }
    }

    /**
     * 补发数据通过socket发送出去
     *
     * @param info 要被发送的字节数组
     */
    public void sendCBytes(String terminalid, Info info) {
        try {
            if (socketc != null && !socketc.isClosed() && psc != null) {
                byte[] msg = buildBJPackage(terminalid, info);
                psc.write(msg, 0, msg.length); // 发送命令
                psc.flush();
            }
        } catch (NumberFormatException e) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "在UpdateToBJServer的sendBytes 496函数出现异常  Direction："
                    + info.getDirection());
        } catch (Exception e) {
            TextAreaScrollUtil.TextAreaScroll(textArea, " 在UpdateToBJServer的sendBytes 500函数出现异常  ：");
        }
    }

    /**
     * 将给定的byte数组发送出去
     *
     * @param data 要被发送的数据
     */
    public void sendBytes(byte[] data) {
        try {
            if (socket != null && !socket.isClosed() && ps != null) {
                ps.write(data, 0, data.length); // 发送命令
                ps.flush();
            }
        } catch (Exception e) {
            TextAreaScrollUtil.TextAreaScroll(textArea, " 在UpdateToBJServer的sendBytes 293函数出现异常：");
        }
    }

    /*************************************************************/

    private void sendToBeijing() {

        TextAreaScrollUtil.TextAreaScroll(textArea, "往北京特定运营商转发数据启动");

        try {
            for (int i = 0; i < cInfoQueue.size(); i++) {
                if ((i + 1) % 70 == 0)
                    // 每隔70次发送一次心跳信号
                    sendBytes(getHeartPackage());

                if ((i + 1) % 7 == 0 && (i + 1) / 7 >= 1) {
                    Info infoTemp = cInfoQueue.poll();
                    cInfoNum++;
                    if (infoTemp != null) {
                        sendCBytes(infoTemp.getYYYYYYYYYY(), infoTemp);
                        TextAreaScrollUtil.TextAreaScroll(textArea, "正在补传数据包序号：" + UploadDatasService.cInfoNum);
                    }
                } else {
                    cInfoQueue.poll();
                    cInfoNum++;
                }
            }

            TextAreaScrollUtil.TextAreaScroll(textArea, "补传数据发送完毕");

        } catch (Exception e) {
            TextAreaScrollUtil.TextAreaScroll(textArea, "SendToBeijingThread的未知异常");
        }
    }

    /**
     * 生成心跳包
     *
     * @return 心跳包内容
     */
    private byte[] getHeartPackage() {
        byte[] bjpackage = new byte[61];
        bjpackage[0] = (byte) 0xAA;
        bjpackage[1] = (byte) 0x55;
        bjpackage[2] = (byte) 0x05;
        bjpackage[3] = (byte) 0x00;
        byte[] tempTerminalid = serverid.getBytes();
        int i = 1;
        for (byte b : tempTerminalid) {
            bjpackage[3 + i] = b;
            i++;
        }
        int numtemp = UploadDatasService.getNum();
        bjpackage[15] = (byte) ((numtemp >> 24) & 0xFF);
        bjpackage[16] = (byte) ((numtemp >> 16) & 0xFF);
        bjpackage[17] = (byte) ((numtemp >> 8) & 0xFF);
        bjpackage[18] = (byte) (numtemp & 0xFF);
        bjpackage[19] = (byte) 0x00;
        bjpackage[20] = (byte) 0x00;
        bjpackage[21] = (byte) 0x00;
        bjpackage[22] = (byte) 0x22;
        bjpackage[23] = (byte) 0x00;
        bjpackage[24] = (byte) 0x3B;
        bjpackage[25] = (byte) 0x20;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.HOUR_OF_DAY, -8);
        String ddmmyy = BuildDateTime.getShortDate(cal);
        String hhmmss = BuildDateTime.getShortTime(cal);
        bjpackage[26] = (byte) ((Byte.parseByte(ddmmyy.substring(4, 5)) << 4) + Byte
                .parseByte(ddmmyy.substring(5, 6)));
        bjpackage[27] = (byte) ((Byte.parseByte(ddmmyy.substring(2, 3)) << 4) + Byte
                .parseByte(ddmmyy.substring(3, 4)));
        bjpackage[28] = (byte) ((Byte.parseByte(ddmmyy.substring(0, 1)) << 4) + Byte
                .parseByte(ddmmyy.substring(1, 2)));
        bjpackage[29] = (byte) ((Byte.parseByte(hhmmss.substring(0, 1)) << 4) + Byte
                .parseByte(hhmmss.substring(1, 2)));
        bjpackage[30] = (byte) ((Byte.parseByte(hhmmss.substring(2, 3)) << 4) + Byte
                .parseByte(hhmmss.substring(3, 4)));
        bjpackage[31] = (byte) ((Byte.parseByte(hhmmss.substring(4, 5)) << 4) + Byte
                .parseByte(hhmmss.substring(5, 6)));
        for (int h = 32; h < 57; h++) {
            bjpackage[h] = 0x00;
        }
        bjpackage[57] = (byte) 0xFF;
        bjpackage[58] = (byte) 0xFF;
        bjpackage[59] = (byte) 0xFF;
        bjpackage[60] = (byte) 0xFF;
        return bjpackage;
    }
}
