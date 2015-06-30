package bankomat.zadaca;

import java.util.ArrayList;

//klasa Bankomat sadrzi data field-s i metode potrebne za sve ono sto pripada bankomatu
public class Bankomat {
	private int deset = 60;
	private int dvadeset = 30;
	private int pedeset = 20;
	private int sto = 10;
	private int balance = (deset * 10) + (dvadeset * 20) + (pedeset * 50)
			+ (sto * 100);

	ArrayList<Korisnik> listaKorisnika = new ArrayList<>();

	Bankomat() {

	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getDeset() {
		return deset;
	}

	public void setDeset(int deset) {
		this.deset = deset;
	}

	public void setDvadeset(int dvadeset) {
		this.dvadeset = dvadeset;
	}

	public void setPedeset(int pedeset) {
		this.pedeset = pedeset;
	}

	public void setSto(int sto) {
		this.sto = sto;
	}

	public int getDvadeset() {
		return dvadeset;
	}

	// metodi addDeset, addDvadeset, addPedeset, addSto omogucavaju da
	// administrator dopuni broj novcanica u bankomatu
	public void addDeset(int deset) {
		if (this.deset + deset <= 100) {
			this.deset += deset;
			System.out
					.println("Stanje uspjesno dopunjeno, broj novcanica od 10KM je: "
							+ getDeset() + "\n");
		} else {
			System.out
					.println("Nemoguce dodati iznos, trenutno u bankomatu postoji "
							+ this.getDeset()
							+ " moguce je dodati jos "
							+ (100 - this.getDeset()) + " novcanica.");
		}
	}

	public void addDvadeset(int dvadeset) {
		if (this.dvadeset + dvadeset <= 100) {
			this.dvadeset += dvadeset;
			System.out
					.println("Stanje uspjesno dopunjeno, broj novcanica od 20KM je: "
							+ getDvadeset() + "\n");
		} else {
			System.out
					.println("Nemoguce dodati iznos, trenutno u bankomatu postoji "
							+ this.getDvadeset()
							+ " moguce je dodati jos "
							+ (100 - this.getDvadeset()) + " novcanica.");
		}

	}

	public int getPedeset() {
		return pedeset;
	}

	public void addPedeset(int pedeset) {
		if (this.pedeset + pedeset <= 100) {
			this.pedeset += pedeset;
			System.out
					.println("Stanje uspjesno dopunjeno, broj novcanica od 50KM je: "
							+ getPedeset() + "\n");
		} else {
			System.out
					.println("Nemoguce dodati iznos, trenutno u bankomatu postoji "
							+ this.getPedeset()
							+ " moguce je dodati jos "
							+ (100 - this.getPedeset()) + " novcanica.");

		}
	}

	public int getSto() {
		return sto;
	}

	public void addSto(int sto) {
		if (this.sto + sto <= 100) {
			this.sto += sto;
			System.out
					.println("Stanje uspjesno dopunjeno, broj novcanica od 100KM je: "
							+ getSto() + "\n");
		} else {
			System.out
					.println("Nemoguce dodati iznos, trenutno u bankomatu postoji "
							+ this.getSto()
							+ " moguce je dodati jos "
							+ (100 - this.getSto()) + " novcanica.");
		}
	}

	public ArrayList<Korisnik> getListaKorisnika() {
		return listaKorisnika;
	}

	public void setListaKorisnika(ArrayList<Korisnik> listaKorisnika) {
		this.listaKorisnika = listaKorisnika;
	}

	// deduct metodi umanjuju broj novcanica u bankomatu nakon sto se izvrsi
	// isplata istim
	public void deduct100(int amount) {
		if (sto - amount >= 0) {
			this.sto = (sto - amount);
		}
	}

	public void deduct50(int amount) {
		if (pedeset >= 0) {
			pedeset = pedeset - amount;
		}
	}

	public void deduct20(int amount) {
		if (dvadeset - amount >= 0) {
			dvadeset = dvadeset - amount;
		}
	}

	public void deduct10(int amount) {
		if (deset - amount >= 0) {
			deset = deset - amount;
		}
	}

}

class Korisnik {
	// klasa korisnik sadrzi podatke i metode vezane za korisnika
	private String userName;
	private int balance;
	private boolean admin = false;
	private String password;

	Korisnik() {

	}

	Korisnik(String userName, String password, int balance) {
		this.userName = userName;
		this.balance = balance;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	// metod withdraw umanjuje stanje na racunu korisnika za podignuti iznos
	// ukoliko je transakcija uspijesno izvrsena
	public void withdraw(int amount) {
		if (balance - amount >= 0) {
			balance = balance - amount;
		} else {
			System.out.println("Stanje na vasem racunu nije dovoljno:");
		}

	}

}
