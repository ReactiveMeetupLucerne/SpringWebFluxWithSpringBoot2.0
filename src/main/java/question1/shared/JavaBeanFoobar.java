package question1.shared;

import java.time.ZonedDateTime;

// just works
public class JavaBeanFoobar {

    private ZonedDateTime zonedDateTime = ZonedDateTime.now();

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + zonedDateTime.toString();
    }
}
