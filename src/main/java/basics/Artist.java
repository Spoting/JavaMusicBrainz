package basics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="artist")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="artype",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="unknown")  
public class Artist implements Serializable {
    //Artist->Person,Group
	@Id
	@Column(name = "arid", unique = true, nullable = false)
    private String id;
	@Column(name = "arname")
    private String name;
	
    private String country;
    @ElementCollection(targetClass=String.class)
    private List<String> aliases;
    @ElementCollection(targetClass=String.class)
    private List<String> tags;

    private String type;

    private String cities;
	
    private Date birthDate;
	
    private Date deathDate;
	
	public Artist() {}

    public Artist(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Artist(String id, String name, String country, List<String> aliases, List<String> tags,
			String type, String cities, Date birthDate, Date deathDate) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.aliases = aliases;
		this.tags = tags;
		this.type = type;
		this.cities = cities;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
	}

	public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }
    
    
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	@Override
	public String toString() {
		return "Artist [id=" + id + ", name=" + name + ", country=" + country + ", aliases=" + aliases + ", tags="
				+ tags + ", type=" + type + ", cities=" + cities + ", birthDate=" + birthDate + ", deathDate="
				+ deathDate + "]";
	}

	
}
