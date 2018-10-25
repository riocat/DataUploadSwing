package entity;

/**
 * 非808协议拆开后的对象
 * @author xpm
 */
public class Info {

    private String data;

    /**
     * 信息类型 ，目前支持两种 v1 和 v4
     */
    private String type;

    // *TH,2020916012,V1,050316,A,2212.8745,N,11346.6574,E,14.28,028,220902,FFFFFBFF
    /**
     * 制造商名称
     */
    private String XX;

    /**
     * 车载机序列号
     */
    private String YYYYYYYYYY;

    /**
     * 被确认的中心命令。
     */
    private String CMD;

    /**
     * 被确认命令中的时间值
     */
    private String hhmmss;

    /**
     * 车载机时间,标准时间，与北京时间有8小时时差。
     */

    private String HHMMSS;

    /**
     * 数据有效位（A/V），A表示GPS数据是有效定位数据，V表示GPS数据是无效定位数据。
     */
    private String S;

    /**
     * 纬度，格式DDFF.FFFF转化成DD.DDDDDD * 100，保留四位小数。
     */
    private String latitude;

    private double latituded;

    /**
     * 纬度标志（N：北纬，S：南纬）。
     */
    private String D;

    /**
     * 经度，DDDFF.FFFF转化成DDD.DDDDDD * 100，保留四位小数。
     */
    private String longitude;

    private double longituded;

    /**
     * 经度标志（E：东经，W：西经）。
     */
    private String G;

    /**
     * 速度，范围000.00 ~ 999.99 节，保留两位小数。
     */
    private String speed;

    /**
     * 方位角，正北为0度，分辨率1度，顺时针方向。该信息字段可能为空如longitude,G,speed,, MMDDYY,，表示角度为0。
     */
    private String direction;

    /**
     * 日/月/年
     */
    private String DDMMYY;

    /**
     * 车辆状态，共四字节，表示车载机部件状态、车辆部件状态以及报警状态等。用ASCII字符表示16进制值，
     * 下面是该变量中各字节的每一位的具体含义，bit表示采用负逻辑，即bit=0有效。
     */
    private String vehicle_statusstr;

    private short[] vehicle_status;

    // 如果再出现alarmtype为空的异常就将这里修改为：alarmtype = "";
    private String alarmtype;

    private String refvalue;

    private VehicleStatus vehiclestatus;

    private short usr_alarm_flag;

    /**
     * 存储收到了多少个字节，流量，
     * 但是由于整合版本已经不准了，如果将来有需要的话可以修改
     */
    private int liuliang;

    private String sqltxt;

    public void statusAnaly(String status) {
        // 按顺序存放 每个 byte 存 4 位
        vehicle_status = new short[4];

        vehicle_status[0] = Short.valueOf(status.substring(0, 2), 16)
                .shortValue();
        vehicle_status[1] = Short.valueOf(status.substring(2, 4), 16)
                .shortValue();
        vehicle_status[2] = Short.valueOf(status.substring(4, 6), 16)
                .shortValue();
        vehicle_status[3] = Short.valueOf(status.substring(6, 8), 16)
                .shortValue();

        vehiclestatus = new VehicleStatus(vehicle_status);
    }

    public String getAlarmtype() {
        return alarmtype;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
    }

    public String getRefvalue() {
        return refvalue;
    }

    public void setRefvalue(String refvalue) {
        this.refvalue = refvalue;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getDDMMYY() {
        return DDMMYY;
    }

    public void setDDMMYY(String ddmmyy) {
        DDMMYY = ddmmyy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getG() {
        return G;
    }

    public void setG(String g) {
        G = g;
    }

    public String getHHMMSS() {
        return HHMMSS;
    }

    public void setHHMMSS(String hhmmss) {
        HHMMSS = hhmmss;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        if (D.equals("N"))
            this.setLatituded(Double.parseDouble(latitude));
        else
            this.setLatituded(Double.parseDouble("-" + latitude));
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        if (G.equals("E"))
            this.setLongituded(Double.parseDouble(longitude));
        else
            this.setLongituded(Double.parseDouble("-" + longitude));
        this.longitude = longitude;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getXX() {
        return XX;
    }

    public void setXX(String xx) {
        XX = xx;
    }

    public String getYYYYYYYYYY() {
        return YYYYYYYYYY;
    }

    public void setYYYYYYYYYY(String yyyyyyyyyy) {
        YYYYYYYYYY = yyyyyyyyyy;
    }

    public String getVehicle_statusstr() {
        return vehicle_statusstr;
    }

    public void setVehicle_statusstr(String vehicle_statusstr) {
        this.vehicle_statusstr = vehicle_statusstr;
        this.statusAnaly(vehicle_statusstr);
    }

    public String getCMD() {
        return CMD;
    }

    public void setCMD(String cmd) {
        CMD = cmd;
    }

    public String getHhmmss() {
        return hhmmss;
    }

    public void setHhmmss(String hhmmss) {
        this.hhmmss = hhmmss;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public VehicleStatus getVehiclestatus() {
        return vehiclestatus;
    }

    public void setVehiclestatus(VehicleStatus vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

    public short[] getVehicle_status() {
        return vehicle_status;
    }

    public void setVehicle_status(short[] vehicle_status) {
        this.vehicle_status = vehicle_status;
    }

    public double getLatituded() {
        return latituded;
    }

    public void setLatituded(double latituded) {
        this.latituded = latituded;
    }

    public double getLongituded() {
        return longituded;
    }

    public void setLongituded(double longituded) {
        this.longituded = longituded;
    }

    // 组成一般Ascii字符串
    public String toAscii() {
        StringBuffer sb = new StringBuffer();
        sb.append("*");
        sb.append(this.getXX()).append(",");
        sb.append(this.getYYYYYYYYYY()).append(",");
        sb.append("V1,");
        sb.append(this.getHHMMSS()).append(",");
        sb.append(this.getS()).append(",");
        sb.append(this.getLatitude()).append(",");
        sb.append(this.getD()).append(",");
        sb.append(this.getLongitude()).append(",");
        sb.append(this.getG()).append(",");
        sb.append(this.getSpeed()).append(",");
        sb.append(this.getDirection()).append(",");
        sb.append(this.getDDMMYY()).append(",");
        sb.append(this.getVehicle_statusstr()).append("#");
        return sb.toString();
    }

    public short getUsr_alarm_flag() {
        return usr_alarm_flag;
    }

    public void setUsr_alarm_flag(short usr_alarm_flag) {
        this.usr_alarm_flag = usr_alarm_flag;
    }

    public int getLiuliang() {
        return liuliang;
    }

    public void setLiuliang(int liuliang) {
        this.liuliang = liuliang;
    }

    public String getSqltxt() {
        return sqltxt;
    }

    public void setSqltxt(String sqltxt) {
        this.sqltxt = sqltxt;
    }
}
