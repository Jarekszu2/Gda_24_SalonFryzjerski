package salonFryzjerski;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO: Twoim zadaniem jest zaimplementowanie systemu z którego będzie korzystać salon fryzjerski
        //  Salon przyjmuje klientów na umówioną porę. Każdy klient musi zostawić imie i numer telefonu by się umówić.
        //
        //  Fryzjer powinien mieć możliwość:
        //      -- dodania wizyty (zapisania) Przy zapisie musi być dodana informacja na jaką godzinę, kto (imie + numer), co chce zrobić (może być enum z listą wszystkich usług fryzjera)
        //      -- zrealizowania wizyty (czyli odchaczenia jako wykonanej) - przy realizowaniu fryzjer może zaznaczyć ile skasował klienta.
        //      -- odchaczenia wizyty jako nie zrealizowanej bo ktoś się nie pojawił :( (ktoś może zapomniał o wizycie)
        //      -- anulowania wizyty (czyli usunięcie wpisu)
        //      -- listowania wizyt
        //      -- wypisywania jakie usługi są dostępne u fryzjera
        //      -- sprawdzania czy wizyta jest aktualna (czy dana osoba posiada wizytę) - tutaj musimy podać dokładną datę i godzinę i imie, a następnie sprawdzamy czy dana osoba miała wizytę.
        //              Zastanów się co zrobić jeśli wizyta jest np. 15 min później. Może ta osoba się pomyliła. Zwróć w tym wypadku poprawną datę i godzinę wizyty która rzeczywiście była umówiona.
        //              Np. jeśli wizyta jest na godzinę 15:00 w 10.05.2019, a ktoś podał inną datę to wylistuj rzeczywiste daty i godziny wizyt tej osoby.
        //      -- podliczania zrealizowanych wizyt. Jeśli przy wizycie było napisane że jest zrealizowana i że fryzjer potrącił klienta na jakąś kwotę,
        //              To powinna istnieć metoda która sumuje wszystkie kwoty za wizyty i zwraca sumę.
        //      -- podliczania ilości wizyt anulowanych
        //      -- podliczania ilości wizyt nie zrealizowanych ponieważ klient się nie pojawił.


        // UWAGI!:
        // - zakładamy że przy dodawaniu/realizowaniu itp. musimy podawać daty i godziny wizyt.
        // - można odchaczyć/dodawać/usuwać itp. wizyty sprzed 10 lat itd. czyli nie ma mechanizmu sprawdzającego poprawność dat.
        // - wszelkie pytania śmiało piszcie :)

        Map<Klient, Wizyta> mapaWizytOczekujacych = new HashMap<>();
        Map<Klient, Wizyta> mapaWizytZrealizowanych = new HashMap<>();
        Map<Klient, Wizyta> mapaWizytNiezrealizowanych = new HashMap<>();
        Map<Klient, Wizyta> mapaWizytAnulowanych = new HashMap<>();
        SalonFryzjerski salonFryzjerski = new SalonFryzjerski(mapaWizytOczekujacych, mapaWizytZrealizowanych, mapaWizytNiezrealizowanych, mapaWizytAnulowanych);

        Klient k1 = new Klient("im1", 1);
        Klient k2 = new Klient("im2", 2);
        LocalDateTime localDateTime1 = LocalDateTime.of(2019,6,22,12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2019,6,22,13, 00);
        Wizyta w1 = new Wizyta(localDateTime1, UslugiFryzjera.FARBOWANIE);
        Wizyta w2 = new Wizyta(localDateTime2, UslugiFryzjera.OGOLENIE);
        System.out.println("MapaWO size: " + mapaWizytOczekujacych.size());
        salonFryzjerski.dodajWizyte(k1, w1);
        salonFryzjerski.dodajWizyte(k2,w1);


        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Podaj komendę:\na: dodaj wizytę\nb: listuj wizyty oczekujące\n\nw: wyjście");
        char znak = 'a';
        boolean flag = false;
        do {
            System.out.println();
            System.out.println("Wybierz: a(dodaj wizytę), b(listuj oczekujące), c lub w(wyjście).");
            znak = scanner.next().charAt(0);
            switch (znak) {
                case 'a':
                    // określenie klienta
                    String imie = "";
                    int nrTelefonu = 0;
                    System.out.println("Podaj swoje imię:");
                    imie = scanner.next();
                    System.out.println("Podaj nr telefonu:");
                    nrTelefonu = scanner.nextInt();
                    Klient klient = new Klient(imie, nrTelefonu);

                    // określenie daty wizyty
                    System.out.println("Podaj datę wizyty w formacie dd-MM-yyyy:");
                    boolean flagDataWizyty = false;
                    String dataWizyty = "";
                    do {
                        try {
                            dataWizyty = scanner.next("\\d{2}-\\d{2}-\\d{4}$");
                            flagDataWizyty = true;
                        } catch (InputMismatchException ime) {
                            System.err.println("Zły format daty!");
                            scanner.next();
                        }
                    } while (!flagDataWizyty);

                    // określenie czasu wizyty
                    System.out.println("Podaj (pełną) godzinę wizyty z zakresu 10 - 17:");
                    int czasWizytyInt = 0;
                    boolean flagCzasWizyty = false;
                    do {
                        while (!scanner.hasNextInt()) {
                            System.out.println("Wprowadź liczbę całkowitą!");
                            scanner.next();
                        }
                        czasWizytyInt = scanner.nextInt();
                        if (czasWizytyInt < 10) {
                            System.out.println("Salon fryzjerski zamknięty.");
                        } else if (czasWizytyInt > 17) {
                            System.out.println("Salon fryzjerski zamknięty.");
                        } else {
                            flagCzasWizyty = true;
                        }
                    } while (!flagCzasWizyty);
                    String czasWizytyString = String.valueOf(czasWizytyInt);

                    String string3 = ":00";

                    String dataCzasString = dataWizyty + " " + czasWizytyString + string3;
//                    System.out.println(dataCzasString);

                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    LocalDateTime localDateTimeWizyta = LocalDateTime.parse(dataCzasString, dateTimeFormatter);
                    System.out.println(localDateTimeWizyta);

                    // wybór rodzaju usługi fryzjerskiej
                    UslugiFryzjera[] uslugiFryzjeras = UslugiFryzjera.values();
//                    for (char znakChar = 'a'; znak < ('a' + uslugiFryzjeras.length); znak++) {
//                        System.out.print(znak + ": " + uslugiFryzjeras[znak].name() + " ");
//                    }
                    System.out.println("Wybierz rodzaj usługi fryzjerskiej:");
                    char[] tabCharTC = {'a', 'b', 'c'};
                    for (int i = 0; i < uslugiFryzjeras.length; i++) {
                        System.out.print(tabCharTC[i] + ": " + uslugiFryzjeras[i].name() + " ");
                    }
                    System.out.println();
                    System.out.println("Wybierz a, b lub c.");
                    UslugiFryzjera uslugiFryzjera = null;
                    char wybor = scanner.next().charAt(0);
                    switch (wybor) {
                        case 'a':
                            uslugiFryzjera = UslugiFryzjera.FARBOWANIE;
                            break;
                        case 'b':
                            uslugiFryzjera = UslugiFryzjera.OGOLENIE;
                            break;
                        case 'c':
                            uslugiFryzjera = UslugiFryzjera.PODCIECIE;
                            break;
                    }
                    Wizyta wizyta = new Wizyta(localDateTimeWizyta, uslugiFryzjera);

                    salonFryzjerski.dodajWizyte(klient, wizyta);
                    break;
                case 'b':
                    salonFryzjerski.listujMapeWizytOczekujacych();
                    break;
                case 'w':
                    flag = true;
                    break;
                default:
                    if (znak != 'w') {
                        System.out.println("Wybierz: a, b lub w.");
                    }
            }

        } while (!flag);


    }
}
