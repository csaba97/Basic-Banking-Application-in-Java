package test;

import org.junit.Test;
import PT2018.demo.DemoProject.Account;
import PT2018.demo.DemoProject.Bank;
import PT2018.demo.DemoProject.Capitalizat;
import PT2018.demo.DemoProject.Person;
import PT2018.demo.DemoProject.SavingAccount;
import PT2018.demo.DemoProject.SpendingAccount;
import PT2018.demo.DemoProject.SpendingAccountType;
import junit.framework.TestCase;
public class test extends TestCase{
	
	@Test
    public void testWithdrawSpending() {
		SpendingAccount tester = new SpendingAccount(1,1,SpendingAccountType.ADULT); // MyClass is tested

        tester.addFund(1000);
        tester.withdraw(100);
        assertEquals(900f, tester.getAmount());
        
        tester.withdraw(100);
        assertEquals(800f, tester.getAmount());
        
        tester.withdraw(200);
        assertEquals(600f, tester.getAmount());
        
        tester.withdraw(300);
        assertEquals(300f, tester.getAmount());
        
        tester.withdraw(200);
        assertEquals(100f, tester.getAmount());
        
        tester.withdraw(300);
        assertEquals(100f, tester.getAmount());
        
        tester.withdraw(100);
        assertEquals(0f, tester.getAmount());
    }
	
	@Test
    public void testAddFundSpending() {
		SpendingAccount tester = new SpendingAccount(1,1,SpendingAccountType.ADULT); // MyClass is tested

        tester.addFund(100);
        assertEquals(100f, tester.getAmount());
        tester.addFund(150);
        assertEquals(250f,tester.getAmount() );
        tester.addFund(150);
        assertEquals(400f,tester.getAmount() );
        tester.addFund(100);
        assertEquals(500f,tester.getAmount());
    }
	
	@Test
    public void testWithdrawSaving() {
		SavingAccount tester = new SavingAccount(1,1,Capitalizat.CAPITALIZAT); // MyClass is tested
		
        tester.addFund(1000,0);
        tester.withdraw();
        assertEquals(0f, tester.getAmount());
        
        tester.addFund(0,0);
        tester.withdraw();
        assertEquals(0f, tester.getAmount());
    }
	
	@Test
    public void testAddFundSaving() {
		SavingAccount tester = new SavingAccount(1,1,Capitalizat.CAPITALIZAT); // MyClass is tested

        tester.addFund(100,5);
        assertEquals(100f, tester.getAmount());
        tester.addFund(150,5);
        assertEquals(100f,tester.getAmount() );
        tester.addFund(150,6);
        assertEquals(100f,tester.getAmount() );
        tester.addFund(100,7);
        assertEquals(100f,tester.getAmount());
    }
	
	
	@Test
    public void testAddPerson() {
		Bank bank=new Bank();
		Person person1=new Person(1,"a",11,"a");
		Person person2=new Person(2,"a1",12,"a1");
		Person person3=new Person(3,"a2",13,"a2");
		Person person4=new Person(4,"a3",14,"a3");
		
		bank.addPerson(person1);
        assertEquals(true, bank.getClients().contains(person1));
        
        bank.addPerson(person2);
        assertEquals(true, bank.getClients().contains(person2));
        
        bank.addPerson(person3);
        assertEquals(true, bank.getClients().contains(person3));
        
        bank.addPerson(person4);
        assertEquals(true, bank.getClients().contains(person4));
    }
	
	@Test
    public void testRemovePerson() {
		Bank bank=new Bank();
		Person person1=new Person(1,"a",11,"a");
		Person person2=new Person(2,"a1",12,"a1");
		Person person3=new Person(3,"a2",13,"a2");
		Person person4=new Person(4,"a3",14,"a3");
		
		bank.addPerson(person1);      
        bank.addPerson(person2);        
        bank.addPerson(person3);      
        bank.addPerson(person4);

        bank.removePerson(person1);
        assertEquals(false, bank.getClients().contains(person1));
        
        bank.removePerson(person2);
        assertEquals(false, bank.getClients().contains(person2));
        
        bank.removePerson(person3);
        assertEquals(false, bank.getClients().contains(person3));
        
        bank.removePerson(person4);
        assertEquals(false, bank.getClients().contains(person4));
    }
	
	@Test
    public void testAddAccountSaving() {
		Bank bank=new Bank();
		Person person=new Person(1,"a",11,"a");
		bank.addPerson(person);      
		
		
		SavingAccount tester = new SavingAccount(1,1,Capitalizat.CAPITALIZAT); 
		SavingAccount tester2 = new SavingAccount(2,1,Capitalizat.CAPITALIZAT); 
		SavingAccount tester3 = new SavingAccount(3,1,Capitalizat.CAPITALIZAT); 
		SavingAccount tester4 = new SavingAccount(4,1,Capitalizat.CAPITALIZAT); 
		bank.addAccount(person, tester);
		bank.addAccount(person, tester2);
		bank.addAccount(person, tester3);
		bank.addAccount(person, tester4);

        assertEquals(true, bank.getSavingAccounts().contains(tester));
        assertEquals(true, bank.getSavingAccounts().contains(tester2));
        assertEquals(true, bank.getSavingAccounts().contains(tester3));
        assertEquals(true, bank.getSavingAccounts().contains(tester4));
    }
	
	@Test
    public void testAddAccountSpending() {
		Bank bank=new Bank();
		Person person=new Person(1,"a",11,"a");
		bank.addPerson(person);      
		
		
		SpendingAccount tester = new SpendingAccount(1,1,SpendingAccountType.ADULT); 
		SpendingAccount tester2 = new SpendingAccount(2,1,SpendingAccountType.ADULT); 
		SpendingAccount tester3 = new SpendingAccount(3,1,SpendingAccountType.ADULT); 
		SpendingAccount tester4 = new SpendingAccount(4,1,SpendingAccountType.ADULT); 
		bank.addAccount(person, tester);
		bank.addAccount(person, tester2);
		bank.addAccount(person, tester3);
		bank.addAccount(person, tester4);

        assertEquals(true, bank.getSpendingAccounts().contains(tester));
        assertEquals(true, bank.getSpendingAccounts().contains(tester2));
        assertEquals(true, bank.getSpendingAccounts().contains(tester3));
        assertEquals(true, bank.getSpendingAccounts().contains(tester4));
    }
	
	
	@Test
    public void testRemoveAccountSaving() {
		Bank bank=new Bank();
		Person person=new Person(1,"a",11,"a");
		bank.addPerson(person);      
		
		
		SavingAccount tester = new SavingAccount(1,1,Capitalizat.CAPITALIZAT); 
		SavingAccount tester2 = new SavingAccount(2,1,Capitalizat.CAPITALIZAT); 
		SavingAccount tester3 = new SavingAccount(3,1,Capitalizat.CAPITALIZAT); 
		SavingAccount tester4 = new SavingAccount(4,1,Capitalizat.CAPITALIZAT); 
		bank.addAccount(person, tester);
		bank.addAccount(person, tester2);
		bank.addAccount(person, tester3);
		bank.addAccount(person, tester4);
		
		bank.removeAccount(person, tester);
		bank.removeAccount(person, tester2);
		bank.removeAccount(person, tester3);
		bank.removeAccount(person, tester4);
		
        assertEquals(false, bank.getSavingAccounts().contains(tester));
        assertEquals(false, bank.getSavingAccounts().contains(tester2));
        assertEquals(false, bank.getSavingAccounts().contains(tester3));
        assertEquals(false, bank.getSavingAccounts().contains(tester4));
    }
	
	@Test
    public void testRemoveAccountSpending() {
		Bank bank=new Bank();
		Person person=new Person(1,"a",11,"a");
		bank.addPerson(person);      
		
		
		SpendingAccount tester = new SpendingAccount(1,1,SpendingAccountType.ADULT); 
		SpendingAccount tester2 = new SpendingAccount(2,1,SpendingAccountType.ADULT); 
		SpendingAccount tester3 = new SpendingAccount(3,1,SpendingAccountType.ADULT); 
		SpendingAccount tester4 = new SpendingAccount(4,1,SpendingAccountType.ADULT); 
		bank.addAccount(person, tester);
		bank.addAccount(person, tester2);
		bank.addAccount(person, tester3);
		bank.addAccount(person, tester4);
		
		bank.removeAccount(person, tester);
		bank.removeAccount(person, tester2);
		bank.removeAccount(person, tester3);
		bank.removeAccount(person, tester4);

        assertEquals(false, bank.getSpendingAccounts().contains(tester));
        assertEquals(false, bank.getSpendingAccounts().contains(tester2));
        assertEquals(false, bank.getSpendingAccounts().contains(tester3));
        assertEquals(false, bank.getSpendingAccounts().contains(tester4));
    }
}
