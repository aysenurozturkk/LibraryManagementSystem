package Library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
	
	private ArrayList<User> users= new ArrayList<User>();						//Kullanıcıları tutan bir ArrayList oluşturuluyor.
	private ArrayList<String> usernames= new ArrayList<String>();				//Kullanıcı isimlerini tutan bir ArrayList oluşturuluyor.
	private ArrayList<Book> books = new ArrayList<Book>();						//Kitapları tutan bir ArrayList oluşturuluyor.
	private ArrayList<String> booknames = new ArrayList<String>();				//Kitap isimlerini tutan bir ArrayList oluşturuluyor.
	private ArrayList<Order> orders = new ArrayList<Order>();					//Siparişleri tutan bir ArrayList oluşturuluyor.
	private ArrayList<Borrowing> borrowings = new ArrayList<Borrowing>();		// Ödünç alma işlemlerini tutan bir ArrayList oluşturuluyor.
	
	
	
	private File usersfile = new File("D:\\java projelerim\\LibraryManagementSystem\\data\\Users");
	private File booksfile = new File("D:\\java projelerim\\LibraryManagementSystem\\data\\Books");
	private File ordersfile = new File("D:\\java projelerim\\LibraryManagementSystem\\data\\Orders");
	private File borrowingsfile = new File("D:\\java projelerim\\LibraryManagementSystem\\data\\Borrowings");
	private File folder = new File("D:\\java projelerim\\LibraryManagementSystem\\data");
	
	public Database() {							//Yapıcı (constructor) metodu, veritabanını başlatmak ve dosyaları kontrol etmek için kullanılıyor.
		if(!folder.exists()) {					//Eğer folder adında belirtilen dizin (kayıtların saklandığı dizin) henüz mevcut değilse...
			folder.mkdirs();					//Bu komut ile yeni dizin oluşturulur. mkdirs() metodu, belirtilen dizini ve gerekirse üst dizinleri oluşturur. Bu sayede veritabanı dosyaları için bir dizin oluşturulmuş olur.
		}
		if(!usersfile.exists()) {				//Eğer usersfile adında belirtilen kullanıcı verilerinin saklandığı dosya mevcut değilse...
			try {
				usersfile.createNewFile();		//Bu try-catch bloğu ile yeni bir kullanıcı verileri dosyası oluşturulmaya çalışılır. Eğer hata oluşursa (Exception), hata görmezden gelinir (catch bloğu).
			} catch (Exception e) {
			
		}
}
			if (!booksfile.exists()) {
				try {
					booksfile.createNewFile();
				} catch (Exception e) {}	
			}
			if (!ordersfile.exists()) {
				try {
					ordersfile.createNewFile();
				} catch (Exception e) {}	
			}
			if (!borrowingsfile.exists()) {
				try {
					borrowingsfile.createNewFile();
				} catch (Exception e) {}	
			}
			
		getUsers();							//Kullanıcı verilerini almak için getUsers() metodunu çağırır. Bu metot, kullanıcı verilerini dosyadan okur ve ilgili listeye ekler.
		getBooks();
		getOrders();
		getBorrowings();
		
	}
	
	public void AddUser(User s) {			//Bu metot, yeni bir kullanıcıyı veritabanına eklemek için kullanılır.
		users.add(s);						//users adlı ArrayList içine (users ArrayListi Database sınıfının özelliğidir) s adlı kullanıcı nesnesi ekleniyor. Bu, yeni kullanıcının veritabanına eklenmesini sağlar.
		usernames.add(s.getName());			//Kullanıcının adı (getName() metodu ile alınır) usernames adlı ArrayList içine ekleniyor. Bu, kullanıcı adlarını takip etmek için kullanılır.
		saveUsers();						// Bu metot, kullanıcı verilerini dosyaya kaydetmek için kullanılır. 
	}
	
	public int login(String phonenumber, String email) {
		int n= -1;							//n adında bir tamsayı değişkeni oluşturuluyor ve -1 ile başlatılıyor. Bu değişken, eğer giriş başarısız olursa -1 değeri ile kalacaktır.
		for(User s : users) {				//Tüm users listesindeki kullanıcıları teker teker incelemek için bir döngü başlatılıyor. Her bir kullanıcı s adlı değişkene atanacak.
			if(s.getPhonenumber().matches(phonenumber)&& s.getMail().matches(email)) {
				n = users.indexOf(s);		// n değişkenine, doğru kullanıcının indeksi atanıyor. Bu, daha sonra doğru kullanıcının hangi indekste olduğunu bilmemizi sağlar.
				break;
			}
		}
		return n;							// Giriş yapmaya çalışan kullanıcının indeksini döndürüyor. Eğer giriş başarısız olursa, -1 dönecektir.
	}
	
	public User getUser(int n) {			//Verilen indeksteki kullanıcıyı döndüren metot.
		return users.get(n);				//users adlı ArrayList içinde, belirtilen n indeksine sahip olan kullanıcı nesnesini döndürür. Bu sayede verilen indeksteki kullanıcıya erişebiliriz.
	}
	
	public void Addbook(Book book) {
		books.add(book);
		booknames.add(book.getName());
		saveBooks();
		}
	
	private void getUsers() {			//Kullanıcı verilerini dosyadan okuyarak kullanıcı listesini oluşturan metot.
		String text1 = "";				//text1 adında bir boş metin (string) oluşturuluyor. Bu metin, dosyadan okunan verileri tutmaya yöneliktir.
		try {							//istisnai bir durum oluşabileceğini öngördüğümüz kodları try bloğu içine alırız.
										//Eğer böyle bir durum oluşursa program kırılmak yerine hatayı bloktan dışarı fırlatır
		
			BufferedReader br1 = new BufferedReader(new FileReader(usersfile));
			String s1;					//s1 adında bir string değişkeni oluşturuluyor. Bu değişken, dosyadan okunan her satırı temsil edecek.
			while((s1 = br1.readLine()) !=null) {		//Dosyanın sonuna kadar (null olmayana kadar) her satırı okumak için bir döngü başlatılıyor.
				text1 = text1 + s1;						//Okunan her satır, text1 metinine ekleniyor. Bu, dosyadan okunan tüm verilerin bir metin içinde toplanmasını sağlar.
			}
			br1.close();								//Dosya okuma işlemi tamamlandığında, BufferedReader kapatılıyor.
		} catch (Exception e) {      // Bu fırlatılan hata catch blokları tarafından yakalanır ve makul bir şekilde yönetilir.
			System.err.println(e.toString()); //hata çıktısıdır. bir dosyaya yazar.
		}
		
		if(!text1.matches("")|| !text1.isEmpty()) {			// Eğer okunan veri metni (text1) boş değilse...
			String[] a1 = text1.split("<NewUser/>");		//Okunan veriyi <NewUser/> etiketi üzerinden bölen bir dizi (a1) oluşturuluyor. Bu, her bir kullanıcının verisinin ayrıldığını belirtir.
			for (String s :a1 ) {							// Elde edilen her bir kullanıcı verisi (a1 dizisindeki her öğe), bir döngü içinde işlenir.
				String[] a2 = s.split("<N/>");				//Her kullanıcı verisi (s), <N/> etiketi üzerinden bölen bir dizi (a2) oluşturur. Bu dizi, kullanıcının adı, e-posta, telefon numarası ve türünü içerir.
				if (a2[3].matches("Admin")){				//Eğer kullanıcının türü "Admin" ise...
						 User user = new Admin(a2[0], a2[1], a2[2]);
						 users.add(user);
						 usernames.add(user.getName());
				}else {
					User user = new NormalUser(a2[0], a2[1], a2[2]);
					users.add(user);
					usernames.add(user.getName());
				}
			}
		}
	}
	
	private void saveUsers() {									// Kullanıcı verilerini dosyaya yazan metot.
		String text1="";										//text1 adında boş bir metin (string) oluşturuluyor. Bu metin, kullanıcı verilerini dosyaya yazmak için kullanılacak.	
		for(User user: users) {									//users listesindeki her bir kullanıcı nesnesi üzerinde dönmek için bir döngü başlatılıyor.
			text1 = text1 + user.toString()+"<NewUser/>\n";			//Her bir kullanıcı nesnesinin toString() metodu çağrılarak, kullanıcı verisinin metine eklenmesi sağlanır. Ayrıca <NewUser/> etiketi eklenerek her kullanıcının verisinin sonlandığını belirtilir ve \n karakteri ile yeni bir satıra geçilir.
		}
		try {
			PrintWriter pw = new PrintWriter(usersfile);			//PrintWriter sınıfı ile usersfile adındaki dosya yazma işlemi başlatılıyor. Bu, kullanıcı verilerini dosyaya yazmak için dosyanın açılmasını sağlar.
			pw.print(text1);								//text1 metni, dosyaya yazdırılır. Bu, kullanıcı verilerini dosyaya kaydetmeyi sağlar.
			pw.close();										//Dosya yazma işlemi tamamlandığında, PrintWriter kapatılır.
			
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	
	private void saveBooks() {			//Kitap verilerini dosyaya yazan metot.
		String text1="";
		for(Book book: books) {
			text1 = text1 + book.toString2()+"<NewBook/>\n";
		}
		try {
			PrintWriter pw = new PrintWriter(booksfile);
			pw.print(text1);
			pw.close();
		
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private void getBooks() {			//Kitap verilerini dosyadan okuyarak kitap listesini oluşturan metot.
		String text1 = "";
		try {					//istisnai bir durum oluşabileceğini öngördüğümüz kodları try bloğu içine alırız.
								//Eğer böyle bir durum oluşursa program kırılmak yerine hatayı bloktan dışarı fırlatır
		
			BufferedReader br1 = new BufferedReader(new FileReader(booksfile));
			String s1;
			while((s1 = br1.readLine()) !=null) {
				text1 = text1 + s1;
			}
			br1.close();
		} catch (Exception e) {      // Bu fırlatılan hata catch blokları tarafından yakalanır ve makul bir şekilde yönetilir.
			System.err.println(e.toString()); //hata çıktısıdır. bir dosyaya yazar.
		}
		
		if(!text1.matches("")|| !text1.isEmpty()) {
			String[] a1 = text1.split("<NewBook/>");
			for (String s :a1 ) {
				Book book = parseBook(s);
				books.add(book);
				booknames.add(book.getName());
			}
		}
	}
	
	public Book parseBook (String s) {
		String[] a = s.split("<N/>");
		Book book = new Book();
		book.setName(a[0]);
		book.setAuthor(a[1]);
		book.setPublisher(a[2]);
		book.setAdress(a[3]);
		book.setQty(Integer.parseInt(a[4]));
		book.setPrice(Double.parseDouble(a[5]));
		book.setBrwcopies(Integer.parseInt(a[6]));
		return book;
		}
	
	public ArrayList<Book> getAllBooks(){			// Tüm kitapları içeren bir ArrayList döndüren metot.
		return books;
	}
	
	public int getBook(String bookname) {			//Verilen isimdeki kitabın indeksini döndüren metot.
		int i = -1;
		for(Book book : books) {
			if(book.getName().matches(bookname)) {
				i = books.indexOf(book);	
			}
		}
		return i;
	}
	
	public Book getBook(int i) {				//Verilen indeksteki kitabı döndüren metot.
		return books.get(i);
	}
	
	public void deleteBook(int i) {				//Verilen indeksteki kitabı silen metot.
		books.remove(i);
		booknames.remove(i);
		saveBooks();
	}
	
	public void deleteAllData() {				//Tüm verileri silen metot.
		if(usersfile.exists()) {
			try {
				usersfile.delete();
			} catch (Exception e) {}	
		}
		if (booksfile.exists()) {
			try {
				booksfile.createNewFile();
			} catch (Exception e) {}	
		}
		if (ordersfile.exists()) {
			try {
				ordersfile.createNewFile();
			} catch (Exception e) {}	
		}
		if (borrowingsfile.exists()) {
			try {
				borrowingsfile.createNewFile();
			} catch (Exception e) {}	
		}
	}
	
		public void addOrder(Order order, Book book, int bookindex) {
			orders.add(order);
			books.set(bookindex, book);
			saveOrders();
			saveBooks();
		}
		
		private void saveOrders() {
			String text1="";
			for(Order order: orders) {
				text1 = text1 + order.toString2()+"<NewOrder/>\n";
			}
			try {
				PrintWriter pw = new PrintWriter(ordersfile);
				pw.print(text1);
				pw.close();
			
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		private void getOrders() {
			String text1 = "";
			try {					
			
				BufferedReader br1 = new BufferedReader(new FileReader(ordersfile));
				String s1;
				while((s1 = br1.readLine()) !=null) {
					text1 = text1 + s1;
				}
				br1.close();
			} catch (Exception e) {      
				System.err.println(e.toString()); 
			}
			
			if(!text1.matches("")|| !text1.isEmpty()) {
				String[] a1 = text1.split("<NewOrder/>");
				for (String s : a1 ) {
					Order order = parseOrder(s);
					orders.add(order);
				}
			}
		}
		
		public boolean userExists(String name) {			//Verilen isimde bir kullanıcının varlığını kontrol eden metot.
			boolean f = false;
			for(User user : users) {
				if(user.getName().toLowerCase().matches(name.toLowerCase())) {
					f=true;
					break;
				}
			} 
			return f;
		}
		
		private User getUserByName(String name) {			//İsimle kullanıcı bulan metot.
			User u = new NormalUser("");
			for(User user : users) {
				if(user.getName().matches(name)) {
					u = user;
					break;
				}
		} 
			return u;
	}
		private Order parseOrder(String s) {				//Metni okuyarak bir sipariş nesnesi oluşturan metot.
			String[] a = s.split("N/>");
			Order order = new Order(books.get(getBook(a[0])), getUserByName(a[1]), 
					Double.parseDouble(a[2]), Integer.parseInt(a[3]));
			return order;
		}
		
		public ArrayList<Order> getAllOrders(){				//Tüm siparişleri içeren bir ArrayList döndüren metot.
			return orders;
		}
		
		private void saveBorrowings() {
			String text1="";
			for(Borrowing borrowing: borrowings) {
				text1 = text1 + borrowing.toString2()+"<NewBorrowing/>\n";
			}
			try {
				PrintWriter pw = new PrintWriter(borrowingsfile);
				pw.print(text1);
				pw.close();
			
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		
		 private void getBorrowings() {
			 String text1 = "";
				try {					
				
					BufferedReader br1 = new BufferedReader(new FileReader(borrowingsfile));
					String s1;
					while((s1 = br1.readLine()) !=null) {
						text1 = text1 + s1;
					}
					br1.close();
				} catch (Exception e) {      
					System.err.println(e.toString()); 
				}
				
				if(!text1.matches("")|| !text1.isEmpty()) {
					String[] a1 = text1.split("<NewBorrowing\n>");
					for (String s : a1 ) {
						Borrowing borrowing = parseBorrowing(s);
						borrowings.add(borrowing);
					}
				}
		 }
		 
		 private Borrowing parseBorrowing(String s) {								//Metni okuyarak bir ödünç alma nesnesi oluşturan metot.
			 String[] a = s.split("<N/>");
			 DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
			 LocalDate start = LocalDate.parse(a[0], formatter);
			 LocalDate finish = LocalDate.parse(a[1], formatter);
			 Book book = getBook(getBook(a[3]));
			 User user = getUserByName(a[4]);
			 Borrowing brw = new Borrowing(start, finish, book, user);
			 return brw;
		 }
		 
		 public void borrowBook(Borrowing brw, Book book, int bookindex) {			//Kitap ödünç almayı gerçekleştiren metot.
			 borrowings.add(brw);
			 books.set(bookindex, book);
			 saveBorrowings();
			 saveBooks();
		 }
		 
		 public ArrayList<Borrowing> getBrws() {									//Tüm ödünç alma işlemlerini içeren bir ArrayList döndüren metot.
			 return borrowings;
		 }
		 
		 public void returnBook(Borrowing b, Book book, int bookindex) {			//Kitabı geri verme işlemini gerçekleştiren metot.
			 borrowings.remove(b);
			 books.set(bookindex, book);
			 saveBorrowings();
			 saveBooks();
		 }
}

		
