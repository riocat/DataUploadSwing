package mainlogic;

import java.util.Calendar;
import java.util.Iterator;

import entity.Info;

import javax.swing.*;

public class SendToBeijingThread extends Thread {
//
//    private JTextPane textPane1;
//
//    public SendToBeijingThread(){
//
//    }
//
//    public SendToBeijingThread(JTextPane textPane1){
//        super();
//        this.textPane1 = textPane1;
//    }
//
//
//    private int times = 1000;
//    // 北京服务器分配的ID号
//    private static String serverid = "10000000058";
//
//    @Override
//    public void run() {
//        Thread.currentThread().setName("往北京特定运营商转发数据线程");
//        textPane1.setText("往北京特定运营商转发数据线程启动");
////        logger.info("往北京特定运营商转发数据线程启动");
//        super.run();
//        while (UpdataToBJServer.runSendThread) {
//            try {
//                for (int n = 0; n < 10; n++) {
//                    if (!UpdataToBJServer.cInfoQueue.isEmpty()) {
//                        for (int i = 0; i < 6; i++) {
//                            UpdataToBJServer.cInfoQueue.poll();
//                            UpdataToBJServer.cInfoNum++;
//                        }
//                        Info infoTemp = UpdataToBJServer.cInfoQueue.poll();
//                        // infoTemp.setYYYYYYYYYY("0062300898");
//                        UpdataToBJServer.cInfoNum++;
//                        if (infoTemp != null) {
//                            UpdataToBJServer.sendCBytes(
//                                    infoTemp.getYYYYYYYYYY(), infoTemp);
//                            textPane1.setText("正在补传数据包序号：" + UpdataToBJServer.cInfoNum);
///*                            logger.info(
//                                    "正在补传数据包序号：" + UpdataToBJServer.cInfoNum,
//                                    true);*/
//                        }
//                    } else {
//                        times = 180000;
//                        textPane1.setText("补传数据发送完毕，休眠时间改变为180000");
//                        /*logger.info(
//                                "补传数据发送完毕，休眠时间改变为180000");*/
//                        break;
//                    }
//                }
//
//                Iterator<java.util.Map.Entry<String, Info>> i = DataStruct.infoConcurrentHashMap
//                        .entrySet().iterator();
//                while (i.hasNext()) {
//                    java.util.Map.Entry<String, Info> entry = (java.util.Map.Entry<String, Info>) i
//                            .next();
//                    String terminalid = entry.getKey();
//                    Info info = entry.getValue();
//                    UpdataToBJServer.sendBytes(terminalid, info);
//                }
//                DataStruct.infoConcurrentHashMap.clear();
//                UpdataToBJServer.sendBytes(getHeartPackage());
//                Thread.sleep(times);
//            } catch (InterruptedException e) {
//                textPane1.setText("SendToBeijingThread的socket异常后休眠失败");
////                logger.error("SendToBeijingThread的socket异常后休眠失败", e);
//            } catch (Exception e) {
//                textPane1.setText("SendToBeijingThread的未知异常");
////                logger.error("SendToBeijingThread的未知异常", e);
//            }
//        }
//    }
//
//    /**
//     * 生成心跳包
//     *
//     * @return 心跳包内容
//     */
//    private byte[] getHeartPackage() {
//        byte[] bjpackage = new byte[61];
//        bjpackage[0] = (byte) 0xAA;
//        bjpackage[1] = (byte) 0x55;
//        bjpackage[2] = (byte) 0x05;
//        bjpackage[3] = (byte) 0x00;
//        byte[] tempTerminalid = serverid.getBytes();
//        int i = 1;
//        for (byte b : tempTerminalid) {
//            bjpackage[3 + i] = b;
//            i++;
//        }
//        int numtemp = UpdataToBJServer.getNum();
//        bjpackage[15] = (byte) ((numtemp >> 24) & 0xFF);
//        bjpackage[16] = (byte) ((numtemp >> 16) & 0xFF);
//        bjpackage[17] = (byte) ((numtemp >> 8) & 0xFF);
//        bjpackage[18] = (byte) (numtemp & 0xFF);
//        bjpackage[19] = (byte) 0x00;
//        bjpackage[20] = (byte) 0x00;
//        bjpackage[21] = (byte) 0x00;
//        bjpackage[22] = (byte) 0x22;
//        bjpackage[23] = (byte) 0x00;
//        bjpackage[24] = (byte) 0x3B;
//        bjpackage[25] = (byte) 0x20;
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
//        cal.add(Calendar.HOUR_OF_DAY, -8);
//        String ddmmyy = BuildDateTime.getShortDate(cal);
//        String hhmmss = BuildDateTime.getShortTime(cal);
//        bjpackage[26] = (byte) ((Byte.parseByte(ddmmyy.substring(4, 5)) << 4) + Byte
//                .parseByte(ddmmyy.substring(5, 6)));
//        bjpackage[27] = (byte) ((Byte.parseByte(ddmmyy.substring(2, 3)) << 4) + Byte
//                .parseByte(ddmmyy.substring(3, 4)));
//        bjpackage[28] = (byte) ((Byte.parseByte(ddmmyy.substring(0, 1)) << 4) + Byte
//                .parseByte(ddmmyy.substring(1, 2)));
//        bjpackage[29] = (byte) ((Byte.parseByte(hhmmss.substring(0, 1)) << 4) + Byte
//                .parseByte(hhmmss.substring(1, 2)));
//        bjpackage[30] = (byte) ((Byte.parseByte(hhmmss.substring(2, 3)) << 4) + Byte
//                .parseByte(hhmmss.substring(3, 4)));
//        bjpackage[31] = (byte) ((Byte.parseByte(hhmmss.substring(4, 5)) << 4) + Byte
//                .parseByte(hhmmss.substring(5, 6)));
//        for (int h = 32; h < 57; h++) {
//            bjpackage[h] = 0x00;
//        }
//        bjpackage[57] = (byte) 0xFF;
//        bjpackage[58] = (byte) 0xFF;
//        bjpackage[59] = (byte) 0xFF;
//        bjpackage[60] = (byte) 0xFF;
//        return bjpackage;
//    }
//
//    /*
//     * 关闭线程
//     */
//    public boolean stopThread() {
//        UpdataToBJServer.runSendThread = false;
//        try {
//            this.interrupt();
//        } catch (SecurityException e) {
//            textPane1.setText("关闭SendToBeijingThread线程出现异常");
////            logger.error("关闭SendToBeijingThread线程出现异常", e);
//        }
//        return true;
//    }
}