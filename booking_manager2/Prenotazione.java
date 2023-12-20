class Prenotazione {
   private String nome;
   private String cognome;
   private boolean is_tesserato;

   public Prenotazione(String n, String c, boolean t) {
      this.nome = n;
      this.cognome = c;
      this.is_tesserato = t;
   }

   public Prenotazione(String csv_str) {
      String[] split = csv_str.split(",");
      this.nome = split[0];
      this.cognome = split[1];
      this.is_tesserato = Boolean.parseBoolean(split[2]);
   }

   public String toCsvString() {
      return this.nome + "," + this.cognome + "," + this.is_tesserato;
   }

   public String toString() {
      return this.nome + " " + this.cognome;
   }

   public String getNome() {
      return this.nome;
   }

   public String getCognome() {
      return this.cognome;
   }

   public String getTesserato() {
      return this.is_tesserato ? "Sì" : "No";
   }
}
