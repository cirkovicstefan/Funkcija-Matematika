// Oscil.java - Klasa prigušenih oscilacija.

package funkcije2;

class Oscil extends Funkcija {

  @Override
  public double f(double x)            // Vrednost funkcije.
    { return p * Math.exp(q*x) * Math.sin(r*x+s); }

  @Override
  public String toString()            // Tekstualni opis.
    { return "p e^(qx) sin(rx+s)"; }
}
