package devsec.app.easykitchen.ui.main.viewmodel;

public class LoginViewModel {
    private String email;
    private String password;

    public LoginViewModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

