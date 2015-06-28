package bankomat.zadaca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BankomatTest {
	public static void main(String[] args) throws IOException,
			FileNotFoundException {
		Scanner input = new Scanner(System.in);
		Bankomat b1 = new Bankomat();
		Korisnik admin = new Korisnik("Goran", "1234", 0);
		b1.listaKorisnika.add(admin);
		admin.setAdmin(true);
		b1 = ucitavanjeStanja(b1); // metod postavlja stanje u bankomatu tako
									// sto cita iz file-a("stanje.txt")
		b1.listaKorisnika.addAll(popunjavaListu(b1)); // metod popunjava listu
														// citajuci iz
														// file-a("datoteka.txt")
		System.out.println("Unesite USERNAME i PASSWORD korisnika: ");
		String userName = input.nextLine();
		String userPass = input.nextLine();
		Korisnik korisnik = new Korisnik();
		boolean repeat = true;
		boolean postojeciKorisnik = false;

		// metod pretrazuje listu da bi pronasao koji se korisnik ulogovao admin
		// se razlikuje po boolean vrijednosti isAdmin
		for (Korisnik k : b1.getListaKorisnika()) {
			if (k.getUserName().equals(userName)
					&& (k.getPassword().equals(userPass))) {
				postojeciKorisnik = true;
				if (k.isAdmin()) {
					korisnik = k;
					System.out.println("Ulogovani ste kao administrator.\n");
					break;
				} else {
					korisnik = k;
					System.out.println("Dobrodosli:\n");
					break;
				}
			}
		}
		while (repeat) {
			if (!postojeciKorisnik) {
				System.out
						.println("Pogresan USERNAME, PASSWORD ili nepostojeci korisnik!");
				System.exit(1);

			}

			if (korisnik.isAdmin()) {
				System.out
						.println("Izaberite zeljenu operaciju:\n1. Pregled stanja na bankomatu \n2. Dodavanje/brisanje novog korisnika \n3. Dopuna novcanica\n4. Logout\n");
			} else {
				System.out
						.println("Izaberite zeljenu operaciju:\n1. Pregled stanja na racunu \n2. Podizanje novca \n3. Logout\n");

			}
			int izborKorisnik = input.nextInt();
			// if-ovi od 60 linije do 106 linije sluze da vode admina kroz
			// operacije koje obavlja
			if (korisnik.isAdmin() && izborKorisnik == 1) {
				System.out
						.println("Broj novcanica od 10KM je: "
								+ b1.getDeset()
								+ "\nBroj novcanica od 20KM je: "
								+ b1.getDvadeset()
								+ "\nBroj novcanica od 50KM je: "
								+ b1.getPedeset()
								+ "\nBroj novcanica od 100KM je: "
								+ b1.getSto()
								+ "\nUkupan iznos na bankomatu je: "
								+ ((b1.getSto() * 100) + (b1.getPedeset() * 50)
										+ (b1.getDvadeset() * 20) + (b1
										.getDeset() * 10)));
				System.out.println();
			} else if (korisnik.isAdmin() && izborKorisnik == 2) {
				System.out
						.println("1. Dodavanje novog korisnika \n2. Brisanje postojeceg korisnika\n");
				int korisnikIzbor = input.nextInt();
				if (korisnikIzbor == 1) {
					System.out
							.println("Unesite USERNAME, PASSWORD i iznos na racunu novog korisnika\n");
					Korisnik k2 = new Korisnik();
					k2.setUserName(input.next());
					k2.setPassword(input.next());
					k2.setBalance(input.nextInt());
					b1.listaKorisnika.add(k2);

				}
				if (korisnikIzbor == 2) {
					System.out
							.println("Unesite USERNAME korisnika kog zelite brisati: \n");
					String imeZaBrisanje = input.next();
					for (int i = 0; i < b1.listaKorisnika.size(); i++) {
						if (imeZaBrisanje.equals(b1.listaKorisnika.get(i)
								.getUserName())) {
							b1.getListaKorisnika().remove(
									b1.listaKorisnika.get(i));
						}
					}
				}
			} else if (korisnik.isAdmin() && izborKorisnik == 3) {
				b1 = dopunaNovcanica(b1);

			} else if (korisnik.isAdmin() && izborKorisnik == 4) {
				repeat = false;
			} else {

			}
			// if-ovi od 111 linije do 134 linije koda vode obicnog korisnika
			// kroz operacije koje su mu dostupne
			if (!korisnik.isAdmin() && izborKorisnik == 1) {
				System.out.println("Stanje na racunu je: "
						+ korisnik.getBalance() + "\n");
			} else if (!korisnik.isAdmin() && izborKorisnik == 2) {
				System.out.println("Unesite iznos koji zelite podici\n");
				int iznos = input.nextInt();
				if ((b1.getBalance() - iznos >= 0)
						&& (korisnik.getBalance() - iznos >= 0)) {
					korisnik.withdraw(iznos);
					b1.setBalance(b1.getBalance() - iznos);
					b1 = deductBils(iznos, b1);
				} else {
					if (korisnik.getBalance() - iznos < 0) {
						System.out
								.println("Stanje na racunu nije dovoljno za transakciju, vase stanje na racunu je:  "
										+ korisnik.getBalance() + "\n");
					} else {
						System.out
								.println("Nedovoljno sredstava za vasu transakciju, molimo idite u najblizu poslovnicu.\n");
					}
				}
			} else if (!korisnik.isAdmin() && izborKorisnik == 3) {
				repeat = false;
			}

		}
		upisStanjaUFile(b1); // upisuje stanje novcanica u file da bi se
								// sacuvalo za sledece pokretanje programa
		upisKorisnikaUFile(b1);	// upisuje stanje liste korisnika u file

	}

	public static Bankomat deductBils(int amount, Bankomat b)
			throws IOException {
		int stotine = 0;
		int pedeset = 0;
		int dvadeset = 0;
		int deset = 0;
		int hundred = b.getSto();
		int fifty = b.getPedeset();
		int twenty = b.getDvadeset();
		int ten = b.getDeset();
		while (amount > 0) {
			if (amount - 100 >= 0 && hundred > 0) {
				amount = amount - 100;
				hundred--;
				stotine++;
			} else if (amount - 50 >= 0 && fifty > 0) {
				amount = amount - 50;
				fifty--;
				pedeset++;
			} else if (amount - 20 >= 0 && twenty > 0) {
				amount = amount - 20;
				twenty--;
				dvadeset++;
			} else if (amount - 10 >= 0 && ten > 0) {
				amount = amount - 10;
				ten--;
				deset++;

				System.out
						.println("Nemoguca isplata, molimo vas, idite u najblizu poslovnicu.");
				break;
			}
			if (amount == 0 && stotine <= b.getSto()
					&& pedeset <= b.getPedeset() && dvadeset <= b.getDvadeset()
					&& deset <= b.getDeset()) {
				b.setSto((b.getSto() - stotine));
				b.deduct50(pedeset);
				b.deduct20(dvadeset);
				b.deduct10(deset);
				System.out.println("Isplata:\n");
				if (stotine > 0) {
					System.out.println("100 X " + stotine);
				}
				if (pedeset > 0) {
					System.out.println("50 X " + pedeset);
				}
				if (dvadeset > 0) {
					System.out.println("20 X " + dvadeset);
				}
				if (deset > 0) {
					System.out.println("10 X " + deset);
				}
			}
			// else {
			// System.out
			// .println("Transakcija nije moguca, molimo vas idite u najblizu poslovnicu.");
			// }
		}
		return b;

	}

	public static ArrayList<Korisnik> popunjavaListu(Bankomat b)
			throws FileNotFoundException {
		ArrayList<Korisnik> lista = new ArrayList<>();
		File file = new File("datoteka.txt");
		Scanner izListe = new Scanner(file);
		while (izListe.hasNext()) {
			Korisnik korisnikFile = new Korisnik();
			korisnikFile.setUserName(izListe.next());
			korisnikFile.setPassword(izListe.next());
			korisnikFile.setBalance(izListe.nextInt());
			lista.add(korisnikFile);

		}
		izListe.close();
		return lista;

	}

	public static void upisKorisnikaUFile(Bankomat b) throws IOException {
		File file = new File("datoteka.txt");
		FileWriter upis = new FileWriter(file);
		try {
			for (int i = 1; i < b.listaKorisnika.size(); i++) {
				upis.write(b.listaKorisnika.get(i).getUserName() + " ");
				upis.write(b.listaKorisnika.get(i).getPassword() + " ");
				upis.write(String.valueOf(b.listaKorisnika.get(i).getBalance())
						+ " \n");

			}
		} finally {

			upis.close();
			System.exit(1);
		}

	}

	public static Bankomat ucitavanjeStanja(Bankomat b) throws IOException {
		File file = new File("Stanje.txt");
		Scanner fromFile = new Scanner(file);
		while (fromFile.hasNextInt()) {
			b.setDeset(fromFile.nextInt());
			b.setDvadeset(fromFile.nextInt());
			b.setPedeset(fromFile.nextInt());
			b.setSto(fromFile.nextInt());
		}
		return b;
	}

	public static void upisStanjaUFile(Bankomat b) throws IOException {
		File file = new File("stanje.txt");
		FileWriter upis = new FileWriter(file);
		try {
			upis.write(String.valueOf(b.getDeset() + " "));
			upis.write(String.valueOf(b.getDvadeset() + " "));
			upis.write(String.valueOf(b.getPedeset() + " "));
			upis.write(String.valueOf(b.getSto()));
		} finally {
			upis.close();
		}
	}

	public static Bankomat dopunaNovcanica(Bankomat b) {
		Scanner input = new Scanner(System.in);
		System.out
				.println("Izaberite novcanice koje zelite dopuniti i unesite kolicinu novcanica:\n1. 100KM\n2. 50KM\n3. 20KM\n4. 10KM");
		int izborKorisnika = input.nextInt();
		if (izborKorisnika == 1) {
			b.addSto(input.nextInt());
		} else if (izborKorisnika == 2) {
			b.addPedeset(input.nextInt());
		} else if (izborKorisnika == 3) {
			b.addDvadeset(input.nextInt());
		} else if (izborKorisnika == 4) {
			b.addDeset(input.nextInt());
		}
		// input.close();
		return b;
	}
}
