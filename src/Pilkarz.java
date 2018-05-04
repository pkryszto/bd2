import java.sql.*;

public class Pilkarz implements obsluga {
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
