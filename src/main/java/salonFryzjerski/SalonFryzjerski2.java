package salonFryzjerski;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SalonFryzjerski2 {
    private List<UslugiSalonu> listaWizytOczekujacych;
    private List<UslugiSalonu> listaWizytZrealizowanych;
    private List<UslugiSalonu> listaWizytNiezrealizowanych;
    private List<UslugiSalonu> listaWizytAnulowanych;

    public void dodajWizyteSF2(Klient klient, Wizyta wizyta) {
        boolean timeReserved = false;
        for (int i = 0; i < listaWizytOczekujacych.size(); i++) {
            if (wizyta.getTerminWizyty().equals(listaWizytOczekujacych.get(i).getWizyta().getTerminWizyty())) {
                timeReserved = true;
                break;
            }
        }
        if (timeReserved) {
            System.out.println("Termin zarezerwowany. Wybierz inny termin.");
        } else {
            UslugiSalonu uslugiSalonu = new UslugiSalonu(klient, wizyta);
            listaWizytOczekujacych.add(uslugiSalonu);
            System.out.println("Wizyta umówiona.");
        }
    }

    public void listujListeUslugiSalonu(List<UslugiSalonu> list) {
        for (int i = 0; i < list.size(); i++) {
            LocalDateTime localDateTime = list.get(i).getWizyta().getTerminWizyty();
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime = localDateTime.toLocalTime();
            System.out.println(list.get(i));
//            System.out.println((i +1) + ". " + list.get(i).getKlient().getImie()
//                    + " " + localDate + " " + localTime + " " + list.get(i).getWizyta().getUslugiFryzjera().name());
        }
    }

    public void zrealizowanieWizyty(String imie, LocalDateTime termin, int kwotaPotracenia) {
        boolean czyJestTakaWizyta = false;
        for (int i = 0; i < listaWizytOczekujacych.size(); i++) {
            if(listaWizytOczekujacych.get(i).getKlient().getImie().equalsIgnoreCase(imie) && listaWizytOczekujacych.get(i).getWizyta().getTerminWizyty().equals(termin)) {
                Klient klient = listaWizytOczekujacych.get(i).getKlient();
                Wizyta wizyta = listaWizytOczekujacych.get(i).getWizyta();
                wizyta.setKwotaPotracenia(kwotaPotracenia);
                wizyta.setRealizacjaWizyty(true);
                UslugiSalonu uslugiSalonu = new UslugiSalonu(klient, wizyta);
                listaWizytZrealizowanych.add(uslugiSalonu);
                listaWizytOczekujacych.remove(i);
                i--;
                czyJestTakaWizyta = true;
                System.out.println("Usługa zrealizowana.");
            }
        }
        if (!czyJestTakaWizyta) {
            System.out.println("Nie ma takiej umówionej wizyty.");
        }
    }

    public boolean sprawdzenieCzyJestNaLiscieOczekujacych(String name, LocalDateTime termin) {
        boolean isPresent = false;
        for (int i = 0; i < listaWizytOczekujacych.size(); i++) {
            if ((listaWizytOczekujacych.get(i).getKlient().getImie().equals(name)) && listaWizytOczekujacych.get(i).getWizyta().getTerminWizyty().equals(termin)) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    public void anulowanieWizyty(String name, LocalDateTime termin) {
        if (sprawdzenieCzyJestNaLiscieOczekujacych(name, termin)) {
            for (int i = 0; i < listaWizytOczekujacych.size(); i++) {
                if ((listaWizytOczekujacych.get(i).getKlient().getImie().equals(name)) && listaWizytOczekujacych.get(i).getWizyta().getTerminWizyty().equals(termin)) {
                    UslugiSalonu uslugiSalonu = listaWizytOczekujacych.get(i);
                    listaWizytAnulowanych.add(uslugiSalonu);
                    listaWizytOczekujacych.remove(i);
                    i--;
                    System.out.println("Wizyta anulowana.");
                }
            }
        }
    }

    public void listaUslugFryzjera() {
        UslugiFryzjera[] uslugiFryzjera = UslugiFryzjera.values();
        for (int i = 0; i < uslugiFryzjera.length; i++) {
            System.out.println((i + 1) + ". " + uslugiFryzjera[i].name());
        }
    }

    public void czyWizytaAktualna(String name, LocalDateTime termin) {
        if (sprawdzenieCzyJestNaLiscieOczekujacych(name, termin)) {
            System.out.println("Wizyta aktualna");
        } else {
            List<UslugiSalonu> listaKlienta = new ArrayList<>();
            for (int i = 0; i < listaWizytOczekujacych.size(); i++) {
                if (listaWizytOczekujacych.get(i).getKlient().getImie().equalsIgnoreCase(name)) {
                    listaKlienta.add(listaWizytOczekujacych.get(i));
                }
            }

            if (listaKlienta.size() == 0) {
                System.out.println("Klient " + name + " nie ma umówionych wizyt.");
            } else {
                System.out.println("Umówione terminy wizyt:");
                for (int i = 0; i < listaKlienta.size(); i++) {
                    System.out.println((i + 1) + ". " + listaKlienta.get(i).getWizyta().getTerminWizyty());
                }
            }
        }
    }
}
