package bankomat.zadaca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankomatTest {
	public static void main(String[] args) throws IOException,
			FileNotFoundException, InputMismatchException {
		Scanner input = new Scanner(System.in);
		Bankomat b1 = new Bankomat();
		Korisnik admin = new Korisnik("Goran", "1234", 0); // pravimo
															// administratora i
															// unosimo ga u
															// listu
		b1.listaKorisnika.add(admin);
		boolean repeat = true;

		admin.setAdmin(true);
		b1 = ucitavanjeStanja(b1); // ucitavamo stanje iz file-a("stanje.txt")
		b1.listaKorisnika.addAll(popunjavaListu(b1)); // ucitavamo listu
														// korisnika iz
														// file-a("datoteka.txt")
		boolean spin = true; // omogucava da se petlja "vrti" dok administrator
								// ne ugasi bankomat
		while (spin) {
			b1 = logovanje(b1); // metod logovanje ce biti objasnjen komentarima
								// u samom metodu
		}
	}

	public static Bankomat deductBils(int amount, Bankomat b)
			throws IOException {
		// metod koji provjerava da li je moguce izvrsiti transakciju i zatim
		// umanjuje stanje u bankomatu za one novcanice kojim je izvrsena
		// isplata
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
			} else {
				System.out
						.println("Nemoguca isplata, molimo vas, idite u najblizu poslovnicu.\n");
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
		}
		return b;

	}

	public static ArrayList<Korisnik> popunjavaListu(Bankomat b)
			throws FileNotFoundException {
		// metod popunjava listu korisnika citajuci iz file-a
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
		// metod koji upisuje listu korisnika u file
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
		}

	}

	public static Bankomat ucitavanjeStanja(Bankomat b) throws IOException {
		// metod koji ucitava stanje novcanica u bankomatu iz file-a
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
		// metod koji upisuje stanje novcanica u bankomatu u file
		File file = new File("stanje.txt");
		FileWriter upis = new FileWriter(file);
		try {
			// upisujem kao String.valueOf iz razloga sto mi je bez toga
			// dodavalo i neki simbol pa je pravilo problem :D
			upis.write(String.valueOf(b.getDeset() + " "));
			upis.write(String.valueOf(b.getDvadeset() + " "));
			upis.write(String.valueOf(b.getPedeset() + " "));
			upis.write(String.valueOf(b.getSto()));
		} finally {
			upis.close();
		}
	}

	public static Bankomat dopunaNovcanica(Bankomat b) {
		// metod koji omogucava korisniku da izabere novcanice koje zeli da
		// dopuni a zatim i broj istih
		Scanner input = new Scanner(System.in);
		System.out
				.println("Izaberite novcanice koje zelite dopuniti i unesite kolicinu novcanica:\n1. 100KM\n2. 50KM\n3. 20KM\n4. 10KM\nZa povratak u glavni meni unesite bilo koji drugi broj.");
		int izborKorisnika = input.nextInt();
		if (izborKorisnika == 1) {
			System.out
					.println("Unesite broj novcanica od 100KM kojim dopunjavate bankomat.");
			b.addSto(input.nextInt());
		} else if (izborKorisnika == 2) {
			System.out
					.println("Unesite broj novcanica od 50KM kojim dopunjavate bankomat.");
			b.addPedeset(input.nextInt());
		} else if (izborKorisnika == 3) {
			System.out
					.println("Unesite broj novcanica od 20KM kojim dopunjavate bankomat.");
			b.addDvadeset(input.nextInt());
		} else if (izborKorisnika == 4) {
			System.out
					.println("Unesite broj novcanica od 10KM kojim dopunjavate bankomat.");
			b.addDeset(input.nextInt());
		}
		return b;
	}

	public static boolean provjeraPostojeciKorisnik(Bankomat b, String user) {
		// metod namjenjen da provjeri da li korisnik vec postoji u listi i
		// sprecava dupliranje USERNAME-a
		for (Korisnik e : b.listaKorisnika) {
			if (user.equals(e.getUserName())) {
				System.out
						.println("Korisnicko ime je zauzeto, pokusajte ponovo.\n");
				return true;
			}
		}
		return false;
	}

	public static Bankomat logovanje(Bankomat b) throws IOException,
			InputMismatchException {
		Scanner input = new Scanner(System.in);
		System.out.println("Unesite USERNAME i PASSWORD korisnika: ");
		String userName = input.next().trim(); // unosimo korisnicko ime i
												// pri tome uklanjamo prazan
												// prostor
		String userPass = input.next().trim(); // unosimo lozinku i pri tome
												// uklanjamo prazan prostor
		Korisnik korisnik = new Korisnik();
		boolean postojeciKorisnik = false;
		// for each petljom prolazimo kroz listu i poredimo uneseno korisnicko
		// ime i lozinku sa onim unesenim u listu
		for (Korisnik k : b.getListaKorisnika()) {
			if (k.getUserName().equals(userName)
					&& (k.getPassword().equals(userPass))) {
				postojeciKorisnik = true;
				// u skladu sa tim da li je ulogovani korisnik admin ili ne
				// ispisujemo mu "poruku dobrodoslice
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
		// u slucaju da nije pronadjen korisnik, obavestavamo o mogucim
		// razlozima i prekidamo dalji rad metoda(petlja ga pokrece iz pocetka i
		// pruza priliku da se ponovo ulogujemo)
		if (!postojeciKorisnik) {
			System.out
					.println("Pogresan USERNAME, PASSWORD ili nepostojeci korisnik!\n");
			return b;

		}
		boolean repeat = true;
		// while petlja omogucava da korisnik ili admin izvrsi vise radnji bez
		// da se svaki put ponovo loguje
		while (repeat) {
			// rastavio sam "if-ove" po tome da li je ulogovani korisnik admin
			// ili ne, prvi dio se odnosi na admina od linije 248 do 334
			if (korisnik.isAdmin()) {
				System.out
						.println("Izaberite zeljenu operaciju:\n1. Pregled stanja na bankomatu \n2. Dodavanje/brisanje novog korisnika \n3. Dopuna novcanica\n4. Logout\n5. Lista korisnika\n6. Gasenje bankomata\n Unesite svoj izbor:\n");
				int korisnikIzbor = input.nextInt();
				// ispisujemo stanje novcanica i ukupan iznos sredstava u
				// bankomatu
				if (korisnikIzbor == 1) {
					System.out
							.println("Broj novcanica od 10KM je: "
									+ b.getDeset()
									+ "\nBroj novcanica od 20KM je: "
									+ b.getDvadeset()
									+ "\nBroj novcanica od 50KM je: "
									+ b.getPedeset()
									+ "\nBroj novcanica od 100KM je: "
									+ b.getSto()
									+ "\nUkupan iznos na bankomatu je: "
									+ ((b.getSto() * 100)
											+ (b.getPedeset() * 50)
											+ (b.getDvadeset() * 20) + (b
											.getDeset() * 10)) + " KM");
					System.out.println("Unesite bilo koji broj da nastavite");
					input.next();
					System.out.println();
				}
				// omogucavamo adminu da pristupi "podmeniju" gdje bira da li
				// zeli ukloniti ili dodati novog korisnika
				if (korisnikIzbor == 2) {
					System.out
							.println("1. Dodavanje novog korisnika \n2. Brisanje postojeceg korisnika\nZa povratak u glavni meni unesite bilo koji drugi broj.");
					int adminIzbor = input.nextInt();
					// opcija podmenija dodaje novog korisnika
					if (adminIzbor == 1) {
						System.out
								.println("Unesite USERNAME novog korisnika: ");
						Korisnik k2 = new Korisnik();
						String userNameNoviKorisnik = "";
						boolean goodUsername = false;
						while (!goodUsername) {
							String newUserName = input.next();
							if (!provjeraPostojeciKorisnik(b, newUserName)) {
								userNameNoviKorisnik = newUserName;
								goodUsername=true;
							} else {
								System.out
										.println("USERNAME je zauzet pokusajte dodjeliti drugi USERNAME: ");
							}
						}
						String password = "";
						boolean goodPassword = false;
						System.out
								.println("Unesite PASSWORD(cetverocifreni broj) novog korisnika: ");
						while (!goodPassword) {
							String pass = input.next();
							if (passwordBroj(pass) && pass.length() <= 4) {
								int num = Integer.parseInt(pass);
								password = String.format("%04d", num);
								goodPassword = true;

							} else {
								System.out
										.println("Napavili ste gresku pri kreiranju PASSWORDA pokusajte ponovo:");
							}
						}
						System.out
								.println("Unesite stanje na racunu korisnika: ");
						int balance = input.nextInt();
						k2.setUserName(userNameNoviKorisnik);
						k2.setPassword(password);
						k2.setBalance(balance);
						b.listaKorisnika.add(k2);
						System.out
								.println("Uspjesno ste dodali korisnika \""
										+ k2.getUserName()
										+ "\", promjene ce biti trajno sacuvane kada se izlogujete.\n");

					}
					// opcija podmenija za brisanje postojeceg korisnika
					if (adminIzbor == 2) {
						System.out
								.println("Unesite USERNAME korisnika kog zelite brisati: \n");
						String imeZaBrisanje = input.next();
						boolean obrisao = false;
						// for petljom pronalazimo korisnika i brisemo ga ako
						// postoji
						for (int i = 0; i < b.listaKorisnika.size(); i++) {
							if (imeZaBrisanje.equals(b.listaKorisnika.get(i)
									.getUserName())) {
								obrisao = true;
								System.out
										.println("Da li ste sigurni da zelite obrisati korisnika "
												+ b.listaKorisnika.get(i)
														.getUserName()
												+ "?\nDa potvrdite unesite svoj PASSWORD, za povratak u prijasnji meni unesite bilo koji drugi broj.\n");
								if (input.nextInt() == 1234) {

									System.out
											.println("Uspjesno ste obrisali korisnika "
													+ b.listaKorisnika.get(i)
															.getUserName()
													+ ", promjene ce biti trajno sacuvane kada se izlogujete.\n");
									b.getListaKorisnika().remove(
											b.listaKorisnika.get(i));
								} else {
									System.out.println("Brisanje otkazano.");
								}
							}
						}
						// u slucaju da korisnik ne postoji obavestavamo admin-a
						if (!obrisao) {
							System.out.println("Nepostojeci korisnik!\n");
						}
					}
					// pozivamo metod za dopunjavanje novcanica
				} else if (korisnikIzbor == 3) {
					b = dopunaNovcanica(b);
					// prekidamo while petlju i omogucavamo da se main metod
					// nastavi
				} else if (korisnikIzbor == 4) {
					System.out.println("Logout...\n");
					repeat = false;
				} else if (korisnikIzbor == 5) {
					ispisKorisnika(b);
					System.out.println("Unesite bilo koji broj da nastavite:");
					input.next();
				}
				// zatvaramo program ali prije toga vrsimo upis stanja i
				// korisnika u pripadajuce file-ove
				else if (korisnikIzbor == 6) {
					System.out
							.println("Da li ste sigurni da zelite iskljuciti bankomat? Unesite svoj PASSWORD da potvrdite ili bilo koji drugi broj da se vratite u glavni meni.\n");
					if (input.nextInt() == 1234) {
						upisStanjaUFile(b);
						upisKorisnikaUFile(b);
						System.out.println("Bankomat uspjesno iskljucen!");
						System.exit(1);
					} else {
						System.out
								.println("Iskljucivanje bankomata ponisteno, vracamo se u glavni meni\n");
					}

				}
				else{
					System.out.println("Unjeli ste nepostojecu komandu, pokusajte ponovo: \n");
				}
				// dio kojim obicni korisnik obavlja zeljene radnje od linije
				// 336 do
			} else if (!korisnik.isAdmin()) {

				System.out
						.println("Izaberite zeljenu operaciju:\n1. Pregled stanja na racunu \n2. Podizanje novca \n3. Logout\nUnesite svoj izbor:\n");
				int korisnikIzbor = input.nextInt();
				// provjera stanja racuna
				if (korisnikIzbor == 1) {
					System.out.println("Stanje na racunu je: "
							+ korisnik.getBalance() + "\n");
					// podizannje zeljenog iznosa ukoliko je moguce(obavestenje
					// o problemu u koliko nije moguce)
				} else if (korisnikIzbor == 2) {
					System.out.println("Unesite iznos koji zelite podici\n");
					int iznos = input.nextInt();
					if ((b.getBalance() - iznos >= 0)
							&& (korisnik.getBalance() - iznos >= 0)) {
						korisnik.withdraw(iznos);
						b.setBalance(b.getBalance() - iznos);
						b = deductBils(iznos, b);
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
					// prekid while petlje da bi program nastavio izvrsenje
				} else if (korisnikIzbor == 3) {
					System.out
							.println("Hvala vam sto koristite nase usluge, dodjite nam opet.\n");
					repeat = false;

				}
				else{
					System.out.println("Unjeli ste broj kome nije dodjeljena funkcija, pokusajte ponovo:\n");
				}

			}
		}
		upisStanjaUFile(b);
		upisKorisnikaUFile(b);
		return b;

	}

	// ispis liste korisnika u konzolu
	public static void ispisKorisnika(Bankomat b) {
		System.out.println("User\t Pass\tBalance");
		for (int i = 1; i < b.listaKorisnika.size(); i++) {
			System.out.println(b.listaKorisnika.get(i).getUserName() + "\t "
					+ b.listaKorisnika.get(i).getPassword() + "\t"
					+ b.listaKorisnika.get(i).getBalance() + "\tKM");
		}
		System.out.println();
	}

	// metod koji provjerava da li je uneseni password broj
	public static boolean passwordBroj(String pass) {
		for (int i = 0; i < pass.length(); i++) {
			if (!Character.isDigit(pass.charAt(i))) {
				return false;
			}

		}
		return true;
	}
}
