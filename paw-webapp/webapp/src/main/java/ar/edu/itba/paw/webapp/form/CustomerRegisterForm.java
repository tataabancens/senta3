package ar.edu.itba.paw.webapp.form;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRegisterForm {

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String mail;

    //this doesnt go
//    @UsernameConstraint
//    @Size(min = 1, max = 50)
//    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
//    private String username;

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String customerName;

    @Size(min = 9, max = 13)
    private String phone;

    private Long userId;

    //this doesnt go
//    @PasswordLengthConstraint
//    @PasswordConstraint
//    private PasswordPair psPair;

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public PasswordPair getPsPair() {
//        return psPair;
//    }
//
//    public void setPsPair(PasswordPair psPair) {
//        this.psPair = psPair;
//    }
}
