import javax.swing.*;
public class InterMenu extends JFrame {
	public InterMenu() {
		super("Baza danych");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(80, 60);
		JPanel Pilkarze = new JPanel();
		JPanel Kluby = new JPanel();
		JPanel Trenerzy = new JPanel();
		JPanel Agenci = new JPanel();
		JPanel KadryNarodowe = new JPanel();
		JTabbedPane Baza = new JTabbedPane();
		Pilkarz p = new Pilkarz();
		Klub k = new Klub();
		Pilkarze.add(p);
		//Pilkarze.add(Pilkarz.PilkarzTab);
		//add(Pilkarz.PilkarzScroll);
		Kluby.add(k);
		//Kluby.add(Klub.KlubScroll);
		Baza.add("Pilkarze", Pilkarze);
		Baza.addTab("Kluby", Kluby);
		Baza.addTab("Trenerzy", Trenerzy);
		Baza.addTab("Agenci", Agenci);
		Baza.addTab("Kadry Narodowe", KadryNarodowe);
		add(Baza);
		setVisible(true);
	}
}
