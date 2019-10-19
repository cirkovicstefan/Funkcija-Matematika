// Grafik.java - Crtanje grafika funkcija.

package funkcije2;
import java.awt.*; import java.awt.event.*;

public class Grafik extends Frame {

  private Funkcija[] funkcije =                // Postojeæe funkcije.
    { new Poli(), new Oscil(), new Log() };
  private Funkcija fun = funkcije[0];          // Funkcija koja se koristi.

  private Platno platno = new Platno();        // Platno po kome se crta.

  private class DogadjajiTastera extends KeyAdapter {  // Ponovno crtanje
    @Override                                          //   pritiskom na
    public void keyPressed(KeyEvent dog) {             //   'enter'.
      if (dog.getKeyCode() == KeyEvent.VK_ENTER)
        platno.repaint();
    }
  }

  private class DogadjajiZize extends FocusAdapter {   // Obeležavanje
    @Override                                          //   teksta pri
    public void focusGained(FocusEvent dog) {          //   dobijanju žiže.
      ((TextComponent)dog.getComponent()).selectAll();
    }
  }

  // Rasporeðivaè i osluškivaèi za ploèe za unos parametara.
  private FlowLayout       raspor     = new FlowLayout(FlowLayout.RIGHT);
  private DogadjajiTastera dogTastera = new DogadjajiTastera();
  private DogadjajiZize    dogZize    = new DogadjajiZize();

  private class Param extends Panel {          // PLOÈA ZA UNOS PARAMETRA:

    private TextField par = new TextField(5);  // - polje za vrednost,
    {                                          // - inicijalizatorski blok,
      setLayout(raspor);
      par.addKeyListener(dogTastera);
      par.addFocusListener(dogZize);
    }

    public Param(String natpis, double vr) {   // - inicijalizacija
      add(new Label(natpis, Label.RIGHT));     //   realnim brojem,
      add(par);
      par.setText(Double.toString(vr));
    }
    
    public Param(String natpis, int vr) {      // - inicijalizacija
      add(new Label(natpis, Label.RIGHT));     //   celim brojem,
      add(par);
      par.setText(Integer.toString(vr));
    }
    
    public double Double() {                   // - dohvatanje kao
      return Double.parseDouble(par.getText());//   realan broj,
    }

    public int Int() {                         // - dohvatanje kao
      return Integer.parseInt(par.getText());  //   ceo broj.
    }
  } // class Param
                                               // Ploèe za parametre:
  private Param Xmin = new Param("Xmin", -10), // - opseg x-a,
                Xmax = new Param("Xmax",  10),
                Ymin = new Param("Ymin", -10), // - opseg y-a,
                Ymax = new Param("Ymax",  10),
                N    = new Param("N"   , 300), // - broj taèaka,
                P    = new Param("P"   ,   1), // - koeficijenti za
                Q    = new Param("Q"   ,   2), //   funkcije.
                R    = new Param("R"   ,   3),
                S    = new Param("S"   ,   4);
  {
    Color boja = new Color(255, 240, 240);   // Postavljanje boje
    Xmax.setBackground(boja);                //   podloge ploèa za
    Xmin.setBackground(boja);                //   opsege koordinata.
    Ymax.setBackground(boja);
    Ymin.setBackground(boja);
  }

  private double xmin, xmax, ymin, ymax;     // Parametri crtanja.
  private int    sir, vis, n;
  private double p, q, r, s;                 // Koeficijenti funkcija.

  private void uzmiParametre() {             // Uzimanje parametara.
    xmin = Xmin.Double();     xmax = Xmax.Double();
    ymin = Ymin.Double();     ymax = Ymax.Double();
    sir  = platno.getWidth(); vis  = platno.getHeight();
    n = N.Int();
    p = P.Double(); q = Q.Double(); r = R.Double(); s = S.Double();
  }

  private Label X = new Label("", Label.RIGHT),    // Natpisi za prikaz
                Y = new Label("", Label.RIGHT);    //   koordinata.
  Panel  ploKoord = new Panel();             // Ploèa za prikaz koordinata.

  private Label greska = new Label("GRESKA!", Label.CENTER); // Natpis za
  {                                                          //   grešku.
    greska.setForeground(Color.RED);
    greska.setFont(new Font(null, Font.BOLD, 20));
    greska.setVisible(false);
  }

  private class Platno extends Canvas {      // PLATNO PO KOME SE CRTA.
    private int a(double x)                  // Preslikavanje koordinate x.
      { return (int)((x-xmin) / (xmax-xmin) * (sir-1)); }

    private int b(double y)                  // Preslikavanje koordinate y.
      { return (int)((ymax-y) / (ymax-ymin) * (vis-1)); }

    @Override
    public void paint(Graphics g) {        // Crtanje krive:
      try {
        greska.setVisible(false);          // - uklanjanje poruke o grešci,
        g.setColor(new Color(240,240,240));  // - popunjavanje podloge,
        g.fillRect(0, 0, getWidth(), getHeight());
        uzmiParametre();                   // - uzimanje parametara,
        fun.postavi(p, q, r, s);           // - postavljanje koeficijenata,
        g.setColor(Color.RED);             // - crtanje koordinatnih osa,
        g.drawLine(0, b(0), sir-1, b(0));
        g.drawLine(a(0), 0, a(0), vis-1);
        double dx = (xmax - xmin) / n;     // - korak duž x-ose,
        g.setColor(Color.BLACK);           // - crtanje funkcije,
        int a0 = 0, b0 = 0;
        boolean prva = true;
        for (double x=xmin; x<=xmax; x+=dx) {
          double y = fun.f(x);
          if (!Double.isNaN(y)) {
            int a = a(x), b = b(y);
            if (!prva) g.drawLine(a0, b0, a, b); else prva = false;
            a0 = a; b0 = b;
          } else prva = true;
        }
      } catch (NumberFormatException gr) { // - obrada greške.
        greska.setVisible(true);
        X.setText(""); Y.setText(""); ploKoord.validate();
      }
    } // paint()

    private class PokretMisa extends MouseMotionAdapter {  // Prikaz koor-
      @Override                                            //   dinata pri
      public void mouseMoved(MouseEvent dog) {             //   pomeranju
        if (fun != null) {                                 //   miša.
          int a = dog.getX();
          double x = xmin + a * (xmax - xmin) / (sir - 1),
                 y = fun.f(x);
          X.setText(String.format("x: %.4g", x));
          Y.setText(String.format("y: %.4g", y));
          ploKoord.validate();
        }
      }
    } // class PokretMisa

    private Platno()                       // Inicijalizacija platna.
      { addMouseMotionListener(new PokretMisa()); }

  } // class Platno

  private class Izbor extends Choice { // PADAJUÆA LISTA ZA IZBOR FUNKCIJE.
    public Izbor() {
      for (Funkcija f: funkcije) add(f.toString());  // Popunjavanje liste.
      select(0);
      addItemListener(new ItemListener() {        // Obrada promene stavke.
        public void itemStateChanged(ItemEvent dog) {
          fun = funkcije[getSelectedIndex()];
        }
      });
    }
  } // class Izbor
 
  private void popuniProzor() {                    // Popunjavanje prozora:
    add(platno, "Center");                         // - platno za crtanje,

    Panel ploca = new Panel(new BorderLayout());   // - zapadna ploèa za
    add(ploca, "West");                            //   unos podataka,
    ploca.setBackground(new Color(255, 255, 240));
    Panel plo = new Panel(new GridLayout (0, 1));
    ploca.add(plo, "North");
    plo.add(Ymax); plo.add(P); plo.add(Q); plo.add(R); plo.add(S);
    plo.add(new Izbor());
    plo = new Panel(new GridLayout(0, 1)); ploca.add(plo, "South");
    plo.add(greska); plo.add(N); plo.add(Ymin);

    ploca = new Panel(new BorderLayout());         // - južna ploèa za unos
    add(ploca, "South");                           //   i prikaz podataka,
    ploca.setBackground(new Color(255, 255, 240));
    ploca.add(Xmin, "West"); ploca.add(Xmax, "East");
    ploca.add(ploKoord, "Center");
    Button crtaj = new Button("Crtaj");
    crtaj.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent dog) { platno.repaint(); }
    });
    ploKoord.add(crtaj); ploKoord.add(X); ploKoord.add(Y);
  } // popuniProzor()

  public Grafik() {                                // Inicijalizacija.
    super("Crtanje");
    setSize(600, 400); setLocationRelativeTo(null);
    popuniProzor();
    setVisible(true);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent dog) { dispose(); }
    });
  } // Grafik()

} // class Grafik
