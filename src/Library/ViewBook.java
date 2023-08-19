package Library;

import java.util.ArrayList;

public class ViewBook implements IOOperatin {

	@Override
	public void oper(Database database, User user) {
	
		ArrayList<Book> books = database.getAllBooks();
		System.out.println("Name\t\tAuthor\t\tPublisher\t\tCLA\tQty\tPrice"
				+ "\tBrw cps");
		for(Book b : books) {
			System.out.println(b.getName()+"\t\t"+b.getAuthor()+"\t\t"+b.getPublisher()+"\t\t"+
				b.getAdress()+"\t"+b.getQty()+"\t"+b.getPrice()+"\t"+b.getBrwcopies());
			
		}
		
		System.out.println();
		user.menu(database, user);
		
	}
	

}
