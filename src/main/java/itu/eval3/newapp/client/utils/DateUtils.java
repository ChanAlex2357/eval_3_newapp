package itu.eval3.newapp.client.utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    
    // Format par défaut pour l'affichage ou la conversion en string
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Liste des formats acceptés à l'entrée
    private static final DateTimeFormatter[] INPUT_FORMATS = new DateTimeFormatter[]{
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd")
    };

    /**
     * Retourne le premier jour du mois d'une date donnée.
     */
    public static Date getStartOfMonth(Date ref) {
        LocalDate localDate = ref.toLocalDate();
        return Date.valueOf(LocalDate.of(localDate.getYear(), localDate.getMonth(), 1));
    }

    public static Date getStartOfYear(Date ref){
        LocalDate localDate = LocalDate.of(ref.toLocalDate().getYear(), 1, 1);
        return Date.valueOf(localDate);
    }

    public static Date getEndOfYear(Date ref) {
        LocalDate localDate = LocalDate.of(ref.toLocalDate().getYear(), 12, 31);
        return Date.valueOf(localDate);
    }

    /**
     * Convertit un LocalDate en java.sql.Date
     */
    public static Date formatDate(LocalDate ref) {
        return Date.valueOf(ref);
    }

    /**
     * Tente de parser une chaîne de caractères vers java.sql.Date en utilisant plusieurs formats.
     */
    public static Date formatDate(String dateStr) {
        for (DateTimeFormatter fmt : INPUT_FORMATS) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateStr.trim(), fmt);
                return Date.valueOf(parsedDate);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Format de date invalide : " + dateStr + ". Formats attendus : dd/MM/yyyy, dd-MM-yyyy, yyyy-MM-dd, yyyy/MM/dd");
    }

    /**
     * Convertit un LocalDate en chaîne de caractères au format dd/MM/yyyy.
     */
    public static String formatDateToString(LocalDate date) {
        return date.format(DEFAULT_FORMATTER);
    }

    /**
     * Convertit un java.sql.Date en chaîne de caractères au format dd/MM/yyyy.
     */
    public static String formatDateToString(Date date) {
        return formatDateToString(date.toLocalDate());
    }
}
