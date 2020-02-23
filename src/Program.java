import java.time.*;
import java.time.format.DateTimeFormatter;

public class Program {
    private static boolean init = false;

    public static void main(String[] args) {
        if (init) {
            throw new IllegalCallerException("Program may only be started once!");
        }
        new Program();
    }

    private Program() {
        if (init) {
            throw new IllegalCallerException("Program may only be started once!");
        }
        init = true;
        // Program starts here!
        LocalDate birthDate = LocalDate.of(1990, 12, 5);

        System.out.println("Days passed since my birthday: " + dateDifferenceDays(LocalDate.now(), birthDate));

        ZonedDateTime skypeAppointmentNewYork = ZonedDateTime.of(LocalDateTime.of(2020, 04, 1, 8, 30), ZoneId.of("America/New_York"));
        ZonedDateTime skypeAppointmentMalmo = skypeAppointmentNewYork.withZoneSameInstant(ZoneId.of("Europe/Stockholm"));

        System.out.println("Skype meeting in New York at: " + DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a").format(skypeAppointmentNewYork));
        System.out.println("Skype meeting in Malmö at: " + DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(skypeAppointmentMalmo));

        System.out.println("Number of Friday the Thirteenths: " + fridayThirteenth(LocalDate.of(1900, 1, 13)));

        System.out.printf("Days spent at the toilet at age 80: %d", bathroomTimeMinutes(80) / 60 / 24);

        final int hoursSleep = 8;
        final int hoursWork = 8;
        final int hoursTravelTime = 1;
        final double hoursToiletTime = 24 * 0.02;
        final double hoursHygiene = 0.5;
        final int hoursChores = 1;
        final double hoursEat = 0.5;
        final double hoursWeekday = hoursSleep + hoursWork + hoursTravelTime + hoursToiletTime + hoursHygiene + hoursChores + hoursEat;
        final double hoursWeekend = hoursSleep + hoursToiletTime + hoursHygiene + hoursChores + hoursEat;
        final double hoursEndOfLife = hoursSleep + (hoursToiletTime + hoursHygiene + hoursChores + hoursEat) * 2;
        // Weekend no work.
        // 65-80 no work, double everything else.
        double freeTime = 0;

        while (birthDate.getDayOfYear() != 2070 || birthDate.getMonthValue() != 12 || birthDate.getDayOfMonth() != 5) {
            if (birthDate.getYear() >= 2055) {
                freeTime += 24-hoursEndOfLife;
            } else {
                if (birthDate.getDayOfWeek() == DayOfWeek.SATURDAY || birthDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    freeTime += 24 - hoursWeekend;
                } else {
                    freeTime += 24 - hoursWeekday;
                }
            }
            birthDate = birthDate.plusDays(1);
        }

        System.out.println("Percent of life freetime: "+freeTime);
    }

    private long dateDifferenceDays(LocalDate date1, LocalDate date2) {
        Period diff = Period.between(date1, date2);
        return (int) Math.abs(Math.floor(diff.getYears() * 365.242199 + diff.getMonths() * 30.4368499 + diff.getDays()));
    }

    private int fridayThirteenth(LocalDate date) {
        int result = 0;
        Period diff = Period.between(date, LocalDate.now());
        int monthDiff = diff.getMonths() + diff.getYears() * 12;
        for (int i = 0; i <= monthDiff; i++) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                ++result;
            }
            date = date.plusMonths(1);
        }
        return result;
    }

    private int bathroomTimeMinutes(int age) {
        return (int) (age * 365.242199 * 27);
    }
}
