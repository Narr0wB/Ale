import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Evento {
    private LocalDate data_evento;
    private ArrayList<Prenotazione> prenotazioni;

    public Evento(LocalDate data, ArrayList<Prenotazione> p) {
        this.data_evento = data;
        this.prenotazioni = p;
    }

    public Evento(LocalDate data) {
        this.data_evento = data;
        this.prenotazioni = new ArrayList<Prenotazione>();
    }

    public Evento(String csv_str) {
        String[] dati = csv_str.split(",");

        this.prenotazioni = new ArrayList<Prenotazione>();
        this.data_evento = LocalDate.parse(dati[0], DateTimeFormatter.ofPattern("dd-MM-uuuu"));

        for(int i = 1; i < dati.length; i += 3) {
            this.prenotazioni.add(new Prenotazione(dati[i] + "," + dati[i + 1] + "," + dati[i + 2]));
        }

    }

    public String toCsvString() {
        if (this.data_evento == null) {
            return "";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            String data = formatter.format(this.data_evento);

            for (Prenotazione p : prenotazioni) {
                data += "," + p.toCsvString();
            }

            return data;
        }
    }

    public void aggiungiPrenotazione(Prenotazione p) {
        this.prenotazioni.add(p);
    }

    LocalDate getData() {
        return this.data_evento;
    }

    ArrayList<Prenotazione> getPrenotazioni() {
        return this.prenotazioni;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        return formatter.format(this.data_evento);
    }
}
