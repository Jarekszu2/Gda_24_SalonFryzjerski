package salonFryzjerski;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {

        List<UslugiSalonu> listaWizytOczekujacych = new ArrayList<>();
        List<UslugiSalonu> listaWizytZrealizowanychych = new ArrayList<>();
        List<UslugiSalonu> listaWizytNiezrealizowanych = new ArrayList<>();
        List<UslugiSalonu> listaWizytAnulowanych = new ArrayList<>();
        SalonFryzjerski2 salonFryzjerski2 = new SalonFryzjerski2(listaWizytOczekujacych, listaWizytZrealizowanychych, listaWizytNiezrealizowanych, listaWizytAnulowanych);


        Klient k1 = new Klient("im1", 1);
        Klient k2 = new Klient("im2", 2);
        LocalDateTime localDateTime1 = LocalDateTime.of(2019,6,22,12, 00);
        LocalDateTime localDateTime2 = LocalDateTime.of(2019,6,22,13, 00);
        Wizyta w1 = new Wizyta(localDateTime1, UslugiFryzjera.FARBOWANIE);
        Wizyta w2 = new Wizyta(localDateTime2, UslugiFryzjera.OGOLENIE);
//        UslugiSalonu us1 = new UslugiSalonu(k1,w1);
//        UslugiSalonu us2 = new UslugiSalonu(k2,w1);
        salonFryzjerski2.dodajWizyteSF2(k1, w1);
        salonFryzjerski2.dodajWizyteSF2(k2,w1);
        salonFryzjerski2.dodajWizyteSF2(k2,w2);


        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Podaj komendę:\na: dodaj wizytę\nb: listuj wizyty oczekujące\nc: realizacja wizyty" +
                "\nd: listuj wizyty zrealizowane\n e: anulowanie wizyty\nf: listuj wizyty anulowane\n\nw: wyjście");
        char znak = 'a';
        boolean flag = false;
        do {
            System.out.println();
            System.out.println("Wybierz: a(dodaj wizytę), b(listuj oczekujące), c(realizacja), d(listuj zrealizowane), e(anulowanie)," +
                    "\n         f(listuj anulowane) lub w(wyjście).");
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
                    LocalDateTime localDateTimeWizyta = getLocalDateTime(scanner);

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

                    salonFryzjerski2.dodajWizyteSF2(klient, wizyta);
                    break;
                case 'b':
                    System.err.println("Usługi oczekujące na realizację:");
                    salonFryzjerski2.listujListeUslugiSalonu(listaWizytOczekujacych);
                    break;
                case 'c':
                    System.out.println("Podaj imię:");
                    String imieC = scanner.next();
                    LocalDateTime localDateTimeRealizacja = getLocalDateTime(scanner);
                    System.out.println("Podaj kwotę potrącenia:");
                    int kwotaPotracenia = scanner.nextInt();
                    salonFryzjerski2.zrealizowanieWizyty(imieC, localDateTimeRealizacja, kwotaPotracenia);
                    break;
                case 'd':
                    System.out.println("Usługi zrealizowane:");
                    salonFryzjerski2.listujListeUslugiSalonu(listaWizytZrealizowanychych);
                    break;
                case 'e':
                    System.out.println("Anulowanie wizyty:");
                    // określenie wizyty
                    System.out.println();
                    System.out.println("Podaj imię:");
                    String nameE = scanner.next();
                    LocalDateTime localDateTimeE = getLocalDateTime(scanner);
                    salonFryzjerski2.anulowanieWizyty(nameE, localDateTimeE);
                    break;
                case 'f':
                    System.out.println("Usługi anulowane:");
                    salonFryzjerski2.listujListeUslugiSalonu(listaWizytAnulowanych);
                    break;
                case 'w':
                    flag = true;
                    break;
                default:
                    if (znak != 'w') {
                        System.out.println("Wybierz: a, b, c, d lub w.");
                    }
            }

        } while (!flag);

    }

    private static LocalDateTime getLocalDateTime(Scanner scanner) {
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
        return LocalDateTime.parse(dataCzasString, dateTimeFormatter);
    }
}
