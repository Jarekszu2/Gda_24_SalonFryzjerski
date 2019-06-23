package salonFryzjerski;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        LocalTime czasWizyty = LocalTime.of(1,1);
        System.out.println(czasWizyty);

        Scanner scanner = new Scanner(System.in);
        String czas = "";
        boolean flag = false;
        System.out.println("Podaj czas w formacie: HH:mm");
        do {
            try {
                czas = scanner.next("\\d{2}:00$");
                flag = true;
            } catch (InputMismatchException ime) {
                System.err.println("Zły format");
                scanner.next();
            }
        } while (!flag);
        System.out.println(czas);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime czasLT = LocalTime.parse(czas, dateTimeFormatter);
        System.out.println("czasLT: " + czasLT);
    }
}
