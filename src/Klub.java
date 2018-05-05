import java.awt.*;
import java.sql.*;

import javax.swing.*;

public class Klub extends JPanel implements obsluga {
	JTable jt;
	JButton DodajKlub;
	JButton UsunKlub;
	JButton SzukajKlub;
	JButton OdswiezKlub;
	public Klub() {
		setLayout(new BorderLayout());
		String[] Kolumny = {"Nazwa", "Rok Powstania", "Bud¿et", "Sponsor", "Trener"};
		Object[][] data= wypelnijTabele(baza.connection);
		jt = new JTable(data, Kolumny);
		JScrollPane KlubScroll = new JScrollPane(jt);
		add(KlubScroll);
		jt.setPreferredScrollableViewportSize(new Dimension(500, 600)); // DZIALA
		jt.setFillsViewportHeight(true);
		DodajKlub = new JButton("Dodaj");
		UsunKlub = new JButton("Usun");
		OdswiezKlub = new JButton("Odswiez");
		SzukajKlub = new JButton("Szukaj");
		add(DodajKlub, BorderLayout.EAST);
		add(UsunKlub, BorderLayout.WEST);
		add(OdswiezKlub, BorderLayout.NORTH);
		add(SzukajKlub, BorderLayout.SOUTH);
	}
	
	public void wypisz(Connection connection) {
		
	}
	public void dodaj(Connection connection, String Imie, String Nazwisko, String Wiek, String Noga, String ID, String Kadra, String Klub, String Sponsor, String Agent) {
		}
	public void usun(Connection connection, int id) {
		
	}
	
	public void usun(Connection connection, String imie, String nazwisko) {
	
	}
	public Object[][] wypelnijTabele(Connection connection) {
		try {
        	Statement st = connection.createStatement();
        	ResultSet rec = st.executeQuery("select Nazwa, Rok_Powstania, Budzet, Sponsor_Nazwa, Trener_ID " + "from Klub " + "order by Nazwa");
        	Object[][] ToReturn = new Object[10][5];
        	int i=0;
        	while (rec.next()) {
        		ToReturn[i][0] =rec.getString(1);
        		ToReturn[i][1] =rec.getString(2);
        		ToReturn[i][2] =rec.getString(3);
        		ToReturn[i][3] =rec.getString(4);
        		ToReturn[i][4] =rec.getString(5);
        		i++;
        	}
        	st.close();
        	return ToReturn;
        }
        catch(SQLException e) {
        	System.out.println("Dupa");
            e.printStackTrace();
            Object[][] ExReturn = new Object[1][5];
            ExReturn[0][0]= "Dupa";
            ExReturn[0][1]= "Dupa";
            ExReturn[0][2]= "Dupa";
            ExReturn[0][3]= "Dupa";
            ExReturn[0][4]= "Dupa";
            return ExReturn;
           // return {{"Adam", "Dupa", "19", "L", "1234"}, {"Izabela", "Lecka", "23", "P", "4321"}};
        }
	}
}
