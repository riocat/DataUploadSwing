package util;

import entity.DataStruct;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rio on 2018/10/24.
 */
public class MyLogUtil {

    private static String defaultDirectory = "LGTOCENTER";

    private static SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss");

    private static SimpleDateFormat sdfc = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

    public static final SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void accountExecute(DataStruct dataStruct) {

        // windows盘根目录下建立日志文件夹及日志文件
        String url = MyLogUtil.class.getResource("").getFile();
        String filePath = getWindowsDiskName(url) + defaultDirectory;
        File dfDirectory = new File(filePath);
        if (!dfDirectory.exists()) {
            dfDirectory.mkdirs();
        }

        String logFileName = filePath + '/' + "数据补传记录" + sdf.format(new Date()) + ".txt";
        FileOutputStream fos = null;
        PrintWriter pw = null;

        try {
            fos = new FileOutputStream(logFileName);
            pw = new PrintWriter(fos);
            pw.println(sdfc.format(new Date()) + "进行车辆信息补传");
            pw.println("车台号：" + dataStruct.getDataMap().get("Cpterminalid"));
            pw.println("数据库表名：" + dataStruct.getDataMap().get("tableName"));
            pw.println("待传数据开始时间：" + MyLogUtil.sdfd.format(dataStruct.getDataMap().get("Startinfodatetime")));
            pw.println("待传数据结束时间：" + MyLogUtil.sdfd.format(dataStruct.getDataMap().get("Endinfodatetime")));
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            if (pw != null) {
                pw.close();
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getWindowsDiskName(String url) {
        String realPath = "";
        System.out.println(url);
        if (url.indexOf("file:/") >= 0) {
            realPath = url.substring(6, 7) + ":/";
        } else {
            realPath = url.substring(0, url.indexOf(":")) + ":/";
        }

        System.out.println(url);
        return realPath;
    }
}
