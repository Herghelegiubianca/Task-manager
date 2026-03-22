package org.example.gui;

import org.example.dataModel.ComplexTask;
import org.example.dataModel.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {
    private EmployeeView ev;
    private Model m;

    public EmployeeController(EmployeeView view, Model mod)
    {
        this.ev=view;
        this.m=mod;

        this.ev.showTasksActionListener(new EmployeeController.ShowSubTasksListener());
        this.ev.modifyStatusActionListener(new EmployeeController.ModifyStatusListener());

    }

    class ShowSubTasksListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task selectedTask=ev.selectedTask();

            if(selectedTask!=null)
            {
                if(selectedTask instanceof ComplexTask)
                {
                    ComplexTask ct=(ComplexTask)selectedTask;

                    EmployeeView secondView=new EmployeeView(ev.getE(),m);
                    secondView.setCurrentTask(ct);
                    secondView.setTitle();

                    secondView.getModelTasks().clear();
                    secondView.getModelTasks().addAll(ct.getTasks());

                    secondView.getFrame().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent)
                        {
                            ev.getModelTasks().clear();

                            if(ev.getCurrentTask()!=null)
                            {
                                ev.getModelTasks().addAll(((ComplexTask)ev.getCurrentTask()).getTasks());
                            }else ev.getModelTasks().addAll(m.getTm().getMap().get(ev.getE()));
                        }

                    });
                    EmployeeController secondController=new EmployeeController(secondView,m);

                    secondView.showFrame();
                    ev.clearTSel();

                    return;
                }else{
                    ev.displayErrorMessages("SIMPLE TASK! Nu exista nimic de afisat!");
                    ev.clearTSel();
                    return;
                }
            }else{
                ev.displayErrorMessages("Trebuie sa selectati un task!");
                return;
            }
        }
    }

    class ModifyStatusListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task selectedTask=ev.selectedTask();

            if(selectedTask!=null)
            {
                try{
                m.modifyStatus(ev.getE(),selectedTask);
                ev.getModelTasks().clear();

                if(ev.getCurrentTask()!=null)
                {
                    ev.getModelTasks().addAll(((ComplexTask)ev.getCurrentTask()).getTasks());
                }else{
                ev.getModelTasks().addAll(ev.getMod().getTm().getMap().get(ev.getE()));}}
                catch(Exception ex)
                {
                    ev.displayErrorMessages(ex.getMessage());
                }
            }else{
                ev.displayErrorMessages("Trebuie sa selectati un task!");
                return;
            }
        }
    }

}

