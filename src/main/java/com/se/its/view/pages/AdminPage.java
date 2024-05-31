package com.se.its.view.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JLabel;

public class AdminPage extends JFrame {
    private JLabel message;
    private JPanel accountPanel;
    private JPanel projectPanel;
    private JTextField idTextField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton accountCreateBtn;

    public AdminPage() {
        initComponents();

        setTitle("CITS");
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(1, 2));

        accountPanel = new JPanel(new GridBagLayout());
        projectPanel = new JPanel();

        projectPanel.setBackground(Color.BLUE);

        GridBagConstraints accountGbc = new GridBagConstraints();

        accountGbc.insets = new Insets(10, 10, 10, 10);

        accountGbc.gridx = 0;
        accountGbc.gridy = 0;
        accountGbc.gridwidth = 2;
        accountGbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("관리자 페이지");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        accountPanel.add(titleLabel, accountGbc);

        accountGbc.gridx = 0;
        accountGbc.gridy = 1;
        accountGbc.gridwidth = 2;
        accountGbc.anchor = GridBagConstraints.CENTER;
        accountCreateBtn = new JButton("계정 생성하기");
        accountPanel.add(accountCreateBtn, accountGbc);

        accountCreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountCreationPage().setVisible(true);
            }
        });



        add(accountPanel);
        add(projectPanel);

    }
}