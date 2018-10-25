package util;

/**
 * Created by rio on 2018/10/22.
 */
public class FormatLL {

    public static String formatLLForUploadToBJ(double ll){
        double lld = Double.valueOf(ll) /100;
        int dd = (int)lld;
        double ff = (lld - dd) * 60 / 100;
        return String.valueOf((int)((dd + ff) * 1000000));
    }

}
