import java.awt.*;
import java.sql.*;

import javax.swing.*;

public class Pilkarz extends JPanel implements obsluga {
	JTable jt;
	JButton DodajPilkarza;
	JButton UsunPilkarza;
	JButton SzukajPilkarza;
	JButton OdswiezPilkarza;
	ButtonGroup ButtonsPilkarz;
		public Pilkarz() {
			setLayout(new BorderLayout());
			String[] Kolumny = {"Imie", "Nazwisko", "Wiek", "Noga", "ID", "Kadra", "Klub", "Sponsor", "Agent"};
			Object[][] data= wypelnijTabele(baza.connection);
			jt = new JTable(data, Kolumny);
			JScrollPane PilkarzScroll = new JScrollPane(jt);
			add(PilkarzScroll);
			jt.setPreferredScrollableViewportSize(new Dimension(700, 600)); // DZIALA
			jt.setFillsViewportHeight(true);
			DodajPilkarza = new JButton("Dodaj");
			UsunPilkarza = new JButton("Usun");
			OdswiezPilkarza = new JButton("Odswiez");
			SzukajPilkarza = new JButton("Szukaj");
			ButtonsPilkarz = new ButtonGroup();
			//add(ButtonsPilkarz);
			ButtonsPilkarz.add(DodajPilkarza);
			ButtonsPilkarz.add(UsunPilkarza);
			ButtonsPilkarz.add(OdswiezPilkarza);
			ButtonsPilkarz.add(SzukajPilkarza);
			add(DodajPilkarza, BorderLayout.EAST);
			add(UsunPilkarza, BorderLayout.WEST);
			add(OdswiezPilkarza, BorderLayout.NORTH);
			add(SzukajPilkarza, BorderLayout.SOUTH);
			/*add(DodajPilkarza);
			add(UsunPilkarza);
			add(OdswiezPilkarza);
			add(SzukajPilkarza);
			*/
	}
	
	public void wypisz(Connection connection) {
		try {
        	Statement st = connection.createStatement();
        	ResultSet rec = st.executeQuery("select Imie, Nazwisko " + "from Pilkarz " + "order by Nazwisko");
        	while (rec.next()) {
        		System.out.println("NAME:\t\t" + rec.getString(1));
        		System.out.println("SURNAME:\t" + rec.getString(2));
        		System.out.println();
        	}
        	st.close();
        }
        catch(SQLException e) {
        	System.out.println("Dupa");
            e.printStackTrace();
            return;
        }
	}
	
	public Object[][] wypelnijTabele(Connection connection) {
		try {
        	Statement st = connection.createStatement();
        	ResultSet rec = st.executeQuery("select Imie, Nazwisko, Wiek, Noga, ID, Kadra_Narodowa, Klub_Nazwa, Sponsor_Nazwa, Agent_ID2 " + "from Pilkarz " + "order by Nazwisko");
        	Object[][] ToReturn = new Object[61][9];
        	int i=0;
        	while (rec.next()) {
        		ToReturn[i][0] =rec.getString(1);
        		ToReturn[i][1] =rec.getString(2);
        		ToReturn[i][2] =rec.getString(3);
        		ToReturn[i][3] =rec.getString(4);
        		ToReturn[i][4] =rec.getString(5);
        		ToReturn[i][5] =rec.getString(6);
        		ToReturn[i][6] =rec.getString(7);
        		ToReturn[i][7] =rec.getString(8);
        		ToReturn[i][8] =rec.getString(9);
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
	
	public void dodaj(Connection connection, String Imie, String Nazwisko, String Wiek, String Noga, String ID, String Kadra, String Klub, String Sponsor, String Agent) {
		try {
        	Statement st = connection.createStatement();
        	st.executeQuery("insert into Pilkarz values ('"+ Imie + "', '" + Nazwisko + "', '" + Wiek + "', '" + Noga + "', '" + ID + "', '" + Kadra + "', '" + Klub + "', '" + Sponsor + "', '" + Agent + "')" );
        	st.close();
        }
        catch(SQLException e) {
        	System.out.println("Dupa");
            e.printStackTrace();
            return;
	}}
	public void usun(Connection connection, int id) {
		try {
        	Statement st = connection.createStatement();
        	String query = "delete from Pilkarz where ID=" + id;
        	st.executeQuery(query);
        	st.close();
        }
        catch(SQLException e) {
        	System.out.println("Dupa");
            e.printStackTrace();
            return;
	}
	}
	
	public void usun(Connection connection, String imie, String nazwisko) {
		try {
        	Statement st = connection.createStatement();
        	String query = "delete from Pilkarz where Imie='" + imie + "' AND Nazwisko='" + nazwisko + "'";
        	st.executeQuery(query);
        	st.close();
        }
        catch(SQLException e) {
        	System.out.println("Dupa");
            e.printStackTrace();
            return;
	}
	}
	
}
