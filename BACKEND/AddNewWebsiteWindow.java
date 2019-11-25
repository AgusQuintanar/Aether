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

//Agustin Quintanar A01636142
//Jonathan Chavez A01636160
public class AddNewWebsiteWindow extends JFrame {

    private ControlPanel controlPanel;

    public AddNewWebsiteWindow(Index index) {
        super("Aether - Add New Website");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        InputPanel inputPanel = new InputPanel();
        this.add(inputPanel);
        this.controlPanel = new ControlPanel(inputPanel,this, index);
		this.add(this.controlPanel,BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
 

    }

}

class InputPanel extends JPanel{
    private JTextArea rawHtmlTextArea;
    private JTextField publicUrlTextField,
                       privateUrlTextField;

    public InputPanel() {
        super();
        this.setPreferredSize(new Dimension(800,500));
        this.rawHtmlTextArea = new JTextArea(50,50);
        this.publicUrlTextField = new JTextField(50);
        this.privateUrlTextField = new JTextField(50);
        this.add(new JLabel("Website's Public Url"));
        this.add(this.publicUrlTextField);
        this.add(new JLabel("Website's Private Url"));
        this.add(this.privateUrlTextField);
        this.add(new JLabel("Website's RawHtml"));
        this.add(this.rawHtmlTextArea);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public String getTextRawHtmlTextArea() {
        return this.rawHtmlTextArea.getText();
    }

    public String getTextPublicUrlTextField() {
        return this.publicUrlTextField.getText();
    }

    public String getTextPrivateUrlTextField() {
        return this.privateUrlTextField.getText();
    }

    public void setTextRawHtmlTextArea(String newRawHtml) {
        this.rawHtmlTextArea.setText(newRawHtml);
    } 

    public void setTextPublicUrlTextField(String newPublicUrl) {
        this.publicUrlTextField.setText(newPublicUrl);
    }

    public void setTextPrivateUrlTextField(String newPrivateUrl) {
        this.privateUrlTextField.setText(newPrivateUrl);
    }
}

class ControlPanel extends JPanel{
    private JButton addAnotherWebsiteButton,
                    finishButton;

    private InputPanel inputPanel;

    private boolean windowIsOpen;


    public ControlPanel(InputPanel inputPanel, AddNewWebsiteWindow addNewWebsiteWindow, Index index) {
        super();
        this.setPreferredSize(new Dimension(800,200));
        this.inputPanel = inputPanel;
        this.windowIsOpen = true;

        this.addAnotherWebsiteButton = new JButton("Add another Website");
        this.addAnotherWebsiteButton.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
                try {
                    String publicUrl = inputPanel.getTextPublicUrlTextField(),
                           privateUrl = inputPanel.getTextPrivateUrlTextField(),
                           rawHtml = inputPanel.getTextRawHtmlTextArea();
                    if (rawHtml.length() > 0 && publicUrl.length()>0 && privateUrl.length()>0) {
                        System.out.println("len: "+rawHtml.length());
                        try {
                            index.addWebsite(publicUrl, privateUrl, rawHtml);

                        } catch(StringIndexOutOfBoundsException efew) {
                            
                        }
                        JOptionPane.showMessageDialog(null, "Website added succesfully.");
                        inputPanel.setTextRawHtmlTextArea("");
                        inputPanel.setTextPublicUrlTextField("");
                        inputPanel.setTextPrivateUrlTextField("");
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
                windowIsOpen = false;
                addNewWebsiteWindow.dispose();
            }
        });

        this.add(this.addAnotherWebsiteButton);
        this.add(this.finishButton);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}

