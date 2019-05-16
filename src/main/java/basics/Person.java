package basics;

import java.io.Serializable;
import java.util.ArrayList;

public class Person extends Artist {

    private String birthDate;
    private String deathDate;
    private String gender;


    public Person () {
        super();
    }
    public Person(String birthDate, String deathDate, String gender,
            String id, String name, String country, String cities,
            ArrayList<String> aliases, ArrayList<String> tags, String type) {
        
        super(id, name, country, cities, aliases, tags, type);
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person [birthDate=" + birthDate + ", deathDate=" + deathDate + ", gender=" + gender + ", "
                + super.toString() + "]\n";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

}
