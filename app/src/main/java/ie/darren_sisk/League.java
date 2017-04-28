package ie.darren_sisk.fantasyfootballdraft;

/**
 * Created by Darren_Sisk on 22/03/2017.
 */
public class League {

    private String position, firstname, lastname, id;

    public League(String firstname, String lastname) {

        this.setFirstname(firstname);
        this.setLastname(lastname);


    }

    public League(String firstname, String lastname, String id) {

        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setId(id);


    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}