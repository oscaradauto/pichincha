package com.app.pichincha.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utilitario {

    public static LocalDateTime convertirADateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime convertirADateTimeFinDelDia(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
    }


    public static String formatearFecha(LocalDateTime fecha) {
        Date date = Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

}
