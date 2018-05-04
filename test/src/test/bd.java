package test;

import java.sql.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//import oracle.jdbc.pool.OracleDataSource;

public class bd extends JFrame{
    private Connection conn;
    private Statement stmt;
    private ResultSet rset;
    private JFormattedTextField communicate;
    private TextField parametrField;
    private TextField nameField;
    private TextField changeField;
    private JTextArea terminal;
    private JFormattedTextField title;
    private JScrollPane scrollPane;
    private JButton minimize, exit1, connect,
        exit, button_5, button_6, button_7, button_8,
        insert, delete, modyf;
    public static final int BUTTON_WIDTH = 180;
    public static final int BUTTON_HEIGHT = 28;
    public static final int W_WIDTH = 640;
    public static final int W_HEIGHT = 440;
    public static final int RES_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int RES_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final static String newLine = "\n";
    private String parametr;
    private String name;
    private String nameToChange;
    private boolean connected;
    private boolean ifParametred;

        /**
         * @param args
         */
        public static void main(String[] args)
        {
            new bd();
        }
        public bd(){
            this.connected = false;
            this.ifParametred = false;
            this.setBounds( 
                ( RES_WIDTH - W_WIDTH ) / 2,
                ( RES_HEIGHT - W_HEIGHT ) / 2,
                640,
                480 );
            int h = 42;    //rozmiar paska gornego
            int h1 = h - 10;    // przyciski
            int w = h;          //reszta

            this.setUndecorated( true );
            this.setResizable( false );
            this.setTitle( ":: Database Connector - BD" );
            this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            setSize( W_WIDTH, W_HEIGHT );

            Container container = getContentPane();
            JPanel p = new JPanel( new FlowLayout( FlowLayout.RIGHT ));
            JPanel xl = new JPanel( new FlowLayout( FlowLayout.CENTER ));
            JPanel xr = new JPanel( new FlowLayout( FlowLayout.CENTER ));
            JPanel xm = new JPanel( new FlowLayout( FlowLayout.CENTER ));
            p.setBorder(BorderFactory.createLineBorder(Color.black));
            //borders
            //xm.setBorder(BorderFactory.createLineBorder(Color.black));
            //xr.setBorder(BorderFactory.createLineBorder(Color.black));
            //xl.setBorder(BorderFactory.createLineBorder(Color.black));

            p.setBackground( new Color( 200, 200, 150) );
            p.setPreferredSize( new Dimension( W_WIDTH ,h ) );

            xl.setPreferredSize( new Dimension( 20 , 320-3*h) );
            xm.setPreferredSize( new Dimension( BUTTON_WIDTH ,320-3*h) );

            int widthLeft = W_WIDTH - BUTTON_WIDTH - 20;
            xr.setPreferredSize( new Dimension(
                widthLeft,
                320-3*h
                ));

            communicate = new JFormattedTextField("$> ");
            communicate.setColumns(widthLeft/13);
            communicate.setEditable(false);

            parametrField = new TextField("parametr here");
            parametrField.setColumns(14);
            parametrField.setEditable(true);
            parametrField.setEnabled(false);

            parametrField.addTextListener(new TextListener() {
            public void textValueChanged(TextEvent e) {
                //
            }});

            parametrField.addKeyListener
                (new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        int key = e.getKeyCode();
                        if (key == KeyEvent.VK_ENTER) {
                            // zatwierdz argument
                            //System.out.println("ENTER pressed");
                            boolean match = true;
                            for ( int k = parametrField.getText().length() - 1;
                            k >= 0 ; k--){
                                if ( !Character.isDigit(parametrField.getText().charAt(k)) ){
                                    match = false;
                                }
                            }
                            if (  parametrField.getText().length() == 0 ) match = false;
                            if ( match ){
                                parametr = parametrField.getText();
                                communicate.setText("  Biezacy parametr: [ " + parametr + " ] + Biezace imie: [ " + name + " ]");
                                if ( connected ){
                                    button_8.setEnabled(true);
                                    button_7.setEnabled(true);
                                }
                            }
                            else{
                                terminal.append("Parametr musi byc liczba !" + newLine );
                            }
                        }
                    }
            }
            );

            nameField = new TextField("-> new name <-");
            nameField.setColumns(14);
            nameField.setEditable(true);
            nameField.setEnabled(false);

            nameField.addKeyListener
                (new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        int key = e.getKeyCode();
                        if (key == KeyEvent.VK_ENTER) {
                            // zatwierdz argument
                            //System.out.println("ENTER pressed");
                            boolean match = true;
                            for ( int k = nameField.getText().length() - 1;
                            k >= 0 ; k--){
                                if ( !Character.isLetter(nameField.getText().charAt(k))
                                    && !Character.isWhitespace(nameField.getText().charAt(k))
                                    ){
                                    match = false;
                                }
                            }
                            if (  nameField.getText().length() == 0 ) match = false;
                            if ( match ){
                                name = nameField.getText();
                                communicate.setText("  Biezacy parametr: [ " + parametr + " ] + Biezace imie: [ " + name + " ]");
                                if ( connected ){
                                    insert.setEnabled(true);
                                    delete.setEnabled(true);
                                    modyf.setEnabled(true);
                                }
                            }
                            else{
                                terminal.append("Nazwa musi skladac sie z liter !" + newLine );
                            }
                        }
                    }
            }
            );

            changeField = new TextField("Customer to change");
            changeField.setColumns(14);
            changeField.setEditable(true);
            changeField.setEnabled(false);

            changeField.addKeyListener
                (new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        int key = e.getKeyCode();
                        if (key == KeyEvent.VK_ENTER) {
                            // zatwierdz argument
                            //System.out.println("ENTER pressed");
                            boolean match = true;
                            for ( int k = changeField.getText().length() - 1;
                            k >= 0 ; k--){
                                if ( !Character.isLetter(changeField.getText().charAt(k))
                                    && !Character.isWhitespace(changeField.getText().charAt(k))
                                    ){
                                    match = false;
                                }
                            }
                            if (  changeField.getText().length() == 0 ) match = false;
                            if ( match ){
                                nameToChange = changeField.getText();
                                communicate.setText("  Biezacy parametr: [ " + parametr + " ] + Biezace imie: [ " + name + " ]");
                            }
                            else{
                                terminal.append("Nazwa musi skladac sie z liter !" + newLine );
                            }
                        }
                    }
            }
            );

            title = new JFormattedTextField(":: Database Connector - BD");
            title.setColumns(29);
            title.setEditable(false);
            title.setBorder(null);
            title.setBackground( new Color( 200, 200, 150) );
            title.setSelectedTextColor(Color.white);
            title.setForeground(Color.white);
            title.setSelectionColor(Color.black);
            title.setFont(
                new Font("Dialog", Font.BOLD, 14)
                );
            p.add(title);

            communicate.setText("  Nie polączono z bazą danych.");

            String str = "";
            terminal = new JTextArea( str, 13, widthLeft/11 + 2 );
            terminal.setFont( new Font ( "Serif", Font.ITALIC, 12 ) );
            terminal.setLineWrap( true );
            terminal.setWrapStyleWord( true );
            terminal.setBackground( new Color( 228, 228, 234 ) );
            terminal.setRows( 20 );
            terminal.setEditable( false );
            scrollPane = new JScrollPane( terminal );
            scrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
            scrollPane.setVisible( true );
            xr.add( communicate );
            xr.add( scrollPane );
 
            minimize = new JButton( "_" );
            minimize.setPreferredSize( new Dimension( w,h1 ) );
            p.add( minimize );

            exit1 = new JButton( "X" );
            exit1.setPreferredSize( new Dimension( w,h1 ) );
            p.add( exit1 );

            connect = new JButton("Connect");
            connect.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            xm.add( connect );

            button_5 = new JButton( "SELECT to file" );
            button_5.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( button_5 );
            button_5.setEnabled(false);

            button_6 = new JButton( "SELECT to Term" );
            button_6.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( button_6 );
            button_6.setEnabled(false);

            button_7 = new JButton( "SELECT param to File" );
            button_7.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( button_7 );
            button_7.setEnabled(false);

            button_8 = new JButton( "SELECT param to Term" );
            button_8.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( button_8 );
            button_8.setEnabled(false);

            insert = new JButton( "INSERT customer" );
            insert.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( insert );
            insert.setEnabled(false);

            delete = new JButton( "DELETE customer" );
            delete.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( delete );
            delete.setEnabled(false);

            modyf = new JButton( "change CUSTOMER name" );
            modyf.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( modyf );
            modyf.setEnabled(false);

            exit = new JButton( "Exit" );
            exit.setPreferredSize( new
                Dimension( BUTTON_WIDTH, BUTTON_HEIGHT ) );
            xm.add( exit );

            xm.add( parametrField );
            xm.add( nameField );
            xm.add( changeField );

            container.add( p,   "North" );
            container.add( xl,  "West" );
            container.add( xm,  "Center" );
            container.add( xr,  "East" );

            /**
             * Minimalizacja
             */
            minimize.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    setState( Frame.ICONIFIED );
            }});

            /**
             * Wyjscie
             */
            exit1.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e) {
                    try{
                        terminal.append( "Rozpoczeto zamykanie programu..." + newLine );
                        disconnect();
                    }
                    catch( SQLException sqle ){
                        System.out.printf( "Wystapil blad w trakcie"
                            +" rozlaczania z baza danych: " + sqle + newLine);
                    }
                    setVisible( false );
                    dispose();
                    System.exit( 0 );
            }});

            /**
             * Wyjscie
             */
            exit.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e) {
                    try{
                        disconnect();
                        terminal.append( "Zamykanie... " + newLine );
                        Thread.sleep(1000);
                    }
                    catch( SQLException sqle ){
                        System.out.printf( "Wystapil blad w trakcie"
                            + " rozlaczania z baza danych: " + sqle + newLine);
                    }
                    catch(InterruptedException inter ){
                        System.out.printf( "Blad: " + inter + newLine);
                    }
                    
                    setVisible( false );
                    dispose();
                    System.exit( 0 );
            }});

            /**
             * Polacz z baza danych
             */
            connect.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    terminal.append( "Nawiazywanie polaczenia z baza danych... " + newLine );
                    try{
                        //setState( Frame.ICONIFIED );
                        connectToDatabase();
                        //setState( Frame.NORMAL );
                        button_5.setEnabled(true);
                        button_6.setEnabled(true);
                        connect.setEnabled(false);
                        communicate.setText("  Prosze wpisac argument do przykladowego SELECT-a");
                        stmt = conn.createStatement();
                    }
                    catch( SQLException sqle ){
                        System.out.printf( "Blad polaczenia z baza danych: " + sqle + newLine);
                    } catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            }});

            /**
             * Przykladowy SELECT do pliku
             */
            button_5.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            button_5.setEnabled(false);
                            button_6.setEnabled(false);
                            String query = ""
                                + "SELECT customer2.cus_id, customer2.cus_firstname, customer2.cus_lastname"
                                + " FROM customer2 ORDER BY customer2.cus_firstname, customer2.cus_lastname";
                            ifParametred = false;
                            exampleSelectToFile(query);
                            rset.close();
                            button_5.setEnabled(true);
                            button_6.setEnabled(true);
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "Nie mozna wykonac! " + sel1 + newLine);
                    }
            }});

            button_6.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            button_5.setEnabled(false);
                            button_6.setEnabled(false);
                            String query = ""
                                + "SELECT customer2.cus_id, customer2.cus_firstname, customer2.cus_lastname"
                                + " FROM customer2 ORDER BY customer2.cus_firstname, customer2.cus_lastname";
                            ifParametred = false;
                            exampleSelectToTerm(query);
                            rset.close();
                            button_5.setEnabled(true);
                            button_6.setEnabled(true);
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "Nie mozna wykonac! " + sel1 + newLine );
                    }
            }});

            button_7.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            button_5.setEnabled(false);
                            button_6.setEnabled(false);
                            String query = ""
                                + "SELECT DISTINCT count(customer_id) FROM zamowienie WHERE zamowienie.customer_id = "
                                +parametr;
                            ifParametred = true;
                            exampleSelectToFile(query);
                            //rset.close();
                            button_5.setEnabled(true);
                            button_6.setEnabled(true);
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "Nie mozna wykonac! " + sel1 + newLine);
                    }
            }});

            button_8.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            button_5.setEnabled(false);
                            button_6.setEnabled(false);
                            String query = ""
                                + "SELECT DISTINCT count(customer_id) FROM zamowienie WHERE zamowienie.customer_id = "
                                + parametr
                            ;
                            ifParametred = true;
                            exampleSelectToTerm(query);
                            //rset.close();
                            button_5.setEnabled(true);
                            button_6.setEnabled(true);
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "!Nie mozna wykonac! " + sel1 + newLine );
                    }
            }});

            delete.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            deleteCustomer();
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "Nie mozna usunac! " + sel1 + newLine);
                    }
            }});

            modyf.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            modyfCustomer();
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "Nie mozna zmienic! " + sel1 + newLine);
                    }
            }});

            insert.addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    try{
                        if( connected ){
                            insertCustomer();
                        }
                    }
                    catch(SQLException sel1){
                        System.out.printf( "Nie mozna dodac! " + sel1 + newLine);
                    }
            }});

            setVisible(true);
    }

        public void connectToDatabase() throws SQLException, ClassNotFoundException{
  /*
            StringBuffer argument = new StringBuffer();
            Integer read;

            try{
                int k = 0;
                 while( (read = new Integer(System.in.read()) ) != 10 ) {
                    String aChar = new Character((char)read.intValue()).toString();
                    //System.out.print(k+": "+aChar+"\n");
                    argument.append(aChar);
                    k++;
                }
            }
            catch (IOException e){
                System.out.println("Error in reading arguments: " + e);
            }
            System.out.println("Argument read: "+argument);
*/
        	Class.forName("oracle.jdbc.driver.OracleDriver");
        	Connection conn = DriverManager.getConnection(
        			"jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf", "pkryszto", "pkryszto");
            /*OracleDataSource ods = new OracleDataSource();
            ods.setURL("jdbc:oracle:thin:kgajowy/kgajowy@ikar.elka.pw.edu.pl:1521/elka.elka.pw.edu.pl");
            conn = ods.getConnection();*/

            // Create Oracle DatabaseMetaData object
            DatabaseMetaData meta = conn.getMetaData();

            terminal.append( "JDBC driver version is " + meta.getDriverVersion() + newLine );
            terminal.append( "Your JDBC installation is correct." + newLine );
            terminal.append( "Polaczono z baza danych!" + newLine );
            connected = true;
            parametrField.setEnabled(true);
            nameField.setEnabled(true);
            changeField.setEnabled(true);
        }

        private void disconnect() throws SQLException{
            if ( connected ){
                terminal.append( "Disconnecting..." + newLine );
                conn.close();
            }
        }
        
        private void exampleSelectToFile(String query) throws SQLException{
            System.out.println("\n");
            FileWriter wr;
                try{
                    terminal.append("Rozpoczynam zapis do pliku.\n" + newLine );
                    wr = new FileWriter("wynik.txt");
                    stmt.setQueryTimeout(10);
                    ResultSet sysdate = stmt.executeQuery("SELECT TO_CHAR (SYSDATE, \'MM-DD-YYYY HH24:MI:SS\')\"NOW\" FROM DUAL");
                    sysdate.next();
                    wr.append("Report date: "
                        + sysdate.getString(1)
                        + newLine
                        );
                    rset = stmt.executeQuery(query);
                    while (rset.next()) {
                        String a = rset.getString(1);
                        for(int i = 0 ; i < a.length() ; i++){
                            wr.append(a.charAt(i));
                        }
                        wr.append(" ");

                        if( !ifParametred ) {
                            a = rset.getString(2);
                            for(int i = 0 ; i < a.length() ; i++){
                                wr.append(a.charAt(i));
                            }
                            wr.append(" ");
                            a = rset.getString(3);
                            for(int i = 0 ; i < a.length() ; i++){
                                wr.append(a.charAt(i));
                            }
                        }
                        wr.append("\n");
                        }
                    wr.close();
                    terminal.append("Zapisano raport do pliku." + newLine);
                }
                catch (IOException e){
                    System.out.printf("Nie mozna zapisac pliku ! " + e + newLine );
                }
            // close the result set, the statement and connect
        }

        private void exampleSelectToTerm(String query) throws SQLException{
            terminal.append(newLine);
            stmt.setQueryTimeout(10);
            ResultSet sysdate = stmt.executeQuery("SELECT TO_CHAR (SYSDATE, \'MM-DD-YYYY HH24:MI:SS\')\"NOW\" FROM DUAL");
                    sysdate.next();
                    terminal.append("Report date: "
                        + sysdate.getString(1)
                        + newLine
                        );
            rset = stmt.executeQuery(query);
            while (rset.next()) {
                terminal.append( "[ " + rset.getString(1) + " ]" + newLine );
                if ( !ifParametred ){
                    terminal.append( " " + rset.getString(2) + " " + newLine );
                    terminal.append( " " + rset.getString(3) + "" + newLine );
                }
            }
        }

        private void insertCustomer() throws SQLException{
            stmt.setQueryTimeout(5);
            stmt.executeUpdate("INSERT INTO customer (customer_id, customer_name, card_id, gift) VALUES (order_ie_seq.nextval, \'"+ name+"\', card_ie_seq.nextval, 1.0)");
            terminal.append("Klient ["+name+"] dodany." + newLine );
        }
        private void deleteCustomer() throws SQLException{
            stmt.setQueryTimeout(5);
            int k = stmt.executeUpdate("delete from customer where customer_name = '"+name+"'");
            if ( k != 0 ){
                terminal.append("Usunieto klientow: [ " + k + " ]" + newLine);
            }
            else terminal.append("Nie ma takiego klienta." + newLine);
        }
        private void modyfCustomer() throws SQLException{
            stmt.setQueryTimeout(5);
            int k = stmt.executeUpdate("UPDATE customer SET customer_name = '" + name + "' WHERE customer_name = '"+nameToChange+"'");
            if ( k != 0 ){
                terminal.append("Klient '"+nameToChange+"' nazywa sie teraz '" + name + "'." + newLine);
            }
            else if (k == 0){
                terminal.append("Nie ma takiego klienta." + newLine);
            }
        }
}
