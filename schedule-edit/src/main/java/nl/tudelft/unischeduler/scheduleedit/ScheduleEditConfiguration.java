package nl.tudelft.unischeduler.scheduleedit;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleEditConfiguration {
    private static Clock clock = generateDefaultClock();

    private static Clock generateDefaultClock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public static Clock getClock() {
        return clock;
    }

    public static void setClock(Clock clock) {
        ScheduleEditConfiguration.clock = clock;
    }
}
