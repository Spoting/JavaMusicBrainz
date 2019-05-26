package basics;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity  
@DiscriminatorValue("Person") 
public class Person extends Artist {

    @Column(name="gender")
    private String gender;

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Person(String id, String name, String country, ArrayList<String> aliases, ArrayList<String> tags,
			String type, String cities, Date birthDate, Date deathDate, String gender) {
		super(id, name, country, aliases, tags, type, cities, birthDate, deathDate);
		this.gender = gender;
		// TODO Auto-generated constructor stub
	}


	public Person(String id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	@Override
	public String toString() {
		return "Person [gender=" + gender + ", Artist=" + super.toString() + "]";
	}
	
    
}
