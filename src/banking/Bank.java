package banking;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Bank extends JFrame 
{
	public List<Representative> representatives;
	public List<Customer> customers;
	
    Bank() 
    {
        super("My Banking Application");
        representatives = new ArrayList<Representative>();
        customers = new ArrayList<Customer>();
        
        JLabel jlrep = new JLabel("Bank Representative Information");
        JLabel jl11 = new JLabel("Enter RepNo");
        final JTextField jtf11 = new JTextField();
        JLabel jl12 = new JLabel("Enter RepName");
        final JTextField jtf12 = new JTextField();
        JLabel jl13 = new JLabel("Enter Branch");
        final JTextField jtf13 = new JTextField();
        JButton jb1 = new JButton("Submit");
        
        JLabel jlcus = new JLabel("Customer Information");
        JLabel jl21 = new JLabel("Enter CustomerNo");
        final JTextField jtf21 = new JTextField();
        JLabel jl22 = new JLabel("Enter CustomerName");
        final JTextField jtf22 = new JTextField();
        JLabel jl24 = new JLabel("Enter Balance");
        final JTextField jtf24 = new JTextField();
        JLabel jl25 = new JLabel("Enter RepNo");
        final JTextField jtf25 = new JTextField();
        JButton jb2 = new JButton("Submit");
        
        JPanel panel = new JPanel();
        final JTextArea jta = new JTextArea();
        jta.setRows(1);
        jta.setColumns(10);
        JLabel jl31 = new JLabel("Enter Cust No");
        final JTextField jtf31 = new JTextField();
        JButton jb3 = new JButton("Get Interest");
        panel.add(jlrep);
        panel.add(jl11);
        panel.add(jtf11);
        panel.add(jl12);
        panel.add(jtf12);
        panel.add(jl13);
        panel.add(jtf13);
        panel.add(jb1);
        panel.add(jlcus);
        panel.add(jl21);
        panel.add(jtf21);
        panel.add(jl22);
        panel.add(jtf22);
        panel.add(jl24);
        panel.add(jtf24);
        panel.add(jl25);
        panel.add(jtf25);
        panel.add(jb2);
        panel.add(jl31);
        panel.add(jtf31);
        panel.add(jb3);
        panel.add(jta);
        
        jb1.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    Statement stmt;
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root", "chemlab2015");

                    if (conn != null) 
                    {

                        System.out.println("Connection successful !!!");
                        int Repno = Integer.parseInt(jtf11.getText());
                        String Repname = jtf12.getText();
                        String branch = jtf13.getText();
                        stmt = (Statement) conn.createStatement();

                        Representative newRep = new Representative(Repno, Repname, branch);
                        representatives.add(newRep);
                        String query1 = "insert into Representative values('" + Repname + "','" + Repno + "','" + branch + "');";

                        stmt.executeUpdate(query1);
                    } 
                    else
                        System.out.println("Connection not successful!!!");

                } 
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                } 
                catch (ClassNotFoundException exx) 
                {
                    System.out.println(exx.getMessage());
                }
            }
        });
        jb2.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    Statement stmt2;
                    Class.forName("com.mysql.jdbc.Driver");

                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root",
                        "chemlab2015");

                    if (conn != null) 
                    {
                        System.out.println("Connection successful !!!");
                        int Custno = Integer.parseInt(jtf21.getText());
                        String CustName = jtf22.getText();
                        String balance = jtf24.getText();
                        double balanceAmount = Double.parseDouble(balance);
                        int Rno = Integer.parseInt(jtf25.getText());

                        stmt2 = (Statement) conn.createStatement();
                        System.out.println(Custno + " " + CustName + " " + balanceAmount + " " + Rno);
                        String query2 = "insert into Customer values('" + Custno + "','" +
                            CustName + "','" + balanceAmount + "','" + Rno + "');";
                        Customer newCust = new Customer(Custno, CustName, balanceAmount, Rno);
                        customers.add(newCust);
                        stmt2.executeUpdate(query2);
                    } 
                    else
                        System.out.println("Connection not successful!!!");

                } 
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                } 
                catch (ClassNotFoundException exx) 
                {
                    System.out.println(exx.getMessage());
                }
            }
        });
        jb3.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    Statement stmt;
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root", "chemlab2015");

                    if (conn != null) 
                    {
                    	System.out.println("Connection successful !!!");
                    	
                        stmt = (Statement) conn.createStatement();
                        String Custno = jtf31.getText();
                        String query3 = "SELECT * FROM Customer WHERE custNo = " + Integer.parseInt(Custno) + ";";

                        ResultSet rs = stmt.executeQuery(query3);
                        
                        while(rs.next())
                        {
                        	double balance = Double.parseDouble(rs.getString("balance"));
                            double interest = (balance * 1 * 10) / 100;
                            double newBalance = balance + interest;
                            
                            jta.append("Interest Amount after 1 Year");
                            jta.append("\n");
                            jta.append(interest + "");
                            jta.append("\n");
                            jta.append("New Balance : ");
                            jta.append(newBalance + "");
                            jta.append("\n");
                            
                            System.out.println("UPDATE Customer SET balance = " + newBalance + " WHERE custNo = " + Integer.parseInt(Custno) + ";");
                            
                            String query4 = "UPDATE Customer SET balance = " + newBalance + " WHERE custNo = " + Integer.parseInt(Custno) + ";";

                            stmt.executeUpdate(query4);
                        }
                        
                    } else
                        System.out.println("Connection not successful!!!");

                } 
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                } 
                catch (ClassNotFoundException exx) 
                {
                    System.out.println(exx.getMessage());
                }
            }
        });
        setContentPane(panel);
    }
    public static void main(String[] args) 
    {
        Bank mf = new Bank();
        mf.getContentPane().setLayout(new BoxLayout(mf.getContentPane(), BoxLayout.Y_AXIS));

        mf.setVisible(true);
        mf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mf.pack();
    }
}