import java.io.BufferedReader;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BrowserWindow {
    private String username;
    private CardLayout cl;
    private JPanel contentPane,
                   userConfigPanel;

    public BrowserWindow() {
        super("Google 2.0");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setBounds(0, 0, (int)this.getToolkit().getScreenSize().getWidth(), (int)this.getToolkit().getScreenSize().getHeight());
		this.setResizable(false);
        this.setBackground(Color.BLACK);

        this.cl = new CardLayout();

        this.contentPane = new JPanel()
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("../ConfigurationFiles/BrowserConfiguration.csv"));
            PrintWriter pw = new PrintWriter(new FileWriter("../ConfigurationFiles/BrowserConfiguration.csv"));
              
            br.readLine(); //Headers

            if (br.readLine() == null) { //If there is no data
                
            } 
            
            String linea;
              StringTokenizer st;
              String nombre,
                    apellido;
              int hrs;
              double tabulador;
              br.readLine();
              pw.println("Nombre completo, Pago");
              while((linea = br.readLine()) != null){
                st = new StringTokenizer(linea);
                nombre = st.nextToken();
                apellido = st.nextToken();
                hrs = Integer.parseInt(st.nextToken());
                tabulador = Double.parseDouble(st.nextToken());
                pw.println(nombre+" "+apellido+", "+(hrs*tabulador));
              }
              pw.close();
              br.close();
          }
          catch (FileNotFoundException ex){
            System.out.println("No se localizo el archivo "+ex);
          }
          catch (IOException ex){
            System.out.println("Ocurrio un error de I/O");
          }
    }
}