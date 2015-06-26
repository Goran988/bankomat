package bankomat.zadaca;

import java.util.ArrayList;

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

	public void addDeset(int deset) {
		if ((balance += 10 * deset) <= 3200 && this.deset + deset <= 60) {
			this.deset = deset;
			balance += 10 * deset;
		} else {
			System.out.println("Nemoguce dodati iznos.");
		}
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

	public void addDvadeset(int dvadeset) {
		if ((balance += 20 * dvadeset) <= 3200
				&& this.dvadeset + dvadeset <= 30) {
			this.dvadeset = dvadeset;
			balance += 20 * dvadeset;
		} else {
			System.out.println("Nemoguce dodati iznos.");
		}
	}

	public int getPedeset() {
		return pedeset;
	}

	public void addPedeset(int pedeset) {
		if ((balance += 50 * pedeset) <= 3200 && this.pedeset + pedeset <= 20) {
			this.pedeset = pedeset;
			balance += 50 * pedeset;
		} else {
			System.out.println("Nemoguce dodati iznos.");
		}
	}

	public int getSto() {
		return sto;
	}

	public void addSto(int sto) {
		if ((balance += 100 * sto) <= 3200 && this.sto + sto <= 10) {
			this.sto = sto;
			balance += 100 * sto;
		} else {
			System.out.println("Nemoguce dodati iznos.");
		}
	}

	public ArrayList<Korisnik> getListaKorisnika() {
		return listaKorisnika;
	}

	public void setListaKorisnika(ArrayList<Korisnik> listaKorisnika) {
		this.listaKorisnika = listaKorisnika;
	}

	public void withdraw(int amount) {
		if (balance - amount >= 0) {
			balance = balance - amount;

		}
	}

	public void deduct100(int amount) {
		if (sto - amount >= 0) {
			this.sto=(sto - amount);
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

	public void withdraw(int amount) {
		if (balance - amount >= 0) {
			balance = balance - amount;
		} else {
			System.out.println("Stanje na vasem racunu nije dovoljno:");
		}

	}
}
