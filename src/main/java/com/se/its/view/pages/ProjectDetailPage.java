package com.se.its.view.pages;

import com.se.its.domain.issue.dto.response.IssueResponseDto;
import com.se.its.domain.issue.presentation.SwingIssueController;
import com.se.its.domain.member.dto.response.MemberResponseDto;
import com.se.its.domain.member.presentation.SwingMemberController;
import com.se.its.domain.project.dto.response.ProjectResponseDto;
import com.se.its.domain.project.presentation.SwingProjectController;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;


public class ProjectDetailPage extends JFrame {

    private SwingMemberController swingMemberController;
    private SwingProjectController swingProjectController;
    private SwingIssueController swingIssueController;
    private ProjectResponseDto currentProject;
    private final Long userId;
    private List<IssueResponseDto> issueDtos;
    private JList<IssueResponseDto> issueDtoJList;

    public ProjectDetailPage(ProjectResponseDto selectedProject, SwingMemberController swingMemberController,
                             SwingProjectController swingProjectController, SwingIssueController swingIssueController,
                             Long userId) {
        this.currentProject = selectedProject;
        this.swingMemberController = swingMemberController;
        this.swingProjectController = swingProjectController;
        this.swingIssueController = swingIssueController;
        this.userId = userId;

        initComponents();

        setTitle("프로젝트 상세 정보");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void initComponents() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel(currentProject.getName());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, gbc);

        gbc.gridy = 1;
        JLabel subTitle = new JLabel("이슈 리스트");
        add(subTitle, gbc);

        issueDtoJList = new JList<>(new DefaultListModel<>());
        initIssueData(currentProject);
        issueDtoJList.setCellRenderer(new IssueListRender());

        JScrollPane scrollPane = new JScrollPane(issueDtoJList);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton issueSearchBtn = new JButton("이슈 검색하기");
        //TODO 이슈 검색 페이지

        add(issueSearchBtn, gbc);

        checkUserIsPLAndInitComponent();
        checkUserIsDEVandInitComponent();
        checkUserIsTesterAndInitComponent();
    }



    private void checkUserIsTesterAndInitComponent() {
        MemberResponseDto member = swingMemberController.findMemberById(userId);
        if(member.getRole().toString().equals("TESTER")) {
            initTESTERComponent();
        }
    }

    private void initTESTERComponent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton browseMyIssue = new JButton("내가 생성한 이슈 조회하기");
        //TODO 내가 생성한 이슈 조회하기 -> 이슈 상세정보
        add(browseMyIssue, gbc);



        gbc.gridy = 5;
        JButton createIssueBtn = new JButton("이슈 생성하기");
        //TODO 이슈 생성 페이지
        add(createIssueBtn, gbc);

        createIssueBtn.addActionListener(
                e -> new IssueCreationPage(currentProject, swingIssueController, userId).setVisible(true)
        );
    }

    private void checkUserIsDEVandInitComponent() {
        MemberResponseDto member = swingMemberController.findMemberById(userId);
        if (member.getRole().toString().equals("DEV")) {
            initDEVComponent();
        }
    }

    private void initDEVComponent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton browseAssignedIssueBtn = new JButton("할당된 이슈 조회하기");
        add(browseAssignedIssueBtn, gbc);
        //TODO 할당된 이슈 조회 -> 할당된 이슈 상세정보 및 상태 변경 가능하게
    }

    private void checkUserIsPLAndInitComponent() {
        MemberResponseDto member = swingMemberController.findMemberById(userId);
        if (member.getRole().toString().equals("PL")) {
            initPLComponent();
        }
    }

    private void initPLComponent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton assignDevToIssueBtn = new JButton("이슈 담당자 지정");
        add(assignDevToIssueBtn, gbc);

        //TODO 이슈 담당자 지정 페이지

        gbc.gridy = 5;
        JButton changeIssueStatusBtn = new JButton("이슈 상태 변경");
        add(changeIssueStatusBtn, gbc);

        gbc.gridy = 6;
        JButton setIssuePrioryBtn = new JButton("이슈 우선순위 설정");
        add(setIssuePrioryBtn, gbc);
    }

    class IssueListRender extends JPanel implements ListCellRenderer<IssueResponseDto> {
        private JLabel issueName;
        private JLabel issuePriority;
        private JLabel issueStatus;
        private JLabel issueAssignee;
        private JLabel issueReportedDate;
        private DateTimeFormatter formatter;

        public IssueListRender() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            issueName = new JLabel();
            issuePriority = new JLabel();
            issueStatus = new JLabel();
            issueAssignee = new JLabel();
            issueReportedDate = new JLabel();
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm");

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(issueName, gbc);

            gbc.gridx = 1;
            add(issueStatus, gbc);

            gbc.gridx = 2;
            add(issuePriority, gbc);

            gbc.gridx = 3;
            add(issueAssignee, gbc);

            gbc.gridx = 4;
            add(issueReportedDate, gbc);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends IssueResponseDto> list, IssueResponseDto value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            issueName.setText("[" + value.getTitle() + "]");
            issueStatus.setText("상태: " + value.getStatus());
            issuePriority.setText("우선순위: " + value.getPriority());
            issuePriority.setText("담당자: " + (value.getAssignee() == null ? "지정 안됨" : value.getAssignee().getName()));
            issueReportedDate.setText("일시: " + value.getReportedDate().format(formatter));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }

    private void initIssueData(ProjectResponseDto projectDto) {
        issueDtos = projectDto.getIssues();
        DefaultListModel<IssueResponseDto> listModel = (DefaultListModel<IssueResponseDto>) issueDtoJList.getModel();
        listModel.clear();
        for (IssueResponseDto issueResponseDto : issueDtos) {
            listModel.addElement(issueResponseDto);
        }
    }


}