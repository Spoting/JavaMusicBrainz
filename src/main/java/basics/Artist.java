package basics;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONArray;


public class Artist {
    //Artist->Person,Group

    private String id;
    private String name;
    private String country;
    private ArrayList<String> aliases;
    private ArrayList<String> tags;
    private String type;
    private String cities;

    public Artist () {}
    public Artist (String id, String name) {
        this.id=id;
        this.name=name;
    }
    public Artist(String id, String name, String country, String cities, ArrayList<String> aliases, ArrayList<String> tags, String type) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.aliases = aliases;
        this.tags = tags;
        this.type = type;
        this.cities = cities;
       // System.out.println("ARTIST CONSTRUCTOR");
    }

    @Override
    public String toString() {
        return "Artist{" + "id=" + id + ", name=" + name + ", country=" + country  + ", cities="+ cities +", aliases=" + aliases + ", tags=" + tags + ", type=" + type + '}';
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

    public ArrayList<String> getAliases() {
        return aliases;
    }

    public void setAliases(ArrayList<String> aliases) {
        this.aliases = aliases;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
