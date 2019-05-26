package basics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity  
@DiscriminatorValue("Group") 
public class Group extends Artist {
	
    @OneToMany(targetEntity = Person.class, cascade=CascadeType.ALL)
    @JoinColumn(name="gid")
    private List<Person> members;
    
	@Override
	public String toString() {
		return "Group [members=" + members.size() + ", toString()=" + super.toString() + "]";
	}

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Group(String id, String name, String country, ArrayList<String> aliases, ArrayList<String> tags, String type,
			String cities, Date birthDate, Date deathDate, ArrayList<Person> members) {
		super(id, name, country, aliases, tags, type, cities, birthDate, deathDate);
		this.members = members;
		// TODO Auto-generated constructor stub
	}

	public Group(String id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	public List<Person> getMembers() {
		return members;
	}

	public void setMembers(List<Person> members) {
		this.members = members;
	}
    


}
