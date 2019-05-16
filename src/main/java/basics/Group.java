package basics;

import java.io.Serializable;
import java.util.ArrayList;

public class Group extends Artist {

    private String beginDate;
    private String endDate;
    private ArrayList<Person> members;

    public Group() {
        super();
    }

    public Group(String beginDate, String endDate, ArrayList<Person> members,
            String id, String name, String country, String cities,
            ArrayList<String> aliases, ArrayList<String> tags, String type) {

        super(id, name, country, cities, aliases, tags, type);
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group [beginDate=" + beginDate + ", endDate=" + endDate + ", " + super.toString() + ",\n [" + "members=" + members + " "
                + "]]\n";
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Person> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Person> members) {
        this.members = members;
    }

}
