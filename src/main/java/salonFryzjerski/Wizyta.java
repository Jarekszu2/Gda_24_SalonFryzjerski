package salonFryzjerski;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Wizyta {
    private LocalDateTime terminWizyty;
    private UslugiFryzjera uslugiFryzjera;
    private boolean realizacjaWizyty;
    private boolean anulowanieWizyty;
    private int kwotaPotracenia;

    public Wizyta(LocalDateTime terminWizyty, UslugiFryzjera uslugiFryzjera) {
        this.terminWizyty = terminWizyty;
        this.uslugiFryzjera = uslugiFryzjera;
        this.realizacjaWizyty = false;
        this.anulowanieWizyty = false;
        this.kwotaPotracenia = 0;
    }
}
