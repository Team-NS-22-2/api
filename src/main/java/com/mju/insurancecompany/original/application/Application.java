package com.mju.insurancecompany.original.application;

import insuranceCompany.application.global.exception.*;
import insuranceCompany.application.global.utility.MyBufferedReader;
import insuranceCompany.application.viewlogic.LoginViewLogic;

import java.io.InputStreamReader;
import java.util.InputMismatchException;

import static insuranceCompany.application.global.constant.CommonConstants.ZERO;
import static insuranceCompany.application.global.constant.LoginViewLogicConstants.MENU_ELEMENTS_LOGIN_VIEW_LOGIC;

public class Application {

    private LoginViewLogic loginViewLogic;

    public Application() {
        loginViewLogic = new LoginViewLogic();
    }

    public void run() {
        MyBufferedReader br = new MyBufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                while (true){
                    String query = loginViewLogic.showMenu();
                    String command = String.valueOf(br.verifyMenu(query, MENU_ELEMENTS_LOGIN_VIEW_LOGIC.length));
                    if(command.equals(ZERO)) throw new InputInvalidMenuException();
                    loginViewLogic.work(command);
                }
            }
            catch (InputException e) {
                System.out.println(e.getMessage());
            }
            catch (ArrayIndexOutOfBoundsException | InputMismatchException |
                    MyIllegalArgumentException | NullPointerException e) {
                System.out.println("정확한 값을 입력해주세요.");
            }
            catch (MyCloseSequence | MyIOException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
    }
}
