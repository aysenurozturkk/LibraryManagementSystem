package Library;



public abstract class User {				// Soyut bir User sınıfı tanımlanıyor.
	
	protected String name;
	protected String email;					//Bu değişkenlere alt sınıflardan erişim sağlanabilir (protected).
	protected String phonenumber;
	protected IOOperatin[] operations;		//Kullanıcının yaptığı işlemleri (operations) takip etmek için bir IOOperation dizisi (array) tanımlanıyor. 
	
	public User() {}						//Parametresiz bir yapıcı (constructor) tanımlanıyor. Muhtemelen alt sınıflar için kullanılmak üzere burada yer alıyor.
	
	public User(String name){				// İsim parametresi alarak bir yapıcı tanımlanıyor. İsim bilgisini alarak kullanıcı oluşturma amacına hizmet ediyor.
		this.name = name;
	}
	
	public User(String name, String email, String phonenumber) {			//Bu yapıcı, kullanıcı bilgilerini eksiksiz bir şekilde alarak oluşturma amacına hizmet eder.
		this.name = name;
		this.email = email;
		this.phonenumber = phonenumber;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMail() {					//Kullanıcının ismi, e-posta adresi ve telefon numarasını döndüren üye metotlar (getter) tanımlanıyor.
		return email;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	
	abstract public String toString();			//Soyut bir metot olan toString() tanımlanıyor. Alt sınıflar bu metodu ezerek nesnenin dize temsilini sağlarlar. (Örneğin, kullanıcının ismi, e-posta adresi vb.)
	
	
	abstract public void menu(Database database, User user);				//Soyut bir metot olan menu() tanımlanıyor.
																			//Bu metodun parametreleri, Database nesnesini ve ilgili kullanıcı örneğini alır. Alt sınıflar kendi gereksinimlerine uygun şekilde bu metodu uygularlar.
}
