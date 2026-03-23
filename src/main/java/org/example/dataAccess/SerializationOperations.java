package org.example.dataAccess;

import org.example.dataModel.Employee;
import org.example.dataModel.Task;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerializationOperations{

    public SerializationOperations(){}

    public void saveData(Map<Employee, List<Task>> map, ArrayList<Task> tasks, ArrayList<Employee> employees) throws
            ParseException, IOException,
            ClassNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("john_doe.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(Employee.getCounter());
        objectOutputStream.writeInt(Task.getCounter());
        objectOutputStream.writeObject(map);
        objectOutputStream.writeObject(tasks);
        objectOutputStream.writeObject(employees);
        objectOutputStream.flush();
        objectOutputStream.close();}

    public void getData(Map<Employee, List<Task>> map, ArrayList<Task> tasks, ArrayList<Employee> employees) throws
            ParseException, IOException,
            ClassNotFoundException{

        FileInputStream fileInputStream = new FileInputStream("john_doe.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        int idE=(int) objectInputStream.readInt();
        int idT=(int) objectInputStream.readInt();

        Employee.setCounter(idE);
        Task.setCounter(idT);

        map.clear();
        map.putAll((Map<Employee, List<Task>>) objectInputStream.readObject());
        tasks.clear();
        tasks.addAll((ArrayList<Task>) objectInputStream.readObject());
        employees.clear();
        employees.addAll((ArrayList<Employee>) objectInputStream.readObject());
        objectInputStream.close();
    }


}