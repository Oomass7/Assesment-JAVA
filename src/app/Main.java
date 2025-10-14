package app;

import Domain.UserDomain;
import view.AuthView;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        AuthView authView = new AuthView();
        MainView mainView = new MainView();

        while (true) {
            UserDomain user = authView.show();
            if (user == null) {
                break;
            }
            mainView.show(user); 
        }
    }
}
