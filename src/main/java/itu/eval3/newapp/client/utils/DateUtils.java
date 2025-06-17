package itu.eval3.newapp.client.utils;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {
    public static Date getStartOfMonth(Date ref){
        Date start_of_moth = Date.valueOf(
            LocalDate.of(
                ref.toLocalDate().getYear(),
                ref.toLocalDate().getMonth(),
                1
            )
        );
        return start_of_moth;
    }
}
