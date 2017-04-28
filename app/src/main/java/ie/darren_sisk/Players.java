package ie.darren_sisk.fantasyfootballdraft;

/**
 * Created by Darren_Sisk on 14/02/2017.
 */
public class Players {

    private String position, firstname, lastname, id;
    private String rank;

    public Players(String firstname, String lastname){

        this.setFirstname(firstname);
        this.setLastname(lastname);


    }

    public Players(String firstname, String lastname, String id, String rank){

        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setId(id);
        this.setRank(rank);


    }

    public Players(String firstname, String lastname, String id){

        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setId(id);


    }

    public String getFirstname(){
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId(){
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRank() {return rank;}

    public void setRank(String rank) {
        this.rank = rank;
    }
}
