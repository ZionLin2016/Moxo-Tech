package px.com.photoselectorlibrary;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by admin on 2016/9/12.
 */
public class SerializableMap implements Serializable {
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
