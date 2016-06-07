package krystleandkori.kittycollector;

/**
 * Created by inky on 6/6/2016.
 */
public class User {
    Double clickStr = 1.0;
    Integer collarsCollected = 0;

    public User(){
        clickStr = 1.0;
        collarsCollected = 0;
    }

    public Double getClickStr() {
        return clickStr;
    }

    public void setClickStr(Double clickStr) {
        this.clickStr = clickStr;
    }

    public Integer getCollarsCollected() {
        return collarsCollected;
    }

    public void setCollarsCollected(Integer collarsCollected) {
        this.collarsCollected = collarsCollected;
    }
}
