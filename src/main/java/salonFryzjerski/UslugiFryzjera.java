package salonFryzjerski;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
public enum UslugiFryzjera {
    FARBOWANIE(50),
    PODCIECIE(30),
    OGOLENIE(40);

    private int koszt;

    public int getKoszt() {
        return koszt;
    }


}
