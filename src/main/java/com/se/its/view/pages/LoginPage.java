package com.se.its.view.pages;

import com.se.its.domain.comment.presentation.SwingCommentController;
import com.se.its.domain.issue.presentation.SwingIssueController;
import com.se.its.domain.member.dto.request.MemberSignInRequestDto;
import com.se.its.domain.member.dto.response.MemberResponseDto;
import com.se.its.domain.member.presentation.SwingMemberController;
import com.se.its.domain.project.presentation.SwingProjectController;
import com.se.its.view.exception.EmptyIdException;
import com.se.its.view.exception.EmptyPasswordException;
import com.se.its.view.util.ErrorMessage;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginPage extends JFrame {

    //TODO 로그인 시 계정의 직책에 따라 페이지가 달라져야됨
    private SwingMemberController swingMemberController;
    private SwingProjectController swingProjectController;
    private SwingIssueController swingIssueController;
    private SwingCommentController swingCommentController;
    private JTextField idTextField;
    private JPasswordField pwTextField;
    private JButton signInBtn;
    private JPanel mainPanel;

    public LoginPage(SwingMemberController swingMemberController, SwingProjectController swingProjectController,
                     SwingIssueController swingIssueController, SwingCommentController swingCommentController) {
        this.swingMemberController = swingMemberController;
        this.swingProjectController = swingProjectController;
        this.swingIssueController = swingIssueController;
        this.swingCommentController = swingCommentController;
        //TODO 페이지 권한마다 달라진
        initComponents();
        ActionListener signInAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        };
        setSignInAction(signInAction);
    }

    private void handleSignIn() {
        if (checkValidation()) {
            String id = idTextField.getText();
            String password = new String(pwTextField.getText());
            try {
                MemberSignInRequestDto requestDto = MemberSignInRequestDto.builder()
                        .signId(id)
                        .password(password)
                        .build();
                MemberResponseDto responseDto = swingMemberController.signIn(requestDto);
                if (responseDto != null) {
                    JOptionPane.showMessageDialog(signInBtn, "로그인 성공");
                    if (responseDto.getRole().toString() == "ADMIN") {
                        new AdminPage(swingMemberController, swingProjectController, swingIssueController,
                                swingCommentController,
                                responseDto.getId());
                    } else if (responseDto.getRole().toString() == "PL") {
                        new PlPage(swingMemberController, swingProjectController, swingIssueController,
                                swingCommentController,
                                responseDto.getId());
                    } else {
                        new DevTesterPage(swingMemberController, swingProjectController, swingIssueController,
                                swingCommentController,
                                responseDto.getId());
                    }
                    dispose();
                } else {
                    showError(ErrorMessage.FAILED_TO_SIGNIN.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError(e.getMessage());
            }

        }
    }


    private void initComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 간의 간격 설정

        // 로그인 레이블
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(new JLabel("로그인"), gbc);

        // ID 레이블
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("ID:"), gbc);

        // ID 텍스트 필드
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        idTextField = new JTextField(15);
        mainPanel.add(idTextField, gbc);

        // PASSWORD 레이블
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("PASSWORD:"), gbc);

        // PASSWORD 텍스트 필드
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        pwTextField = new JPasswordField(15);
        mainPanel.add(pwTextField, gbc);

        // 로그인 버튼
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signInBtn = new JButton("로그인");
        mainPanel.add(signInBtn, gbc);

        add(mainPanel);
        setTitle("CITS");
        setSize(300, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void checkIdEmpty() {
        if (idTextField.getText().isEmpty()) {
            throw new EmptyIdException();
        }
    }

    private void checkPassword() {
        if (pwTextField.getText().isEmpty()) {
            throw new EmptyPasswordException();
        }
    }

    private boolean checkValidation() {
        try {
            checkIdEmpty();
            checkPassword();
            return true;
        } catch (EmptyIdException | EmptyPasswordException e) {
            showError(e.getMessage());
            return false;
        }
    }

    private void setSignInAction(ActionListener actionListener) {
        signInBtn.addActionListener(actionListener);
        idTextField.addActionListener(actionListener);
        pwTextField.addActionListener(actionListener);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}

