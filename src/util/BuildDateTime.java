package util;

import java.util.Calendar;

/**
 * Created by rio on 2018/10/22.
 */
public class BuildDateTime {

    public static String getShortDate(Calendar cal)
    {
        return new StringBuffer().append(getDay(cal)).append(getMonth(cal))
                .append(getShortYear(cal)).toString();
    }

    public static String getShortTime(Calendar cal)
    {
        return new StringBuffer().append(getHour(cal)).append(getMinute(cal))
                .append(getSecond(cal)).toString();
    }

    /**
     * <p>
     * [功能描述]：返回日，格式：DD，如2014-11-24 13:46:08，则返回24
     * </p>
     *
     * @author 熊平民, 2014-11-24上午11:28:54
     * @since GPS调度管理系统 1.0.1
     *
     * @param cal
     * @return
     */
    public static String getDay(Calendar cal)
    {
        int d = cal.get(Calendar.DATE);
        if (d < 10)
        {
            return "0" + String.valueOf(d);
        }
        else
        {
            return String.valueOf(d);
        }
    }

    /**
     * <p>
     * [功能描述]：返回月，格式：DD，如2014-11-24 13:46:08，则返回11
     * </p>
     *
     * @author 熊平民, 2014-11-24下午2:43:02
     * @since GPS调度管理系统 1.0.1
     *
     * @param cal
     * @return
     */
    public static String getMonth(Calendar cal)
    {
        int m = cal.get(Calendar.MONTH) + 1;
        if (m < 10)
        {
            return "0" + String.valueOf(m);
        }
        else
        {
            return String.valueOf(m);
        }
    }

    /**
     * <p>
     * [功能描述]：返回短格式年，格式：YY，如2014-11-24 13:46:08，则返回14
     * </p>
     *
     * @author 熊平民, 2014-11-24下午2:20:20
     * @since GPS调度管理系统 1.0.1
     *
     * @param cal
     * @return
     */
    public static String getShortYear(Calendar cal)
    {
        return String.valueOf(cal.get(Calendar.YEAR)).substring(2, 4);
    }

    /**
     * <p>
     * [功能描述]：返回小时(24小时制)，格式：hh，如2014-11-24 13:46:08，则返回13
     * </p>
     *
     * @author 熊平民, 2014-11-24下午2:18:52
     * @since GPS调度管理系统 1.0.1
     *
     * @param cal
     * @return
     */
    public static String getHour(Calendar cal)
    {
        if (cal.get(Calendar.HOUR_OF_DAY) < 10)
        {
            return "0" + cal.get(Calendar.HOUR_OF_DAY);
        }
        else
        {
            return String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        }
    }

    /**
     * <p>
     * [功能描述]：返回分钟，格式：mm，如2014-11-24 13:46:08，则返回46
     * </p>
     *
     * @author 熊平民, 2014-11-24下午2:14:58
     * @since GPS调度管理系统 1.0.1
     *
     * @param cal
     * @return
     */
    public static String getMinute(Calendar cal)
    {
        if (cal.get(Calendar.MINUTE) < 10)
        {
            return "0" + cal.get(Calendar.MINUTE);
        }
        else
        {
            return String.valueOf(cal.get(Calendar.MINUTE));
        }
    }

    /**
     * <p>
     * [功能描述]：返回秒，格式：ss，如2014-11-24 13:46:08，则返回08
     * </p>
     *
     * @author 熊平民, 2014-11-24下午2:14:37
     * @since GPS调度管理系统 1.0.1
     *
     * @param cal
     * @return
     */
    public static String getSecond(Calendar cal)
    {
        if (cal.get(Calendar.SECOND) < 10)
        {
            return "0" + cal.get(Calendar.SECOND);
        }
        else
        {
            return String.valueOf(cal.get(Calendar.SECOND));
        }
    }

}
