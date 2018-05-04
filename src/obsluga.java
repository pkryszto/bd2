import java.sql.Connection;

public interface obsluga {
	public void wypisz(Connection connection);
	public void dodaj(Connection connection, String Imie, String Nazwisko, String Wiek, String Noga, String ID, String Kadra, String Klub, String Sponsor, String Agent);
	public void usun(Connection connection, int id);
	public void usun(Connection connection, String imie, String nazwisko);
}
