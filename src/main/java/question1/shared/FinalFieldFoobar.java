package question1.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class FinalFieldFoobar {

    private final ZonedDateTime zonedDateTime;

    @JsonCreator
    public FinalFieldFoobar(@JsonProperty("zonedDateTime") ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + zonedDateTime.toString();
    }
}
