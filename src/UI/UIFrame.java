package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIFrame {
    private static UIFrame instance;

    private final int FRAME_WIDTH = 550;
    private final int FRAME_HEIGHT = 500;

    private final int MAIN_TEXT_HEIGHT = 30;
    private final int MAIN_TEXT_WIDTH = 40;

    private final int INFO_HEIGHT = 15;
    private final int INFO_WIDTH = 30;

    private JTextArea mainText;
    private JTextArea partyInfo;
    private JTextArea otherInfo;
    private JTextField input;

    private String textInput;
    private boolean inputEntered = false;

    private UIFrame()
    {
        JFrame fr = new JFrame();
        fr.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        fr.setTitle("Dungeon Crawl");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setResizable(false);

        // Add the panel to the frame
        JPanel panel = CreatePanel();
        fr.add(panel);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
    }

    public static UIFrame getInstance() {
        if(instance == null) {
            instance = new UIFrame();
        }
        return instance;
    }

    private JPanel CreatePanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        mainText = new JTextArea(MAIN_TEXT_HEIGHT, MAIN_TEXT_WIDTH);
        mainText.setEditable(false);
        mainText.setLineWrap(true);
        mainText.setWrapStyleWord(true);
        mainText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainText.setFont(new Font("Courier New", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(mainText);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        panel.add(scroll, c);

        partyInfo = new JTextArea(INFO_HEIGHT, INFO_WIDTH);
        partyInfo.setEditable(false);
        partyInfo.setLineWrap(true);
        partyInfo.setWrapStyleWord(true);
        partyInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        partyInfo.setFont(new Font("Courier New", Font.PLAIN, 12));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTHEAST;
        panel.add(partyInfo, c);

        otherInfo = new JTextArea(INFO_HEIGHT, INFO_WIDTH);
        otherInfo.setEditable(false);
        otherInfo.setLineWrap(true);
        otherInfo.setWrapStyleWord(true);
        otherInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        otherInfo.setFont(new Font("Courier New", Font.PLAIN, 12));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.SOUTHEAST;
        panel.add(otherInfo, c);

        input = new JTextField(MAIN_TEXT_WIDTH + 3);
        input.setEditable(true);
        input.setFont(new Font("Courier New", Font.PLAIN, 12));

        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textInput = input.getText().trim();
                input.setText("");
                mainText.append(textInput + "\n");
                mainText.setCaretPosition(mainText.getDocument().getLength());
                inputEntered = true;
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.SOUTHWEST;
        panel.add(input, c);

        return panel;
    }
    public String getTextInput()
    {
        inputEntered = false;
        return textInput;
    }

    public void appendMain(String text)
    {
        mainText.append(text);
        mainText.setCaretPosition(mainText.getDocument().getLength());
    }

    public void clearThenAppendMain(String text)
    {
        mainText.setText(text);
        mainText.setCaretPosition(mainText.getDocument().getLength());
    }

    public void appendParty(String text)
    {
        partyInfo.append(text);
    }

    public void appendOther(String text)
    {
        otherInfo.append(text);
    }

    public boolean isInputEntered()
    {
        return inputEntered;
    }

    public void setInputEntered(boolean inputEntered)
    {
        this.inputEntered = inputEntered;
    }

    public void clearMainText()
    {
        mainText.setText("");
    }

    public void clearPartyInfo()
    {
        partyInfo.setText("");
    }

    public void clearOtherInfo()
    {
        otherInfo.setText("");
    }

    public void clearAll()
    {
        mainText.setText("");
        partyInfo.setText("");
        otherInfo.setText("");
    }
}
