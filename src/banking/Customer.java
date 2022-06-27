package banking;

public class Customer 
{
	public String name;
	public double balance;
	public int custNo, repNo;
	
	Customer(int custNo, String name, double balance, int repNo)
	{
		this.name = name;
		this.balance = balance;
		this.custNo = custNo;
		this.repNo = repNo;
	}
}
