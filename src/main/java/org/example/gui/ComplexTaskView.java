package org.example.gui;

import org.example.dataModel.ComplexTask;
import org.example.dataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComplexTaskView{
    private JFrame frame;
    private JPanel panel;
    private ComplexTask currentTask;
    private JButton addTask;
    private JList<Task> tasks;
    private DefaultListModel<Task> modelTasks;
    private JButton deleteTask;
    private JButton showTasks;
    private JRadioButton simple;
    private JRadioButton complex;
    private JScrollPane scrollTasks;
    private JSpinner startHour;
    private JSpinner endHour;


    public ComplexTaskView(ComplexTask task)
    {
        frame=new JFrame();
        frame.setTitle("ComplexTask "+task.getIdTask());
        frame.setSize(500,200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel=new JPanel();
        panel.setLayout(new GridLayout(3,1));

        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        currentTask=task;
        addTask=new JButton("addTask");
        deleteTask=new JButton("deleteTask");
        showTasks=new JButton("showTasks");
        simple=new JRadioButton("simple");
        complex=new JRadioButton("complex");
        ButtonGroup simpleComplex=new ButtonGroup();
        simpleComplex.add(simple);
        simpleComplex.add(complex);

        panel1.add(addTask);
        panel1.add(deleteTask);
        panel1.add(showTasks);
        panel2.add(simple);
        panel2.add(complex);

        SpinnerNumberModel shm=new SpinnerNumberModel(1,1,24,1);
        startHour=new JSpinner(shm);

        SpinnerNumberModel ehm=new SpinnerNumberModel(1,1,24,1);
        endHour=new JSpinner(ehm);

        JLabel label1=new JLabel("startHour:");
        JLabel label2=new JLabel("endHour:");
        panel3.add(label1);
        panel3.add(startHour);
        panel3.add(label2);
        panel3.add(endHour);

        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);

        frame.add(panel,BorderLayout.SOUTH);
        modelTasks=new DefaultListModel<>();
        modelTasks.addAll(currentTask.getTasks());
        tasks=new JList<>(modelTasks);
        scrollTasks=new JScrollPane(tasks);

        frame.add(scrollTasks,BorderLayout.CENTER);
    }

    public void showFrame()
    {
        this.frame.setVisible(true);
    }

    public boolean isSimpleTask()
    {
        return simple.isSelected();
    }

    public boolean isComplexTask()
    {
        return complex.isSelected();
    }

    public Task selectedTask()
    {
        return tasks.getSelectedValue();
    }

    public Task getCurrentTask()
    {
        return this.currentTask;
    }

    public int getStartHour()
    {
        return (int)startHour.getValue();
    }

    public int getEndHour()
    {
        return (int)endHour.getValue();
    }

    public DefaultListModel<Task> getModelTasks() {
        return modelTasks;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void showTasksActionListener(ActionListener u)
    {
           showTasks.addActionListener(u);
    }

    public void addTaskActionListener(ActionListener u)
    {
        addTask.addActionListener(u);
    }

    public void deleteTaskActionListener(ActionListener u)
    {
        deleteTask.addActionListener(u);
    }

    public void displayErrorMessages(String err)
    {
        JOptionPane.showMessageDialog(this.frame,err,"Error Message!",JOptionPane.ERROR_MESSAGE);
    }

    public void clearTSel()
    {
        tasks.clearSelection();
    }
}


