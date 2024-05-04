import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import javax.swing.*;

import static java.lang.System.exit;

class MyFrame extends JFrame implements ActionListener {

    JLabel Caption, displayField, times, firstDelay, betweenDelay;
    JMenuBar menuBar;
    JMenu closeMenu, helpMenu;
    JPanel buttonPanel1, ButtonPanel2, textPanel, inputPanel, mainCenterPanel;
    JButton Close, Continue , cancel, runSpam;
    JTextArea textArea;

    JTextField timesField, firstDelayField, betweenDelayField;

    MyFrame() {
        Caption = new JLabel("Spam Bot");
        Caption.setHorizontalAlignment(JLabel.CENTER);
        Caption.setFont(new Font("", Font.BOLD, 70));

        displayField = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("Photo.jpg"));
        displayField.setIcon(icon);
        displayField.setSize(40,40);
        displayField.setHorizontalAlignment(JLabel.CENTER);

        menuBar = new JMenuBar();
        closeMenu = new JMenu("Close");
        helpMenu = new JMenu("Help");
        closeMenu.addActionListener(this);
        helpMenu.addActionListener(this);
        closeMenu.setMnemonic(KeyEvent.VK_C);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(closeMenu);
        menuBar.add(helpMenu);


        Close = new JButton("Close");
        Continue = new JButton("Continue");
        Close.addActionListener(this);
        Continue.addActionListener(this);

        buttonPanel1 = new JPanel(new FlowLayout());
        buttonPanel1.add(Close);
        buttonPanel1.add(Continue);

        textArea = new JTextArea("Enter the message you want to spam here",10,45);

        times = new JLabel("Number of times do spam: ");
        firstDelay = new JLabel("Initial delay (in seconds): ");
        betweenDelay = new JLabel("Delay between spams (in ms): ");
        timesField = new JTextField();
        firstDelayField = new JTextField();
        betweenDelayField = new JTextField();
        times.setFont(new Font("", Font.PLAIN, 15));
        firstDelay.setFont(new Font("", Font.PLAIN, 15));
        betweenDelay.setFont(new Font("", Font.PLAIN, 15));
        cancel = new JButton("Cancel");
        runSpam = new JButton("Run Spam");
        cancel.addActionListener(this);
        runSpam.addActionListener(this);

        textPanel = new JPanel((new GridLayout(3,2)));
        textPanel.add(times);
        textPanel.add(timesField);
        textPanel.add(firstDelay);
        textPanel.add(firstDelayField);
        textPanel.add(betweenDelay);
        textPanel.add(betweenDelayField);

        ButtonPanel2 = new JPanel();
        ButtonPanel2.add(cancel);
        ButtonPanel2.add(runSpam);


        this.add(menuBar,BorderLayout.NORTH);
        this.add(buttonPanel1,BorderLayout.SOUTH);
        this.add(displayField,BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == Close || e.getSource() == closeMenu) {
            exit(0);
        }

        if (e.getSource() == Continue) {
            getContentPane().removeAll();
            revalidate();
            repaint();

            this.setLayout(new FlowLayout());
            this.add(Caption);
            add(textArea);
            this.add(textPanel);
            this.add(ButtonPanel2, BorderLayout.SOUTH);

            revalidate();
            repaint();

        }

        if (e.getSource() == cancel){
            exit(1);
        }

        if (e.getSource() == runSpam) {
            setVisible(false);
            runSpam();
            JOptionPane.showMessageDialog(null, " Successfully spam the message "+ timesField.getText() + " times.", "Message Spammed Successfully" ,JOptionPane.PLAIN_MESSAGE);
            exit(0);
        }
    }

    public void runSpam() {
        try {
            Robot robot = new Robot();

            StringSelection stringSelection = new StringSelection(textArea.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            int counter = 0;

            int time = Integer.parseInt(firstDelayField.getText()) * 1000;
            Thread.sleep(time);

            while (counter < Integer.parseInt(timesField.getText())) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_V);

                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(Integer.parseInt(betweenDelayField.getText()));

                counter++;
            }
        } catch (AWTException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        frame.setTitle("Spam Bot");
        frame.setSize(550, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
