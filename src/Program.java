import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Program {
    private static boolean init = false;
    private LocalDate birthDate = LocalDate.of(1990, 12, 5);
    ZonedDateTime skypeAppointmentNewYork = ZonedDateTime.of(LocalDateTime.of(2020, 04, 1, 14, 30), ZoneId.of("America/New_York"));
    ZonedDateTime skypeAppointmentMalmo = skypeAppointmentNewYork.withZoneSameInstant(ZoneId.of("Europe/Stockholm"));

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
        prints();
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

    private float bathroomTimeMinutes(LocalDate birthDate, int age) {
        float timeDay = 14.5f;
        float timeMobileModifier = 12.5f;
        float totalBathroomTime = 0;
        LocalDate date = birthDate;
        while(!date.equals(birthDate.withYear(birthDate.getYear()+age))) {
            totalBathroomTime += (date.isBefore(birthDate.withYear(2017)))? timeDay:timeDay+timeMobileModifier;
            date = date.plusDays(1);
        }
        return totalBathroomTime;
    }

    private float freeTimeHours(LocalDate birthDate, int age, boolean mobileUse) {
        float sleep = 8;
        float work  = 9; // not on weekends or after 65
        float other = (24*0.02f)+2; // double after 65
        float hoursFreeTime = 0;
        LocalDate date = birthDate;
        while(!date.equals(birthDate.withYear(birthDate.getYear()+age))) {
            if(!date.isBefore(birthDate.withYear(birthDate.getYear()+65))) { // age 65 and up
                hoursFreeTime += 24-(other*2+sleep);
            } else { // before age 65
                if (date.getDayOfWeek()==DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY) {
                    hoursFreeTime += 24-(sleep+other);
                } else {
                    hoursFreeTime += 24-(work+sleep+other);
                }
            }
            if(mobileUse) hoursFreeTime -= 3;
            date = date.plusDays(1);
        }
        return hoursFreeTime;
    }

    private void prints() {
        System.out.println("Days passed since my birthday: " + dateDifferenceDays(LocalDate.now(), birthDate));
        System.out.println("Skype meeting in New York at: " + DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a").withLocale(new Locale("en", "US")).format(skypeAppointmentNewYork));
        System.out.println("Skype meeting in Malmö at: " + DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(skypeAppointmentMalmo));
        System.out.println("Number of Friday the Thirteenths: " + fridayThirteenth(LocalDate.of(1900, 1, 13)));
        System.out.println("Antal dagar sittandes på toaletten till ålder 80: "+bathroomTimeMinutes(birthDate,30)/60/24);
        System.out.printf("Total fritid i procent av ens liv vid ålder 80: %.2f%%\n",(freeTimeHours(birthDate, 80, false)/24/30.4368499/12)/80*100);
        System.out.println("Total fritid i timmar fram till ålder 80: "+freeTimeHours(birthDate, 80, false));
        System.out.println("Total fritid i timmar fram till ålder 80 med mobiltid borträknat: "+freeTimeHours(birthDate, 80, true));
    }
}
