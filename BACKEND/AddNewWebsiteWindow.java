import java.util.LinkedList;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;



public class AddNewWebsiteWindow extends JFrame {

    private ControlPanel controlPanel;

    public AddNewWebsiteWindow() {
        super("Aether - Add New Website");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        InputPanel inputPanel = new InputPanel();
        this.add(inputPanel);
        this.controlPanel = new ControlPanel(inputPanel,this);
		this.add(this.controlPanel,BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    public LinkedList<String[]> getNewWebsitesList() {
        return this.controlPanel.getNewWebsitesList();
    }

}

class InputPanel extends JPanel{
    private JTextArea rawHtmlTextArea;
    private JTextField urlTextField;

    public InputPanel() {
        super();
        this.setPreferredSize(new Dimension(800,500));
        this.rawHtmlTextArea = new JTextArea(50,50);
        this.urlTextField = new JTextField(50);
        this.add(new JLabel("Website's URL"));
        this.add(this.urlTextField);
        this.add(new JLabel("Website's RawHtml"));
        this.add(this.rawHtmlTextArea);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public String getTextRawHtmlTextArea() {
        return this.rawHtmlTextArea.getText();
    }

    public String getTextUrlTextField() {
        return this.urlTextField.getText();
    }

    public void setTextRawHtmlTextArea(String newRawHtml) {
        this.rawHtmlTextArea.setText(newRawHtml);
    } 

    public void setTextUrlTextField(String newUrl) {
        this.urlTextField.setText(newUrl);
    }
}

class ControlPanel extends JPanel{
    private JButton addAnotherWebsiteButton,
                    finishButton;

    private InputPanel inputPanel;

    private LinkedList<String[]> newWebsitesList;


    public ControlPanel(InputPanel inputPanel, AddNewWebsiteWindow addNewWebsiteWindow) {
        super();
        this.setPreferredSize(new Dimension(800,200));
        this.inputPanel = inputPanel;
        this.newWebsitesList = new LinkedList<>();

        this.addAnotherWebsiteButton = new JButton("Add another Website");
        this.addAnotherWebsiteButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
                try {
                    if (inputPanel.getTextRawHtmlTextArea().length() > 0 && inputPanel.getTextUrlTextField().length()>0) {
                        String[] newWebsite = {inputPanel.getTextUrlTextField(),inputPanel.getTextRawHtmlTextArea()};
                        newWebsitesList.add(newWebsite);
                        JOptionPane.showMessageDialog(null, "Website added succesfully.");
                        inputPanel.setTextRawHtmlTextArea("");
                        inputPanel.setTextUrlTextField("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid url or raw html.");
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null, "Invalid url or raw html.");
                    System.out.println("Null value found in either rawHtml or url");
                }
			
			}
		});

        this.finishButton = new JButton("Finish");

        this.finishButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                addNewWebsiteWindow.dispose();
            }
        });

        this.add(this.addAnotherWebsiteButton);
        this.add(this.finishButton);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public LinkedList<String[]> getNewWebsitesList() {
        return this.newWebsitesList;
    }
}

