package question1;

import java.time.ZonedDateTime;

public class PrivateFieldFoobar {
    private final ZonedDateTime now = ZonedDateTime.now();

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + now.toString();
    }
}
