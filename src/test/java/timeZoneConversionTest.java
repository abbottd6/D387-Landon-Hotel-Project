import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class timeZoneConversionTest {

    @Test
    public void presentationMessageTest() {
        List<String> convertedTimes = new ArrayList<>();
        String[] presentationMessage;
        LocalDateTime presentationTime = LocalDateTime.of(2024, 8, 23,1,30);

        ZonedDateTime utcTime = presentationTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime mtTime = utcTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime eastTime = utcTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm z");
        convertedTimes.add((utcTime.format(formatter)));
        convertedTimes.add(mtTime.format(formatter));
        convertedTimes.add(eastTime.format(formatter));

        presentationMessage = new String[]{"Come join us for a live web presentation exclusively on the Landon " +
                "Hotel website on Friday, August 23rd at " + "\n" + convertedTimes.get(0) + "\n"
                + convertedTimes.get(1) + "\n" + convertedTimes.get(2)};
    }
}
