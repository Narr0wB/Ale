import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.ExpandVetoException;

import java.awt.*;  

public class Manager extends JFrame {

private DefaultListModel<Evento> eventi;

   private JList eventi_list_view;
   private JList prenotazioni_list_view;

   private JMenuBar app_bar;

   private JPanel prenotazione_panel;

   private JLabel n_view;
   private JLabel c_view;
   private JLabel t_view;

   private JFrame evento_aggiungi_frame;
   private JFrame prenotazione_aggiungi_frame;


   public Manager() {
      init_lists();
      init_menu_bar();
      init_evento_frame();
      init_prenotazione_frame();

      prenotazione_panel = new JPanel();

      JLabel nome = new JLabel("Nome: ");
      n_view = new JLabel();
      JLabel cognome = new JLabel("Cognome: ");
      c_view = new JLabel();
      JLabel tesserato = new JLabel("Tesserato: ");
      t_view = new JLabel();

      prenotazione_panel = new JPanel();
      GridLayout gl = new GridLayout(3, 2);
      gl.setHgap(10);
      prenotazione_panel.setLayout(gl);

      prenotazione_panel.add(nome);
      prenotazione_panel.add(n_view);
      prenotazione_panel.add(cognome);
      prenotazione_panel.add(c_view);
      prenotazione_panel.add(tesserato);
      prenotazione_panel.add(t_view);

      prenotazione_panel.setVisible(false);

      this.add(prenotazione_panel);

      setSize(1200, 600);
      setLayout(new FlowLayout(FlowLayout.LEFT));
      setVisible(true);

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
   }

   private void init_lists() {
      eventi = new DefaultListModel<Evento>();

      eventi_list_view = new JList<Evento>(eventi);
      prenotazioni_list_view = new JList<Prenotazione>();

      Dimension list_dim = new Dimension(400, 500);

      eventi_list_view.setPreferredSize(list_dim);
      prenotazioni_list_view.setPreferredSize(list_dim);

      eventi_list_view.setSelectionModel(new DefaultListSelectionModel(){

         @Override
         public void setSelectionInterval(int index0, int index1) {
             if (index0==index1) {
                 if (isSelectedIndex(index0)) {
                     removeSelectionInterval(index0, index0);
                     return;
                 }
             }
             super.setSelectionInterval(index0, index1);
         }

         @Override
         public void addSelectionInterval(int index0, int index1) {
             if (index0==index1) {
                 if (isSelectedIndex(index0)) {
                     removeSelectionInterval(index0, index0);
                     return;
                 }
             super.addSelectionInterval(index0, index1);
             }
         }

     });

      prenotazioni_list_view.setSelectionModel(new DefaultListSelectionModel(){

      @Override
      public void setSelectionInterval(int index0, int index1) {
          if (index0==index1) {
              if (isSelectedIndex(index0)) {
                  removeSelectionInterval(index0, index0);
                  return;
              }
          }
          super.setSelectionInterval(index0, index1);
      }

      @Override
      public void addSelectionInterval(int index0, int index1) {
          if (index0==index1) {
              if (isSelectedIndex(index0)) {
                  removeSelectionInterval(index0, index0);
                  return;
              }
          super.addSelectionInterval(index0, index1);
          }
      }

  });

      eventi_list_view.addListSelectionListener(new ListSelectionListener() {

         @Override
         public void valueChanged(ListSelectionEvent arg) {
            if (!arg.getValueIsAdjusting()) {

               JList lista_eventi = (JList) arg.getSource();
               Evento e = (Evento) lista_eventi.getSelectedValue();
               DefaultListModel<Prenotazione> prenotazioni_model = new DefaultListModel<Prenotazione>();

               if (e == null) {
                  prenotazioni_list_view.setModel(prenotazioni_model);

                  return;
               }

               for (Prenotazione p : e.getPrenotazioni()) {
                  prenotazioni_model.addElement(p);
               }
      
               prenotazioni_list_view.setModel(prenotazioni_model);
            }
         }
      });

      prenotazioni_list_view.addListSelectionListener(new ListSelectionListener() {

         @Override
         public void valueChanged(ListSelectionEvent arg) {

            
            if (!arg.getValueIsAdjusting()) {
               JList lista = (JList)arg.getSource();
               Prenotazione p = (Prenotazione)lista.getSelectedValue();

               if (p == null) {
                  prenotazione_panel.setVisible(false);

                  return;
               }
      
               prenotazione_panel.setVisible(true);
               n_view.setText(p.getNome());
               c_view.setText(p.getCognome());
               t_view.setText(p.getTesserato());
            }
      
         }
      });

      this.add(eventi_list_view);
      this.add(prenotazioni_list_view);
   }

   private void init_menu_bar() {
      app_bar = new JMenuBar();

      JMenu file = new JMenu("File");
      JMenu aggiungi = new JMenu("Aggiungi...");

      JMenuItem salva = new JMenuItem("Salva");
      JMenuItem carica = new JMenuItem("Carica");

      salva.addActionListener(e -> salva_listener());
      carica.addActionListener(e -> carica_listener());

      file.add(salva);
      file.add(carica);

      JMenuItem evento = new JMenuItem("Evento");
      JMenuItem prenotazione = new JMenuItem("Prenotazione");

      evento.addActionListener(e -> aggiungi_evento_menu_listener());
      prenotazione.addActionListener(e -> aggiungi_prenotazione_menu_listener());

      aggiungi.add(evento);
      aggiungi.add(prenotazione);

      app_bar.add(file);
      app_bar.add(aggiungi);

      this.setJMenuBar(app_bar);
   }

   private void init_evento_frame() {
      evento_aggiungi_frame = new JFrame();

      evento_aggiungi_frame.setSize(new Dimension(800, 500));
      evento_aggiungi_frame.setLayout(new FlowLayout());

      JTextField tf = new JTextField();
      tf.setPreferredSize(new Dimension(300, 30));


      JLabel info = new JLabel("Inserire la data dell'evento (GG-MM-AAAA)");
      JButton aggiungi_btn = new JButton("Aggiungi");

      aggiungi_btn.addActionListener(e -> aggiungi_evento_btn_listener(tf));

      evento_aggiungi_frame.add(tf);
      evento_aggiungi_frame.add(info);
      evento_aggiungi_frame.add(aggiungi_btn);


      evento_aggiungi_frame.setVisible(false);
   }

   private void init_prenotazione_frame() {
      prenotazione_aggiungi_frame = new JFrame();

      prenotazione_aggiungi_frame.setSize(new Dimension(800, 500));
      prenotazione_aggiungi_frame.setLayout(new FlowLayout());

      JLabel nome = new JLabel("Nome: ");
      JLabel cognome = new JLabel("Cognome: ");
      JLabel tesserato = new JLabel("Tesserato: ");

      JTextField nome_tf = new JTextField();
      JTextField cognome_tf = new JTextField();
      JCheckBox tesserato_cb = new JCheckBox();

      nome_tf.setPreferredSize(new Dimension(200, 30));
      cognome_tf.setPreferredSize(new Dimension(200, 30));

      JButton aggiungi = new JButton("Aggiungi");

      aggiungi.addActionListener(e -> aggiungi_prenotazione_btn_listener(nome_tf, cognome_tf, tesserato_cb));

      prenotazione_aggiungi_frame.add(nome);
      prenotazione_aggiungi_frame.add(nome_tf);

      prenotazione_aggiungi_frame.add(cognome);
      prenotazione_aggiungi_frame.add(cognome_tf);

      prenotazione_aggiungi_frame.add(tesserato);
      prenotazione_aggiungi_frame.add(tesserato_cb);

      prenotazione_aggiungi_frame.add(aggiungi);
   }

   private void aggiungi_evento_menu_listener() {
      evento_aggiungi_frame.setVisible(true);
   }

   private void aggiungi_prenotazione_menu_listener() {
      prenotazione_aggiungi_frame.setVisible(true);
   }

   private void aggiungi_evento_btn_listener(JTextField tf) {
      String text = tf.getText();

      tf.setText("");

      LocalDate data = null;

      try {
         data = LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-uuuu"));
      } catch (Exception e) {
         System.out.println("ERRORE: FORMATO DATA INVALIDO");

         return;
      }

      eventi.addElement(new Evento(data));

      evento_aggiungi_frame.setVisible(false);
   }

   private void aggiungi_prenotazione_btn_listener(JTextField n, JTextField c, JCheckBox t) {
      Evento e = (Evento) eventi_list_view.getSelectedValue();

      if (e == null) {
         return;
      }

      String nome = n.getText();
      String cognome = c.getText();
      boolean tesserato = t.isSelected();

      n.setText("");
      c.setText("");
      t.setSelected(false);

      e.aggiungiPrenotazione(new Prenotazione(nome, cognome, tesserato));

      prenotazione_aggiungi_frame.setVisible(false);
   }
   
   private void salva_listener() {
      if (eventi.size() == 0) {
         return;
      }
      
      JFileChooser fc = new JFileChooser();

      int retVal = fc.showOpenDialog(this);

      if (retVal == JFileChooser.APPROVE_OPTION) {
         try {
            scriviFile(fc.getSelectedFile().getPath());
         } catch (Exception e) {
            System.out.println("IMPOSSIBILE SCRIVERE FILE");
            return;
         }
      }
   }

   private void carica_listener() {
      JFileChooser fc = new JFileChooser();

      int retVal = fc.showOpenDialog(this);

      if (retVal == JFileChooser.APPROVE_OPTION) {
         try {
            caricaFile(fc.getSelectedFile().getPath());
         } catch (Exception e) {
            System.out.println("IMPOSSIBILE LEGGERE FILE");
            return;
         }
      }
   }  

   public void caricaFile(String csv_path) throws IOException {
      File csv = new File(csv_path);

      Scanner sc = new Scanner(csv);
      
      while (sc.hasNextLine()) {
         String data = sc.nextLine();

         eventi.addElement(new Evento(data));
      }

      sc.close();
   }

   public void scriviFile(String csv_path) throws IOException {
      FileOutputStream fos = new FileOutputStream(csv_path);

      for (int i = 0; i < eventi.size(); ++i) {
         Evento e = eventi.elementAt(i);

         fos.write((e.toCsvString() + "\n").getBytes());
      }

      fos.close();
   }

   // public void aggiungiEvento(LocalDate d, ArrayList<Prenotazione> p) {
   //    eventi.addElement(new Evento(d, p));
   // }

   // public void aggiungiPrenotazioneEvento(LocalDate d, Prenotazione p) {
   //    for (int i = 0; i < eventi.size(); i++) {
   //       Evento e = eventi.elementAt(i);

   //       if (e.getData() == d) {
   //             e.aggiungiPrenotazione(p);
   //       }
   //    }
   // }

   public static void main(String[] args) {
      Manager m = new Manager();

   }

}
