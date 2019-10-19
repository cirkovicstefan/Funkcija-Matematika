// Funkcija.java – Apstraktna klasa funkcija.

package funkcije2;

abstract class Funkcija {
  protected double p, q, r, s;          // Koeficijenti funkcije.

  public void postavi(double a, double b, double c, double d)
    { p = a; q = b; r = c; s = d; }    // Postavljanje koeficijenata.

  public abstract double f(double x);  // Vrednost funkcije.

  @Override
  public abstract String toString();  // Tekstualni opis funkcije.
}
