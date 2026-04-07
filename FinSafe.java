/*2. Core Java: The "FinSafe" Transaction Validator
Business Case: "FinSafe" is a digital wallet app. They are seeing an increase in "Overdraft" errors where users spend more than they have because the app processes transactions too slowly.
Problem Statement
Build a robust Transaction Processor that validates every "Spend" request against the user's current balance and logs every action for auditing purposes.
Student Tasks:
1. Encapsulation: Create an Account class with private variables (balance, accountHolder).
2. Custom Exception: Create a user-defined exception InSufficientFundsException.
3. Validation Logic: Write a processTransaction(double amount) method.
○       If the amount is negative, throw an IllegalArgumentException.
○       If the amount > balance, throw your custom InSufficientFundsException.
1. Transaction History: Store the last 5 successful transaction amounts in an ArrayList and provide a printMiniStatement() method.
Deliverable: A console-based Java application where a user can Deposit, Withdraw, and View History with full error handling.*/
import java.time.LocalTime;
import java.util.*;
class InSufficientFundsException extends Exception
{
    InSufficientFundsException(String message)
    {
        super(message);
    }
}
class Account
{
    private double balance;
    private String accountHolder;
    private List<String> transaction;
    Account(String name,double balance)
    {
        this.accountHolder=name;
        this.balance=balance;
        this.transaction=new ArrayList<>();
    }
    public void processTransaction(double amount,String type) throws InSufficientFundsException
    {
        if(amount<=0)
        {
            throw new IllegalArgumentException("Invalid  amount");
        }
        if(type.equals("deposit"))
        {
            deposit(amount);
        }
        else if(type.equals("withdraw"))
        {
            withdraw(amount);
        }
    }
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        balance += amount;
        transactionhistory(amount, "Deposited");
        System.out.println("Money deposited successfully.");
    }
    public void withdraw(double amount) throws InSufficientFundsException
    {
        if(amount<=0)
        {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }

        if(amount>balance)
        {
            throw new InSufficientFundsException("Insufficient balance");
        }
        balance-=amount;
        transactionhistory(amount,"Withdrawn");
        System.out.println("Money is withdrawn .");
    }
    public void transactionhistory(double amount,String type)
    {
        LocalTime time=LocalTime.now();
        if (transaction.size() == 5) {
            transaction.remove(0);
        }
        transaction.add(type+" Rs."+amount+" at "+time);
    }
    public void printMiniStatement()
    {
        System.out.println("This is the bank statement for " + accountHolder + " based on the past 5 transaction history.");
        System.out.println("Current balance: Rs." + balance);
        for(String str:transaction)
        {
            System.out.println(str);
        }
    }


}
public class FinSafe {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Account ac=new Account("Shashank",250000);
        while(true)
        {
            System.out.println("1.processTransaction 2.transaction history");
            String s=sc.next();
            if(s.equals("done"))
            {
                break;
            }
            try
            {

            int n=Integer.parseInt(s);
            if(n==1)
            {
                System.out.println("a:deposit\nb:withdraw");
                System.out.print("Enter your choice: ");
                String c=sc.next();
                if(c.equals("a"))
                {
                    System.out.print("Enter the amount to deposit: ");
                    double amount=sc.nextDouble();
                    ac.processTransaction(amount, "deposit");
                }
                else if(c.equals("b"))
                {
                    System.out.print("Enter the amount to withdraw: ");
                    double amount=sc.nextDouble();
                    ac.processTransaction(amount, "withdraw");
                }
            }
            else if(n==2)
            {
                ac.printMiniStatement();
            }
            } catch (InSufficientFundsException e) {
                System.out.println(e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println( e.getMessage());

            } catch (Exception e) {
                System.out.println("Unexpected error");
            }
        }
    }
}
