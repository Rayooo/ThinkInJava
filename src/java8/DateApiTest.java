package java8;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * Create by Ray 2017/4/26 14:22
 */
public class DateApiTest {

    @Test
    public void main(){
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);

        System.out.println(millis);         //1493187857378
        System.out.println(legacyDate);     //Wed Apr 26 14:24:17 CST 2017


        System.out.println(ZoneId.getAvailableZoneIds());
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());



        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));  // 测试哪个时间在前false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);   //计算时间差
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239



        //本地化时间
        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);       // 23:59:59

        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);   // 13:37




        LocalDate today = LocalDate.now();      //修改时间，增加时间
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        System.out.println(tomorrow);       //2017-04-27
        System.out.println(yesterday);      //2017-04-25

        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);    // FRIDAY



        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern("MM dd, yyyy - HH:mm");

        LocalDateTime parsed = LocalDateTime.parse("12 03, 2014 - 07:13", formatter);
        String string = formatter.format(parsed);
        System.out.println(string);     //12 03, 2014 - 07:13
    }


}
