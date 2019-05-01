package tests;



import basics.*;
import java.util.ArrayList;

public class DemoBasics {

	/** Abstract class Artist->Person,Group
	 *  Abstract class Release->Album,Compilation 
	**/
	public static void main(String[] args) {
            ArrayList<String> aliases = new ArrayList();
            aliases.add("alias 1");
            aliases.add("alias 2");
            
            ArrayList<String> tags = new ArrayList();
            tags.add("tag 1");
            tags.add("tag 2");
            
            
            
            //PERSON
            Artist person = new Person("1960", "2010", "male", "personID", 
                    "Fred", "US", "California", aliases, tags, "Person");
            System.out.println(person.toString());
            //GROUP
            ArrayList<Person> members = new ArrayList();
            members.add((Person)person);
            Artist group = new Group("2000", "2009",  members, "groupID", 
                    "GroupFred", "US", "California", aliases, tags, "Group");
            System.out.println(group.toString());
            //ALBUM
            Release album = new Album(person, "albumID", "Fred's Album", 
                    "official", "EN", "2001", "CD", 10, "Album");
            System.out.println(album.toString());
            //COMPILATION
            ArrayList<Artist> artists = new ArrayList();
            artists.add(person);
            artists.add(group);
            Release compilation = new Compilation(artists, "comID", "Mixtape", "unofficial",
                    "EN", "2005", "LP", 5, "Compilation");
            System.out.println(compilation.toString());
            
	}
}
