package com.company.view;

import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.service.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.security.InvalidAlgorithmParameterException;
import java.util.Scanner;

public class IOMenu {

    private Scanner input;
    private PrintStream output;

    private int menuOption;
    private int subMenuOption;

    private CurrencyService currencyService = new CurrencyService();
    private ClientsManager clientsManager = new ClientsManager();
    private ExchangeOfficeService exchangeOfficeService = new ExchangeOfficeService();
    private ExchangeService exchangeService = new ExchangeService();

    private String authorName = "Cioboata Mihai Catalin";

/*    private enum Menu{
        MONEZI,
        CLIENT,
        CASA_SCHIMB_VALUTAR

        public enum ClientCommands{
            ADAUGA_CLIENT,
            SCHIMBA_NUME,
            SCHIMBA_PRENUME,
            RESETEAZA_BANI,
            AFISEAZA_BANI
        }

        public enum MoneziCommands{
            ADAUGA_MONEDA,
            STERGE_MONEDA,
            SCHIMBA_NUME_MONEDA,
            SCHIMBA_SIMBOL_MONEDA,
            SCHIMBA_TARA_MONEDA,
            AFISEAZA_MONEDA,
            AFISEAZA_TOATE_MONEZILE
        }

        public enum CasaSchimbCommands{
            ADAUGA_BANI,
            RESETEAZA_BANI,
            AFISEAZA_BANI
        }
    }
*/

    void processSubMenu() throws DuplicateCurrencyException, InvalidCurrencyNameException, DuplicateClientException {
        String nume;
        String nume_nou;
        String prenume;
        String nume_valuta;
        int amount;
        switch (menuOption){
            case 1:
                switch (subMenuOption){
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele monedei (Ex: Leu, Euro, Lira, Dolar american): ");
                        nume = input.next();
                        output.println("Introduceti simbolul monedei (Ex: RON, EUR, USD): ");
                        String simbol = input.next();
                        currencyService.addCurrency(nume, simbol);
                        break;
                    case 2:
                        output.println("Introduceti numele monedei: ");
                        nume = input.next();
                        currencyService.removeCurrency(nume);
                        break;
                    case 3:
                        output.println("Introduceti numele monedei pe care doriti sa o modificati: ");
                        nume = input.next();
                        output.println("Introduceti noul nume al monedei: ");
                        nume_nou = input.next();
                        currencyService.changeCurrencyName(nume, nume_nou);
                        break;
                    case 4:
                        output.println("Introduceti numele monedei pe care doriti sa o modificati: ");
                        nume = input.next();
                        output.println("Introduceti noul simbol al monedei: ");
                        nume_nou = input.next();
                        currencyService.changeCurrencySymbol(nume, nume_nou);
                        break;
                    case 5:
                        output.println("Introduceti numele monedei pe care doriti sa o modificati: ");
                        nume = input.next();
                        output.println("Introduceti noua denumire a tarii pentru moneda: ");
                        nume_nou = input.next();
                        currencyService.changeCurrencyCountry(nume, nume_nou);
                        break;
                    case 6:
                        output.println("Introduceti numele monedei pe care doriti sa o afisati: ");
                        nume = input.next();
                        currencyService.showCurrencyInfo(nume);
                        break;
                    case 7:
                        currencyService.showAllCurrenciesInfo();
                        break;
                }
                break;
            case 2:
                switch (subMenuOption){
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele de familie al clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti prenumele clientului: ");
                        prenume = input.next();
                        clientsManager.addClient(nume, prenume);
                        break;
                    case 2:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti numele valutei/monedei: ");
                        nume_valuta = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextInt();
                        clientsManager.addMoney(nume, nume_valuta, amount);
                        break;
                    case 3:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti numele valutei/monedei: ");
                        nume_valuta = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextInt();
                        clientsManager.increaseMoney(nume, nume_valuta, amount);
                        break;
                    case 4:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti numele valutei/monedei: ");
                        nume_valuta = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextInt();
                        clientsManager.decreaseMoney(nume, nume_valuta, amount);
                        break;
                    case 5:
                        output.println("Introduceti numele vechi al clientului: ");
                        nume = input.next();
                        output.println("Introduceti noul nume al clientului: ");
                        nume_nou = input.next();
                        clientsManager.changeClientFirstName(nume, nume_nou);
                        break;
                    case 6:
                        output.println("Introduceti numele vechi al clientului: ");
                        nume = input.next();
                        output.println("Introduceti noul prenume al clientului: ");
                        nume_nou = input.next();
                        clientsManager.changeClientLastName(nume, nume_nou);
                        break;
                    case 7:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        clientsManager.resetClientMoney(nume);
                        break;
                    case 8:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        clientsManager.showClientMoney(nume, true);
                        break;
                    case 9:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        output.println(clientsManager.getClientSmallestMoneyAmount(nume));
                        break;
                    case 10:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        output.println(clientsManager.getClientBiggestMoneyAmount(nume));
                        break;
                    case 11:
                        output.println("Introduceti numele clientului: ");
                        nume = input.next();
                        output.println(clientsManager.getClientInfoFormated(nume));
                        break;
                    case 12:
                        output.println(clientsManager.getAllClientsInfoFormated());
                }
                break;
            case 3:
                switch (subMenuOption){
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele monedei: ");
                        nume = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextInt();
                        exchangeOfficeService.addMoney(nume, amount);
                        break;
                    case 2:
                        exchangeOfficeService.resetOfficeMoney();
                        output.println("Banii au fost resetati!");
                        break;
                    case 3:
                        output.println(exchangeOfficeService.getOfficeMoney(true));
                        break;
                    case 4:
                        exchangeOfficeService.showSmallestMoneyAmount();
                        break;
                    case 5:
                        exchangeOfficeService.showBiggestMoneyAmount();
                        break;
                }
                break;
        }
    }

    IOMenu(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }

    public void showAppDesc() {
        output.println("---------- CASA DE SCHIMB VALUTAR --------------");
        output.println("---------- Realizat de: " + authorName);
        output.println();
    }

    public void showMenu() {
        output.println("----- Meniuri disponibile: -------");
        output.println("Alegeti numarul comenzii");
        output.println("1. MONEZI");
        output.println("2. CLIENT");
        output.println("3. CASA SCHIMB VALUTAR");
    }

    public void readMenuOption() throws InvalidAlgorithmParameterException {
        int val = input.nextInt();
        if (val >= 1 && val <= 3) {
            menuOption = val;
        }
        else throw new InvalidAlgorithmParameterException("Optiune invalida");
    }

    public void showSubMenu() {
        output.println("Alegeti numarul comenzii, sau \"0\" pentru a merge inapoi");
        output.println("----- Optiuni disponibile: -------");
        switch (menuOption) {
            case 1:
                output.println("1. Adauga moneda");
                output.println("2. Sterge moneda");
                output.println("3. Schimbare nume moneda");
                output.println("4. Schimbare simbol moneda");
                output.println("5. Schimbare tara moneda");
                output.println("6. Afiseaza moneda");
                output.println("7. Afieaza toate monedele");
                break;
            case 2:
                output.println("1. Adauga client");
                output.println("2. Adauga bani de valuta noua");
                output.println("3. Adauga bani");
                output.println("4. Scade bani");
                output.println("5. Schimba nume");
                output.println("6. Schimba prenume");
                output.println("7. Reseteaza bani");
                output.println("8. Afiseaza bani");
                output.println("9. Afiseaza valuta cu cea mai mica suma de bani");
                output.println("10. Afiseaza valuta cu cea mai mare suma de bani");
                output.println("11. Afiseaza informatii despre client");
                output.println("12. Afiseaza informatii despre toti clientii");
                break;
            case 3:
                output.println("1. Adauga bani");
                output.println("2. Reseteaza bani");
                output.println("3. Afiseaza bani");
                output.println("4. Afiseaza valuta cu cea mai mica suma de bani");
                output.println("5. Afiseaza valuta cu cea mai mare suma de bani");
                break;
        }
    }

    public void readSubMenuOption() throws InvalidAlgorithmParameterException, DuplicateCurrencyException, DuplicateClientException, InvalidCurrencyNameException {
        int val = input.nextInt();
        switch (menuOption) {
            case 1:
                if (val < 0 || val > 7) {
                    throw new InvalidAlgorithmParameterException("Optiune invalida!");
                }
                break;
            case 2:
                if (val < 0 || val > 12) {
                    throw new InvalidAlgorithmParameterException("Optiune invalida!");
                }
                break;
            case 3:
                if (val < 0 || val > 5) {
                    throw new InvalidAlgorithmParameterException("Optiune invalida!");
                }
                break;
        }
        subMenuOption = val;
        processSubMenu();
    }
}
