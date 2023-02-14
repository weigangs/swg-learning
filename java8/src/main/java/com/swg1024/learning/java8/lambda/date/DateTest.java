package com.swg1024.learning.java8.lambda.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年02月01日 22:15
 */
public class DateTest {

    @Test
    public void test1() {
        // with 创建可修改得副本，get 的不可修改
        LocalDate date1 = LocalDate.of(2017, 9, 21);
        LocalDate date2 = date1.withYear(2011);
        System.out.println(date2.toString());
        LocalDate date3 = date2.withDayOfMonth(25);
        System.out.println(date3.toString());
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2);
        System.out.println(date4.toString());
    }

    public void test2() {
        // 精确到分，秒，纳秒
        Duration duration = Duration.between(LocalDateTime.of(2022, 1, 20, 10, 20), LocalDateTime.of(2022, 2, 30, 10, 20));

        Duration duration2 = Duration.between(LocalTime.of(10, 20, 50), LocalTime.of(20, 20, 50));
        // 周期， 年月日，天，月，分
        Period period = Period.between(LocalDate.of(2002, 10, 3), LocalDate.of(2022, 10, 3));
    }

    @Test
    public void test3() {
        // 下个工作日，月末最后一天，下个周日
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date3 = date2.with(lastDayOfMonth());
        System.out.println(date3);
        LocalDate date4 = date3.minusDays(1);
        System.out.println(date4);
        // 下一个工作日，使用lambda，TemporalAdjuster 接口，只有一个方法，所以可以使用函数式接口写法
        LocalDate date5 = date4.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            } else if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
    }

    @Test
    public void test4() {
        DateTimeFormatter chineseFormatter = DateTimeFormatter.ofPattern("yyyy啊MM月dd号", Locale.CHINA);
        LocalDate date = LocalDate.now();
        System.out.println(date.format(chineseFormatter));
    }
}
