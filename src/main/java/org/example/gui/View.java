package org.example.gui;
import org.example.dataModel.Employee;
import org.example.dataModel.Task;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class View {
    private JFrame frame1;
    private JPanel panel1;
    private JButton deleteTask;
    private JButton addTask;
    private JButton addEmployee;
    private JButton statistics;
    private JButton workDuration;
    private JButton assignTask;
    private JButton filterEmployees;
    private JButton showTasks;
    private JRadioButton simple;
    private JRadioButton complex;
    private JList<Task> tasks;
    private JList<Employee> employees;
    private JScrollPane p1;
    private JScrollPane p2;
    private Model m;
    private JTextField tf;
    private DefaultListModel<Employee> employ;
    private DefaultListModel<Task> toDo;
    private JSpinner startHour;
    private JSpinner endHour;

    public View(Model mod)
    {

        this.m=mod;

        frame1=new JFrame();
        frame1.setTitle("Employees and Task Management App");
        frame1.setSize(1200,500);
        frame1.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame1.setLayout(new BorderLayout());

        panel1=new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        deleteTask=new JButton("deleteTask");
        addTask=new JButton("addTask");
        addEmployee=new JButton("addEmployee");
        statistics=new JButton("statistics");
        workDuration=new JButton("workDuration");
        assignTask=new JButton("assignTask");
        filterEmployees=new JButton("filterEmployees");
        showTasks=new JButton("showTasks");
        tf=new JTextField("Employee Name");

        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();

        panel2.add(deleteTask);
        panel2.add(addTask);
        panel3.add(addEmployee);
        panel3.add(statistics);
        panel3.add(workDuration);
        panel2.add(assignTask);
        panel3.add(filterEmployees);
        panel3.add(showTasks);
        panel3.add(tf);

        ButtonGroup simpleComplex;

        simple=new JRadioButton("simpleTask");
        complex=new JRadioButton("complexTask");
        simpleComplex=new ButtonGroup();
        simpleComplex.add(simple);
        simpleComplex.add(complex);

        panel2.add(simple);
        panel2.add(complex);

        SpinnerNumberModel shm=new SpinnerNumberModel(1,1,24,1);
        startHour=new JSpinner(shm);

        SpinnerNumberModel ehm=new SpinnerNumberModel(1,1,24,1);
        endHour=new JSpinner(ehm);

        JLabel label1=new JLabel("startHour:");
        panel2.add(label1);
        panel2.add(startHour);
        JLabel label2=new JLabel("endHour:");
        panel2.add(label2);
        panel2.add(endHour);


        panel1.add(panel2);
        panel1.add(panel3);
        frame1.add(panel1, BorderLayout.SOUTH);

        toDo=new DefaultListModel<>();
        toDo.addAll(m.getTm().getTasks());
        tasks=new JList<>(toDo);

        employ=new DefaultListModel<>();
        employ.addAll(m.getTm().getEmployees());
        employees=new JList<>(employ);

        p1=new JScrollPane(tasks);
        p1.setPreferredSize(new Dimension(500,0));
        p2=new JScrollPane(employees);
        p2.setPreferredSize(new Dimension(500,0));

        frame1.add(p1,BorderLayout.EAST);
        frame1.add(p2,BorderLayout.WEST);

        frame1.setVisible(true);

    }

    public JList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(JList<Task> tasks) {
        this.tasks = tasks;
    }

    public Model getM() {
        return m;
    }

    public void setM(Model m) {
        this.m = m;
    }

    public JFrame getFrame()
    {
        return this.frame1;
    }

    public JList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(JList<Employee> employees) {
        this.employees = employees;
    }

    public void setEmploy(DefaultListModel<Employee> employ) {
        this.employ = employ;
    }

    public void addEmployeeActionListener(ActionListener u)
    {
        addEmployee.addActionListener(u);
    }

    public void addTaskActionListener(ActionListener u)
    {
        addTask.addActionListener(u);
    }

    public void deleteTaskActionListener(ActionListener u)
    {
        deleteTask.addActionListener(u);
    }

    public void statisticsActionListener(ActionListener u)
    {
        statistics.addActionListener(u);
    }

    public void workDurationActionListener(ActionListener u)
    {
        workDuration.addActionListener(u);
    }

    public void assignTaskActionListener(ActionListener u)
    {
        assignTask.addActionListener(u);
    }

    public void filterEmployeesActionListener(ActionListener u)
    {
        filterEmployees.addActionListener(u);
    }

    public void showTasksActionListener(ActionListener u)
    {
        showTasks.addActionListener(u);
    }

    public String getEmployeeName()
    {
        return tf.getText();
    }

    public void clearEmployeeName()
    {
        tf.setText("Employee Name");
    }

    public DefaultListModel<Task> getToDo() {
        return toDo;
    }

    public DefaultListModel<Employee> getEmploy() {
        return employ;
    }

    public void displayErrorMessages(String err)
    {
        JOptionPane.showMessageDialog(this.frame1,err,"Error Message!",JOptionPane.ERROR_MESSAGE);
    }

    public boolean isSimpleTask()
    {
        return simple.isSelected();
    }

    public boolean isComplexTask()
    {
        return complex.isSelected();
    }

    public int getStartHour()
    {
        return (int)startHour.getValue();
    }

    public int getEndHour()
    {
        return (int)endHour.getValue();
    }

    public Task selectedTask()
    {
        return tasks.getSelectedValue();
    }

    public Employee selectedEmployee()
    {
        return employees.getSelectedValue();
    }

    public void clearTSel()
    {
        tasks.clearSelection();
    }

    public void clearESel()
    {
        employees.clearSelection();
    }

    public void clearSels(){ clearTSel(); clearESel();}

    public void displayFilteredEmployees(String emp)
    {
        JTextArea mesaj=new JTextArea(emp);
        mesaj.setEditable(false);
        JScrollPane panelAux=new JScrollPane(mesaj);
        panelAux.setSize(500,500);

        JOptionPane.showMessageDialog(this.frame1,panelAux,"Angajati cu >40 wh (asc)",JOptionPane.INFORMATION_MESSAGE);

    }

    public void displayStatistics(String emp)
    {

        JTextArea mesaj=new JTextArea(emp);
        mesaj.setEditable(false);
        JScrollPane panelAux=new JScrollPane(mesaj);
        panelAux.setSize(500,500);

        JOptionPane.showMessageDialog(this.frame1,panelAux,"Statistici",JOptionPane.INFORMATION_MESSAGE);

    }

    public void displayEmployeeWH(String emp,int nr)
    {
        String mesaj=emp+" lucreaza "+nr+" ore.";
        JOptionPane.showMessageDialog(this.frame1,mesaj,"Info",JOptionPane.INFORMATION_MESSAGE);

    }


}
