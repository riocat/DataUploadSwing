package entity;

/**
 * Created by rio on 2018/10/22.
 */
public class VehicleStatus {
    // 第一字段
    // 温度报警
    private boolean temperature = false;

    // 三次密码错误报警
    private boolean passwd = false;

    // GPRS阻塞报警
    private boolean gprsblock = false;

    // 车辆处于断油电状态
    private boolean oilpoweroff = false;

    // 电瓶拆除报警
    private boolean batteryDemolition = false;

    // 高电平传感器1为高
    private boolean highSensors1 = false;

    // 高电平传感器2为高
    private boolean highSensors2 = false;

    // 低电平传感器1搭铁
    private boolean lowSensors1 = false;

    // 第二字段
    // GPS接收机故障报警
    private boolean ReceiverFault = false;

    // 保留
    // 保留
    // 主机由后备电池供电
    private boolean ReserveBattery = false;

    // 电瓶被拆除
    private boolean batteryRemoved = false;

    // GPS天线开路

    private boolean gpsAerialOpen = false;

    // GPS天线短路
    private boolean gpsAerialShort = false;

    // 低电平传感器2搭铁
    private boolean lowSensors2 = false;

    // 第三字段
    // 车门开
    private boolean dooropen = false;

    // 车辆设防
    private boolean cardefend = false;

    // ACC关
    private boolean accoff;

    // 保留
    // 保留
    // 发动机
    private boolean Engine = false;

    // 自定义报警
    private boolean defalarm = false;

    // 保留

    // 第四字段
    // 盗警
    private boolean pirates = false;

    // 劫警
    private boolean robbery = false;

    // 超速报警
    private boolean Speeding = false;

    // 非法点火报警
    private boolean Illegalstart = false;

    // 禁止驶入越界报警
    private boolean ProhibitionIn = false;

    // GPS天线开路报警
    private boolean gpsAerialOpenAlarm = false;

    // GPS天线短路报警
    private boolean gpsAerialShortAlarm = false;

    // 禁止驶出越界报警
    private boolean ProhibitionOut = false;

    public VehicleStatus(short[] bl) {
        if (!((bl[0] & 1) == 1))
            this.setTemperature(true);
        if (!((bl[0] & 2) == 2))
            this.setPasswd(true);
        if (!((bl[0] & 4) == 4))
            this.setGprsblock(true);
        if (!((bl[0] & 8) == 8))
            this.setOilpoweroff(true);
        if (!((bl[0] & 16) == 16))
            this.setBatteryDemolition(true);
        if (!((bl[0] & 32) == 32))
            this.setHighSensors1(true);
        if (!((bl[0] & 64) == 64))
            this.setHighSensors2(true);
        if (!((bl[0] & 128) == 128))
            this.setLowSensors1(true);

        // 第二字段
        // GPS接收机故障报警
        // 保留
        // 保留
        // 主机由后备电池供电
        // 电瓶被拆除
        // GPS天线开路
        // GPS天线短路
        // 低电平传感器2搭铁
        if (!((bl[1] & 1) == 1))
            this.setReceiverFault(true);
        if (!((bl[1] & 2) == 2)) {
        }
        if (!((bl[1] & 4) == 4)) {
        }
        if (!((bl[1] & 8) == 8))
            this.setReserveBattery(true);
        if (!((bl[1] & 16) == 16))
            this.setBatteryRemoved(true);
        if (!((bl[1] & 32) == 32))
            this.setGpsAerialOpen(true);
        if (!((bl[1] & 64) == 64))
            this.setGpsAerialShort(true);
        if (!((bl[1] & 128) == 128))
            this.setLowSensors2(true);
        // 第三字段
        // 车门开
        // 车辆设防
        // ACC关
        // 保留
        // 保留
        // 发动机
        // 自定义报警
        // 保留
        if (!((bl[2] & 1) == 1))
            this.setDooropen(true);
        if (!((bl[2] & 2) == 2))
            this.setCardefend(true);
        if (!((bl[2] & 4) == 4))
            this.setAccoff(true);
        if (!((bl[2] & 8) == 8)) {
        }
        if (!((bl[2] & 16) == 16)) {
        }
        if (!((bl[2] & 32) == 32))
            this.setEngine(true);
        if (!((bl[2] & 64) == 64))
            this.setDefalarm(true);
        if (!((bl[2] & 128) == 128)) {
        }
        // 第四字段
        // 盗警
        // 劫警
        // 超速报警
        // 非法点火报警
        // 禁止驶入越界报警
        // GPS天线开路报警
        // GPS天线短路报警
        // 禁止驶出越界报警

        if (!((bl[3] & 1) == 1))
            this.setPirates(true);
        if (!((bl[3] & 2) == 2))
            this.setRobbery(true);
        if (!((bl[3] & 4) == 4))
            this.setSpeeding(true);
        if (!((bl[3] & 8) == 8))
            this.setIllegalstart(true);
        if (!((bl[3] & 16) == 16))
            this.setProhibitionIn(true);
        if (!((bl[3] & 32) == 32))
            this.setGpsAerialOpenAlarm(true);
        if (!((bl[3] & 64) == 64))
            this.setGpsAerialShortAlarm(true);
        if (!((bl[3] & 128) == 128))
            this.setProhibitionOut(true);

    }

    public boolean isAccoff() {
        return accoff;
    }

    public void setAccoff(boolean accoff) {
        this.accoff = accoff;
    }

    public boolean isBatteryDemolition() {
        return batteryDemolition;
    }

    public void setBatteryDemolition(boolean batteryDemolition) {
        this.batteryDemolition = batteryDemolition;
    }

    public boolean isBatteryRemoved() {
        return batteryRemoved;
    }

    public void setBatteryRemoved(boolean batteryRemoved) {
        this.batteryRemoved = batteryRemoved;
    }

    public boolean isCardefend() {
        return cardefend;
    }

    public void setCardefend(boolean cardefend) {
        this.cardefend = cardefend;
    }

    public boolean isDefalarm() {
        return defalarm;
    }

    public void setDefalarm(boolean defalarm) {
        this.defalarm = defalarm;
    }

    public boolean isDooropen() {
        return dooropen;
    }

    public void setDooropen(boolean dooropen) {
        this.dooropen = dooropen;
    }

    public boolean isEngine() {
        return Engine;
    }

    public void setEngine(boolean engine) {
        Engine = engine;
    }

    public boolean isGpsAerialOpen() {
        return gpsAerialOpen;
    }

    public void setGpsAerialOpen(boolean gpsAerialOpen) {
        this.gpsAerialOpen = gpsAerialOpen;
    }

    public boolean isGpsAerialOpenAlarm() {
        return gpsAerialOpenAlarm;
    }

    public void setGpsAerialOpenAlarm(boolean gpsAerialOpenAlarm) {
        this.gpsAerialOpenAlarm = gpsAerialOpenAlarm;
    }

    public boolean isGpsAerialShort() {
        return gpsAerialShort;
    }

    public void setGpsAerialShort(boolean gpsAerialShort) {
        this.gpsAerialShort = gpsAerialShort;
    }

    public boolean isGpsAerialShortAlarm() {
        return gpsAerialShortAlarm;
    }

    public void setGpsAerialShortAlarm(boolean gpsAerialShortAlarm) {
        this.gpsAerialShortAlarm = gpsAerialShortAlarm;
    }

    public boolean isGprsblock() {
        return gprsblock;
    }

    public void setGprsblock(boolean gprsblock) {
        this.gprsblock = gprsblock;
    }

    public boolean isHighSensors1() {
        return highSensors1;
    }

    public void setHighSensors1(boolean highSensors1) {
        this.highSensors1 = highSensors1;
    }

    public boolean isHighSensors2() {
        return highSensors2;
    }

    public void setHighSensors2(boolean highSensors2) {
        this.highSensors2 = highSensors2;
    }

    public boolean isIllegalstart() {
        return Illegalstart;
    }

    public void setIllegalstart(boolean illegalstart) {
        Illegalstart = illegalstart;
    }

    public boolean isLowSensors2() {
        return lowSensors2;
    }

    public void setLowSensors2(boolean lowSensors2) {
        this.lowSensors2 = lowSensors2;
    }

    public boolean isOilpoweroff() {
        return oilpoweroff;
    }

    public void setOilpoweroff(boolean oilpoweroff) {
        this.oilpoweroff = oilpoweroff;
    }

    public boolean isPasswd() {
        return passwd;
    }

    public void setPasswd(boolean passwd) {
        this.passwd = passwd;
    }

    public boolean isPirates() {
        return pirates;
    }

    public void setPirates(boolean pirates) {
        this.pirates = pirates;
    }

    public boolean isProhibitionIn() {
        return ProhibitionIn;
    }

    public void setProhibitionIn(boolean prohibitionIn) {
        ProhibitionIn = prohibitionIn;
    }

    public boolean isProhibitionOut() {
        return ProhibitionOut;
    }

    public void setProhibitionOut(boolean prohibitionOut) {
        ProhibitionOut = prohibitionOut;
    }

    public boolean isReceiverFault() {
        return ReceiverFault;
    }

    public void setReceiverFault(boolean receiverFault) {
        ReceiverFault = receiverFault;
    }

    public boolean isReserveBattery() {
        return ReserveBattery;
    }

    public void setReserveBattery(boolean reserveBattery) {
        ReserveBattery = reserveBattery;
    }

    public boolean isRobbery() {
        return robbery;
    }

    public void setRobbery(boolean robbery) {
        this.robbery = robbery;
    }

    public boolean isSpeeding() {
        return Speeding;
    }

    public void setSpeeding(boolean speeding) {
        Speeding = speeding;
    }

    public boolean isTemperature() {
        return temperature;
    }

    public void setTemperature(boolean temperature) {
        this.temperature = temperature;
    }

    public boolean isLowSensors1() {
        return lowSensors1;
    }

    public void setLowSensors1(boolean lowSensors1) {
        this.lowSensors1 = lowSensors1;
    }
}
