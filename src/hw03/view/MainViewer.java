/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: NAMES of team members
 * Date: Oct 29, 2015
 * Time: 1:51:47 AM
 *
 * Project: csci205_hw_01
 * Package: hw03.view
 * File: MainViewer
 * Description:
 *
 * ****************************************
 */
package hw03.view;

/**
 * The REAL GUI
 *
 * @author Dunni Adenuga
 */
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Uses JDialog and an object of frame to render GUI
 *
 * @author ia005
 */
public class MainViewer extends JDialog {
    private JTextField freqText = new JTextField(10);
    private JTextField sampleRateText = new JTextField(10);
    private JTextField lengthText = new JTextField(10);
    private JFrame frame = new JFrame();
    private JFileChooser chooser = new JFileChooser(".");
    private JMenuItem newAction = new JMenuItem("New");
    private JMenuItem openAction = new JMenuItem("Open");
    private JMenuItem exitAction = new JMenuItem("Exit");
    private JButton plotButton = new JButton("Plot");
    private JButton plotButton2 = new JButton("Plot");

    //http://examples.javacodegeeks.com/desktop-java/swing/jdialog/java-jdialog-example/
    //public MainViewer(JFrame framer, String title) {
    // super(framer, title);
    //}
//http://www.java-tips.org/java-se-tips-100019/15-javax-swing/1755-how-to-create-menu-bar.html
    public MainViewer() {

        frame.setTitle("WaveForm Visualization");
        frame.setSize(200, 200);

        // Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();

        // Add the menubar to the frame
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("File");

        menuBar.add(fileMenu);

        // Create and add simple menu item to one of the drop down menu
        fileMenu.add(newAction);
        fileMenu.add(openAction);
        fileMenu.add(exitAction);

        setTitle("Enquiry");
        GridLayout grid = new GridLayout(4, 0, 2, 0);
        setLayout(grid);
        JPanel jpanel1 = new JPanel();
        jpanel1.add(new JLabel("Frequency(Hz): "), jpanel1.LEFT_ALIGNMENT);
        jpanel1.add(freqText, jpanel1.RIGHT_ALIGNMENT);
        JPanel jpanel2 = new JPanel();
        jpanel2.add(new JLabel("Sample Rate(Hz): "), jpanel2.LEFT_ALIGNMENT);
        jpanel2.add(sampleRateText, jpanel2.RIGHT_ALIGNMENT);
        JPanel jpanel3 = new JPanel();
        jpanel3.add(new JLabel("Length(s): "), jpanel3.LEFT_ALIGNMENT);
        jpanel3.add(lengthText, jpanel3.RIGHT_ALIGNMENT);
        getContentPane().add(jpanel1);
        getContentPane().add(jpanel2);
        getContentPane().add(jpanel3);
        getContentPane().add(plotButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

    }

    /**
     * Show the JDialog part of this class
     */
    public void dialogBox() {

        setVisible(true);
    }

    /**
     * Display JFileChooser
     *
     * @return If "Ok" was chosen
     */
    public int chooseFile() {
        return chooser.showOpenDialog(frame);
    }

    /**
     * If Exit is chosen on GUI close everything
     */
    public void exitOption() {
        JOptionPane.showMessageDialog(frame, "Good-Bye!");
        frame.setVisible(false);
        frame.dispose();
    }

    public JTextField getFreqText() {
        return freqText;
    }

    public JTextField getSampleRateText() {
        return sampleRateText;
    }

    public JTextField getLengthText() {
        return lengthText;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JFileChooser getChooser() {
        return chooser;
    }

    public JMenuItem getNewAction() {
        return newAction;
    }

    public JMenuItem getOpenAction() {
        return openAction;
    }

    public JMenuItem getExitAction() {
        return exitAction;
    }

    public JButton getPlotButton() {
        return plotButton;
    }

    public JButton getPlotButton2() {
        return plotButton2;
    }

    public static void main(String[] args) {
        new MainViewer();
    }
}
