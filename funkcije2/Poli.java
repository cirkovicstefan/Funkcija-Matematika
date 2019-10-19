// Poli.java - Klasa polinoma treæeg reda.

package funkcije2;

class Poli extends Funkcija {

  @Override
  public double f(double x)            // Vrednost polinoma.
    { return((p * x + q) * x + r) * x + s; }

  @Override
  public String toString()            // Tekstualni opis.
    { return "px^3+qx^2+rx+s"; }
}
