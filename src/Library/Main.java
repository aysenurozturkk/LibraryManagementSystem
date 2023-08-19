package Library;

import java.util.Scanner;

public class Main {
	
	static Scanner s; 				// nesneler tanımlanıyor.Bu nesneler sınıfın içindeki 
	static Database database;		//tüm metotlarda kullanılabilecek şekilde static olarak tanımlanıyor.
	public static void main(String[] args) {
		
		database= new Database();
		System.out.println("Welcome to Library Management System!");
		
			int num;				//Kullanıcının seçimini tutmak için num adında bir tamsayı değişkeni tanımlanıyor.
			System.out.println("0. Exit\n1. Login\n2. New User.");
			s = new Scanner(System.in);
			num = s.nextInt();		// Kullanıcının girdiği tamsayı değeri num değişkenine atanıyor.
			switch (num) {
				case 1: login(); break;
				case 2: newuser(); break;
			}


	}
	
	private static void login() {
		System.out.println("Enter phone number:");
		String phonenumber = s.next();
		System.out.println("Enter email:");
		String email = s.next();
		int n = database.login(phonenumber,email);		//Database sınıfındaki login metodu çağrılarak kullanıcının giriş yapma durumu kontrol ediliyor.
		if(n != -1) {									// Eğer -1 değilse (yani giriş başarılıysa), 
			User user = database.getUser(n);
			user.menu(database, user);
		}else {
			System.out.println("User doesn't exist!");
		}
		
	}
	
	private static void newuser() {
		System.out.println("Enter name:");
		String name = s.next();
		if (database.userExists(name)) {				//Kullanıcının isminin veritabanında var olup olmadığı kontrol ediliyor.
			System.out.println("User exists!");			//Eğer kullanıcı varsa User exists! mesajı yazdırılıyor ve newuser metodu tekrar çağrılıyor.
			newuser();
		}
		System.out.println("Enter phone number:");
		String phonenumber = s.next();
		System.out.println("Enter email:");
		String email = s.next();
		System.out.println("1. Admin\n2. Normal User");
		int n2 = s.nextInt();
		User user;
		if(n2 == 1) {
			user = new Admin(name, email, phonenumber);
		}else {
			user = new NormalUser(name, email, phonenumber);
		}
		database.AddUser(user);
		user.menu(database, user);
	}

}
