import java.sql.Timestamp;
import java.util.*;

class Util {
    // creating a static method to generate a timestamp
    static Timestamp getTimeStamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }
}