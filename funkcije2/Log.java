// Log.java - Klasa logaritamskih funkcija.

package funkcije2;

class Log extends Funkcija {

  @Override
  public double f(double x)           // Vrednost funkcije.
    { return p + q * Math.log(x); }

  @Override
  public String toString()            // Tekstualni opis.
    { return "p + q ln x"; }
}
