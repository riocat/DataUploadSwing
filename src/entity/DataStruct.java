package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rio on 2018/10/23.
 */
public class DataStruct {

    private Map<String,Object> dataMap = new HashMap<String, Object>();

    public Map<String,Object> getDataMap(){
        return this.dataMap;
    }
}
