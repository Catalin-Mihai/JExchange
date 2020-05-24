package com.company.view;

import com.company.exceptions.DuplicateClientException;
import com.company.exceptions.DuplicateCurrencyException;
import com.company.exceptions.InvalidCurrencyNameException;
import com.company.service.entity.ClientsManager;
import com.company.service.entity.CurrencyService;
import com.company.service.entity.LogService;
import com.company.service.entity.OfficeService;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.InvalidAlgorithmParameterException;
import java.util.Scanner;

import static com.company.service.entity.LogService.LogTypes.CURRENCY_DELETE_CURRENCY;

public class IOMenu {

    private final Scanner input;
    private final PrintStream output;
    private final CurrencyService currencyService = new CurrencyService();
    private final ClientsManager clientsManager = new ClientsManager();
    private final OfficeService officeService = new OfficeService();
    private final String authorName = "Cioboata Mihai Catalin";
    private int menuOption = 1;
    private int subMenuOption;

    IOMenu(InputStream input, PrintStream output) {
        this.input = new Scanner(input);
        this.output = output;
    }

    void processSubMenu() throws DuplicateCurrencyException, InvalidCurrencyNameException, DuplicateClientException, IOException {
        String nume;
        String nume_nou;
        String numeOffice;
        String prenume;
        String nume_valuta;
        String nume_valuta_2;
        String numeVechi;
        double amount;
        switch (menuOption) {
            case 1:
                switch (subMenuOption) {
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele monedei (Ex: Leu, Euro, Lira, Dolar american): ");
                        nume = input.next();
                        output.println("Introduceti simbolul monedei (Ex: RON, EUR, USD): ");
                        String simbol = input.next();
                        currencyService.addCurrency(nume, simbol);
                        LogService.getInstance().Log(LogService.LogTypes.CURRENCY_ADD_CURRENCY, nume);
                        break;
                    case 2:
                        output.println("Introduceti numele monedei: ");
                        nume = input.next();
                        currencyService.removeCurrency(nume);
                        LogService.getInstance().Log(CURRENCY_DELETE_CURRENCY, nume);
                        break;
                    case 3:
                        output.println("Introduceti numele monedei pe care doriti sa o modificati: ");
                        nume = input.next();
                        output.println("Introduceti noul nume al monedei: ");
                        nume_nou = input.next();
                        currencyService.changeCurrencyName(nume, nume_nou);
                        LogService.getInstance().Log(LogService.LogTypes.CURRENCY_CHANGE_CURRENCY_NAME, nume, nume_nou);
                        break;
                    case 4:
                        output.println("Introduceti numele monedei pe care doriti sa o modificati: ");
                        nume = input.next();
                        output.println("Introduceti noul simbol al monedei: ");
                        nume_nou = input.next();
                        currencyService.changeCurrencySymbol(nume, nume_nou);
                        LogService.getInstance().Log(LogService.LogTypes.CURRENCY_CHANGE_CURRENCY_SYMBOL, nume, nume_nou);
                        break;
                    case 5:
                        output.println("Introduceti numele monedei pe care doriti sa o modificati: ");
                        nume = input.next();
                        output.println("Introduceti noua denumire a tarii pentru moneda: ");
                        nume_nou = input.next();
                        currencyService.changeCurrencyCountry(nume, nume_nou);
                        LogService.getInstance().Log(LogService.LogTypes.CURRENCY_CHANGE_CURRENCY_COUNTRY, nume, nume_nou);
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
                switch (subMenuOption) {
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele de familie al clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti prenumele clientului: ");
                        prenume = input.next();
                        clientsManager.addClient(prenume, nume);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_ADD_CLIENT, prenume);
                        break;
                    case 2:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti numele valutei/monedei: ");
                        nume_valuta = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextDouble();
                        clientsManager.addMoney(nume, nume_valuta, amount);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_ADD_MONEY, nume, nume_valuta, String.valueOf(amount));
                        break;
                    case 3:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti numele valutei/monedei: ");
                        nume_valuta = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextDouble();
                        clientsManager.increaseMoney(nume, nume_valuta, amount);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_INCREASE_MONEY, nume, nume_valuta, String.valueOf(amount));
                        break;
                    case 4:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        output.println("Introduceti numele valutei/monedei: ");
                        nume_valuta = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextDouble();
                        clientsManager.decreaseMoney(nume, nume_valuta, amount);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_DECREASE_MONEY, nume, nume_valuta, String.valueOf(amount));
                        break;
                    case 5:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        output.println("Introduceti noul nume al clientului: ");
                        nume_nou = input.next();
                        numeVechi = clientsManager.getClientByFirstName(nume).getName();
                        clientsManager.changeClientLastName(nume, nume_nou);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_CHANGE_LASTNAME,
                                numeVechi, clientsManager.getClientByFirstName(nume).getName());
                        break;
                    case 6:
                        output.println("Introduceti prenumele vechi al clientului: ");
                        nume = input.next();
                        output.println("Introduceti noul prenume al clientului: ");
                        nume_nou = input.next();
                        numeVechi = clientsManager.getClientByFirstName(nume).getName();
                        clientsManager.changeClientFirstName(nume, nume_nou);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_CHANGE_FIRSTNAME,
                                numeVechi, clientsManager.getClientByFirstName(nume_nou).getName());
                        break;
                    case 7:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        clientsManager.resetClientAllMoney(nume);
                        LogService.getInstance().Log(LogService.LogTypes.CLIENT_RESET_ALL_MONEY, nume);
                        break;
                    case 8:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        clientsManager.showClientMoney(nume, true);
                        break;
                    case 9:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        output.println(clientsManager.getClientSmallestMoneyAmount(nume));
                        break;
                    case 10:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        output.println(clientsManager.getClientBiggestMoneyAmount(nume));
                        break;
                    case 11:
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        output.println(clientsManager.getClientInfoFormated(nume));
                        break;
                    case 12:
                        output.println(clientsManager.getAllClientsInfoFormated());
                }
                break;
            case 3:
                switch (subMenuOption) {
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        output.println("Introduceti adresa oficiului de schimb valutar: ");
                        nume = input.next();
                        officeService.addOffice(numeOffice, nume);
                        break;
                    case 2:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        output.println("Introduceti numele monedei: ");
                        nume = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextDouble();
                        officeService.addMoney(numeOffice, nume, amount);
                        LogService.getInstance().Log(LogService.LogTypes.OFFICE_ADD_MONEY, numeOffice, nume, String.valueOf(amount));
                        break;
                    case 3:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        output.println("Introduceti numele monedei: ");
                        nume = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextFloat();
                        officeService.increaseMoney(numeOffice, nume, amount);
                        LogService.getInstance().Log(LogService.LogTypes.OFFICE_INCREASE_MONEY, numeOffice, nume, String.valueOf(amount));
                        break;
                    case 4:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        output.println("Introduceti numele monedei: ");
                        nume = input.next();
                        output.println("Introduceti cantitatea de bani: ");
                        amount = input.nextFloat();
                        officeService.decreaseMoney(numeOffice, nume, amount);
                        LogService.getInstance().Log(LogService.LogTypes.OFFICE_DECREASE_MONEY, numeOffice, nume, String.valueOf(amount));
                        break;
                    case 5:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        officeService.resetOfficeAllMoney(numeOffice);
                        output.println("Banii au fost resetati!");
                        LogService.getInstance().Log(LogService.LogTypes.OFFICE_RESET_MONEY, numeOffice);
                        break;
                    case 6:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        output.println(officeService.getOfficeAllMoneyFormated(numeOffice, true));
                        break;
                    case 7:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        officeService.showSmallestMoneyAmount(numeOffice);
                        break;
                    case 8:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        officeService.showBiggestMoneyAmount(numeOffice);
                        break;
                }
                break;
            case 4:
                switch (subMenuOption) {
                    case 0:
                        output.println("Inapoi la meniul principal...");
                        break;
                    case 1:
                        output.println("Introduceti numele oficiului de schimb valutar: ");
                        numeOffice = input.next();
                        output.println("Introduceti prenumele clientului: ");
                        nume = input.next();
                        //output.println("\"" + nume + "\"");
                        System.out.println(clientsManager.getClientInfoFormated(nume));
                        output.println("Introduceti numele valutei pe care vreti sa o vindeti: ");
                        nume_valuta = input.next();
                        output.println("Introduceti numele valutei pe care vreti sa o cumparati: ");
                        nume_valuta_2 = input.next();
                        output.println("Introduceti cantitatea de bani pe care doriti sa o vindeti: ");
                        amount = input.nextFloat();
                        int exchangeID = officeService.exchangeMoney(numeOffice, nume, nume_valuta, nume_valuta_2, amount);
                        System.out.println("Tranzactie incheiata cu succes");
                        LogService.getInstance().Log(LogService.LogTypes.EXCHANGE_EXCHANGE_MONEY,
                                String.valueOf(exchangeID));
                        break;
                    case 2:
                        output.println("Curs valutar: ");
                        output.println(officeService.getAllExchangeRatesFormatted());
                        break;
                    case 3:
                        output.println("Introduceti numele valutei pe care clientul o vinde");
                        nume_valuta = input.next();
                        output.println("Introduceti numele valutei pe care clientul o cumpara");
                        nume_valuta_2 = input.next();
                        output.println("Introduceti rata de schimb dintre cele doua monede (Exemplu: 4.77 in cazul Euro->Leu)");
                        amount = input.nextFloat();
                        new OfficeService().addExchangeRate(nume_valuta, nume_valuta_2, amount);
                        LogService.getInstance().Log(LogService.LogTypes.EXCHANGE_ADD_EXCHANGE_RATE, nume_valuta, nume_valuta_2, String.valueOf(amount));
                        break;
                    case 4:
                        output.println("Istoric schimburi valutare: ");
                        output.println(new OfficeService().getAllExchangesFormatted());
                        break;
                }
                break;
        }
    }

    public void showAppDesc() {
        output.println("---------- CASA DE SCHIMB VALUTAR --------------");
        output.println("---------- Realizat de: " + authorName);
        output.println();
    }

    public void showMenu() {
        output.println("----- Meniuri disponibile: -------");
        output.println("Alegeti numarul comenzii");
        output.println("0. INCHIDE PROGRAM");
        output.println("1. MONEZI");
        output.println("2. CLIENTI");
        output.println("3. OFFICE");
        output.println("4. SCHIMB VALUTAR");
    }

    public void readMenuOption() throws InvalidAlgorithmParameterException {
        int val = input.nextInt();
        if (val >= 0 && val <= 4) {
            menuOption = val;
        } else throw new InvalidAlgorithmParameterException("Optiune invalida");
    }

    public void showSubMenu() {
        if (menuOption == 0) {
            return;
        }
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
                output.println("1. Adauga o noua casa de schimb valutar");
                output.println("2. Adauga bani de valuta noua");
                output.println("3. Adauga bani");
                output.println("4. Scade bani");
                output.println("5. Reseteaza bani");
                output.println("6. Afiseaza bani");
                output.println("7. Afiseaza valuta cu cea mai mica suma de bani");
                output.println("8. Afiseaza valuta cu cea mai mare suma de bani");
                break;
            case 4:

                output.println("1. Schimba bani");
                output.println("2. Curs valutar");
                output.println("3. Adauga un nou schimb valutar.");
                output.println("4. Afiseaza istoricul schimburilor valutare");
        }
    }

    public void readSubMenuOption() throws InvalidAlgorithmParameterException, DuplicateCurrencyException, DuplicateClientException, InvalidCurrencyNameException, IOException {
        if (menuOption == 0) {
            return;
        }
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
                if (val < 0 || val > 8) {
                    throw new InvalidAlgorithmParameterException("Optiune invalida!");
                }
                break;
            case 4:
                if (val < 0 || val > 4) {
                    throw new InvalidAlgorithmParameterException("Optiune invalida!");
                }
                break;
        }
        subMenuOption = val;
        processSubMenu();
    }

    public int getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(int menuOption) {
        this.menuOption = menuOption;
    }
}
