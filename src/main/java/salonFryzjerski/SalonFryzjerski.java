package salonFryzjerski;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SalonFryzjerski {
    private Map<Klient, Wizyta> mapaWizytOczekujacych;
    private Map<Klient, Wizyta> mapaWizytZrealizowanych;
    private Map<Klient, Wizyta> mapaWizytNiezrealizowanych;
    private Map<Klient, Wizyta> mapaWizytAnulowanych;

    public void dodajWizyte(Klient klient, Wizyta wizyta) {
        Collection<Map.Entry<Klient, Wizyta>> entries = mapaWizytOczekujacych.entrySet();
        List<Integer> integerList = new ArrayList<>();
        for (Map.Entry<Klient, Wizyta> entry : entries) {
            if (entry.getValue().getTerminWizyty() == wizyta.getTerminWizyty()) {
                integerList.add(1);
            } else {
                integerList.add(0);
            }
        }
        int suma = 0;
        for (int i = 0; i < integerList.size(); i++) {
            suma += integerList.get(i);
        }

        if (suma > 0) {
            System.out.println("Ten termin jest już zarezerwowany!");
        } else {
            mapaWizytOczekujacych.put(klient, wizyta);
            System.out.println("Umówiono wizytę.");
        }
    }

    public void dodajWizyte2(Klient klient, Wizyta wizyta) {
        mapaWizytOczekujacych.put(klient, wizyta);
    }

    public void listujMapeWizytOczekujacych() {
        if (mapaWizytOczekujacych.size() == 0) {
            System.out.println("Nie ma oczekujacych wizyt.");
        } else {
            mapaWizytOczekujacych.forEach((k, w) -> System.out.println(k.getImie() + " " + w.getTerminWizyty()));
        }
    }
}
