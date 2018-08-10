
package hirikattaproject;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.Scanner;

public class HirikattaProject {
    static SerialPort chosenPort;
    public static void main(String[] args) {
        //Create confugaration window
        JFrame window=new JFrame();
        window.setTitle("Sensor value");
        window.setSize(600,400);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //create a dropdown list and button
        JComboBox<String> portList=new JComboBox<String>();
        JButton connectionButton=new JButton("connect");
        JPanel topPanel=new JPanel();
        topPanel.add(portList);
        topPanel.add(connectionButton);
        window.add(topPanel,BorderLayout.NORTH);
        
        //populate the dropdown list
        SerialPort[] portNames=SerialPort.getCommPorts();
        for(int i=0;i<portNames.length;i++){
            portList.addItem(portNames[i].getSystemPortName());
        }
        
        //create line Graph
        XYSeries series=new XYSeries("Sensor Reading");
        XYSeriesCollection dataset=new XYSeriesCollection(series);
        JFreeChart chart=ChartFactory.createXYLineChart("SensorReading", "time(Second)", "Reading", dataset, PlotOrientation.VERTICAL, false, false, false);
        window.add(new ChartPanel(chart),BorderLayout.CENTER);
        //configure the connection button and use another thread to listen for data
        connectionButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                if(connectionButton.getText().equals("connect")){
                    //attempt to coonect serial port
                    chosenPort =SerialPort.getCommPort(portList.getSelectedItem().toString());
                    chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                    if(chosenPort.openPort()){
                        connectionButton.setText("Disconnect");
                        portList.setEnabled(false);                       
                    }
                    //create new threed that listens for incoming text and populates the graph
                    Thread thread=new Thread(){
                        @Override
                        public void run(){
                            try (Scanner scanner = new Scanner(chosenPort.getInputStream())) {
                                int x=0;
                                while(scanner.hasNextLine()){
                                    try {
                                        //System.out.println(scanner.hasNextLine());
                                        String line=scanner.nextLine();
                                        //System.out.println(line);
                                        int number=Integer.parseInt(line);
                                        //int number =scanner.nextInt();
                                        //System.out.println(number);
                                        
                                        
                                        //Integer.parseInt(line);
                                        series.add(x++,number);
                                        window.repaint();
                                        
                                    } catch (Exception e) {
                                        System.out.println("exception"+e);
                                    }
                                }
                                scanner.close();
                            }
                            
                        }
                    };
                    thread.start();
                }else{
                //disconnect serial port
                    chosenPort.closePort();
                    portList.setEnabled(true);
                    connectionButton.setText("connect");
                }
                
            }
        });
        
        //show window
        window.setVisible(true);
        
        
    }
    
}
