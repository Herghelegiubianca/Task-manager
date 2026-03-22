package org.example.gui;

import org.example.dataModel.ComplexTask;
import org.example.dataModel.Employee;
import org.example.dataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeView {

    private JFrame frame;
    private JPanel panel;
    private Employee e;
    private ComplexTask currentTask;
    private JButton modifyStatus;
    private JButton showTasks;
    private DefaultListModel<Task> modelTasks;
    private JList<Task> tasks;
    private JScrollPane scrollTasks;
    private Model mod;


    public EmployeeView(Employee em, Model m)
    {
        e=em;
        mod=m;
        frame=new JFrame();
        frame.setTitle("Employee "+e.getName());
        frame.setSize(500,200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel=new JPanel();

        modifyStatus=new JButton("modifyStatus");
        showTasks=new JButton("showTasks");
        panel.add(modifyStatus);
        panel.add(showTasks);

        frame.add(panel,BorderLayout.SOUTH);
        modelTasks=new DefaultListModel<>();
        modelTasks.addAll(m.getTm().getMap().get(em));
        tasks=new JList<>(modelTasks);
        scrollTasks=new JScrollPane(tasks);

        frame.add(scrollTasks,BorderLayout.CENTER);

    }

    public void setCurrentTask(ComplexTask currentTask) {
        this.currentTask = currentTask;
    }

    public Model getMod() {
        return mod;
    }

    public Employee getE() {
        return e;
    }

    public void setE(Employee e) {
        this.e = e;
    }

    public void showFrame()
    {
        this.frame.setVisible(true);
    }

    public Task selectedTask()
    {
        return tasks.getSelectedValue();
    }

    public Task getCurrentTask()
    {
        return this.currentTask;
    }

    public void showTasksActionListener(ActionListener u)
    {
        showTasks.addActionListener(u);
    }

    public void modifyStatusActionListener(ActionListener u)
    {
        modifyStatus.addActionListener(u);
    }

    public void displayErrorMessages(String err)
    {
        JOptionPane.showMessageDialog(this.frame,err,"Error Message!",JOptionPane.ERROR_MESSAGE);
    }

    public void clearTSel()
    {
        tasks.clearSelection();
    }

    public DefaultListModel<Task> getModelTasks() {
        return modelTasks;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setTitle()
    {
        frame.setTitle("Employee "+e.getName()+" | ComplexTask "+currentTask.getIdTask());
    }
}
