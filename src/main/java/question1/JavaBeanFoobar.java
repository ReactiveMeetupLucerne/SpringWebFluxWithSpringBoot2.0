package question1;

import java.time.ZonedDateTime;

public class JavaBeanFoobar {
    private final ZonedDateTime now = ZonedDateTime.now();

    public ZonedDateTime getNow() {
        return now;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + now.toString();
    }
}
