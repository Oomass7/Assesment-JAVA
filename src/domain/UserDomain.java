package domain;

public class UserDomain {
    private Long idUser;
    private String nameUser;
    private String email;
    private String passwordUser;
    private String rol;

    public UserDomain() {}

    public UserDomain(Long idUser, String nameUser, String email, String passwordUser, String rol) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.email = email;
        this.passwordUser = passwordUser;
        this.rol = rol;
    }

    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    public String getNameUser() { return nameUser; }
    public void setNameUser(String nameUser) { this.nameUser = nameUser; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordUser() { return passwordUser; }
    public void setPasswordUser(String passwordUser) { this.passwordUser = passwordUser; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
