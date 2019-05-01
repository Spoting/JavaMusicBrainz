/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import basics.*;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class APIWrapper {

    public static String artistURL = "http://musicbrainz.org/ws/2/artist/";
    public static String releaseURL = "http://musicbrainz.org/ws/2/release/";
    public static String and = "%20AND%20";
    public static String endURL = "&fmt=json";
    public static String query = "?query=";
    public static String inc = "?inc=";
//FOR VARIOUS ARTISTS URL == http://musicbrainz.org/ws/2/release/3f89e2e2-9489-4cfa-a5e2-1e081b144cb6?inc=artist-credits+recordings&fmt=json
    //Βρες Releases με βάση το Type (Album, Compilation)
    public static ArrayList<Release> getReleasesByType(String relTitleIn, String arg) throws ParseException, IOException {
        ArrayList<Release> relArr = new ArrayList();
        String url;
        if (!arg.equals("")) { //χτίσε το url
            url = releaseURL + query + "title:" + relTitleIn + and + "type:" + arg + endURL;
        } else {
            url = releaseURL + query + "title:" + relTitleIn + endURL;
        }
        JSONObject response = getResponse(url); // Get JSON response
        JSONArray jsonReleases = (JSONArray) response.get("releases"); // Get JSONArray releases
        if (jsonReleases.isEmpty()) {
            System.out.println("Search returned zero results");
            return null;
        }
        //για κάθε jsonrelease δημιούργησε Release αντικείμενα και βάλτα σε array
        for (int i = 0; i < jsonReleases.size(); i++) {
            JSONObject release = (JSONObject) jsonReleases.get(i);
            Release a = makeRelease(release);
            if (a != null) {
                relArr.add(a);
            }
        }
        //γύρνα τον πίνακα με τα releases
        return relArr;
    }

    //Παρομοίως με getReleasesByType (σειρα 27)
    public static ArrayList<Release> getReleasesByStatus(String relTitleIn, String arg) throws ParseException, IOException {
        ArrayList<Release> relArr = new ArrayList();
        String url;
        if (!arg.equals("")) {
            url = releaseURL + query + "title:" + relTitleIn + and + "status:" + arg + endURL;
        } else {
            url = releaseURL + query + "title:" + relTitleIn + endURL;
        }
        JSONObject response = getResponse(url);
        JSONArray jsonReleases = (JSONArray) response.get("releases");
        if (jsonReleases.isEmpty()) {
            System.out.println("Search returned zero results");
            return null;
        }
        for (int i = 0; i < jsonReleases.size(); i++) {
            JSONObject release = (JSONObject) jsonReleases.get(i);
            Release a = makeRelease(release);
            if (a != null) {
                relArr.add(a);
            }
        }
        return relArr;
    }

    //Βρες Artists με βάση το country
    public static ArrayList<Artist> getArtistsFromCountry(String artNameIn, String arg) throws IOException, ParseException {
        ArrayList<Artist> artArr = new ArrayList();
        String url;
        if (!arg.equals("")) { //χτίσε το url
            url = artistURL + query + "artist:" + artNameIn + and + "country:" + arg + endURL;
        } else {
            url = artistURL + query + "artist:" + artNameIn + endURL;
        }
        JSONObject response = getResponse(url); //Get JSON Response
        JSONArray jsonArtists = (JSONArray) response.get("artists"); // Get JSONArray artists
        if (jsonArtists.isEmpty()) {
            System.out.println("Search returned zero results");
            return null;
        }
        //για κάθε jsonartist δημιούργησε Artist αντικείμενα και βάλτα σε array
        for (int i = 0; i < jsonArtists.size(); i++) {
            JSONObject art = (JSONObject) jsonArtists.get(i);
            Artist a = makeArtist(art);
            if (a != null) {
                artArr.add(a);
            }
        }
        //γύρνα τον πίνακα με τα Releases
        return artArr;
    }

    //Παρομοίως με getArtistsFromCountry(γραμμη 79)
    public static ArrayList<Artist> getArtistsFromTags(String artNameIn, String arg) throws ParseException, IOException {
        ArrayList<Artist> artArr = new ArrayList();
        String url;
        if (!arg.equals("")) {
            url = artistURL + query + "artist:" + artNameIn + and + "tags:" + arg + endURL;
        } else {
            url = artistURL + query + "artist:" + artNameIn + endURL;
        }
        JSONObject response = getResponse(url);
        JSONArray jsonArtists = (JSONArray) response.get("artists");
        if (jsonArtists.isEmpty()) {
            System.out.println("Search returned zero results");
            return null;
        }
        for (int i = 0; i < jsonArtists.size(); i++) {
            JSONObject art = (JSONObject) jsonArtists.get(i);
            Artist a = makeArtist(art);
            if (a != null) {
                artArr.add(a);
            }
        }

        return artArr;
    }

    //Χρησιμοποιείται για να παίρνουμε στοιχεία για τους Members ενώς Group.
    //Παίρνει ως όρισμα ένα Artist->Group και του setάρει το Members με Artist->Persons
    public static void getMembersOfGroup(Group group) throws ParseException, IOException, NullPointerException {
        ArrayList<Person> groupMembers = new ArrayList();

        if (group.getMembers() == null) {
            //Χτίζουμε το url βάζωντας το ?inc=artist-rels για να πάρουμε τα artist-relations του group
            String url = artistURL + group.getId() + inc + "artist-rels+aliases+tags" + endURL;

            JSONObject response = getResponse(url);
            JSONArray relations = (JSONArray) response.get("relations"); // Get JSONArray relations

            for (int i = 0; i < relations.size(); i++) { //για κάθε relation          
                JSONObject relation = (JSONObject) relations.get(i);
                JSONObject art = (JSONObject) relation.get("artist"); // Get JSON artist

                Person memb = new Person();             //χτίσε Person με
                memb.setId((String) art.get("id"));                 //id
                memb.setName((String) art.get("name"));             //name
                memb.setType((String) relation.get("type"));        //type
                memb.setBirthDate((String) relation.get("begin"));  //begin
                memb.setDeathDate((String) relation.get("end"));    //end

                if (memb != null && memb.getType().equals("member of band")) {
                    groupMembers.add(memb); // βάλε το person στον πίνακα
                }
            }
            //setαρε τον πίνακα στο Group
            group.setMembers(groupMembers);
        }
    }

    //Χρησιμοποιείται για να παίρνουμε πληροφορίες για τον Artist ενός Album πέρα απο id & name
    public static Artist fillArtistAlbum(Artist art) throws ParseException, IOException {
        Artist tmp = null;
        String url = artistURL + art.getId() + "?" + endURL;
        JSONObject response = getResponse(url);
        tmp = makeArtist(response);
        return tmp;
    }

        //Βασική κλάση που χρησιμοποιεί την JSOUP βιλβιοθήκη για να παίρνουμε response απο το MusicBrainz δίνοντας το κατάλληλο url
    public static JSONObject getResponse(String url) throws ParseException, IOException, org.jsoup.HttpStatusException {

        JSONObject response = null;
        try {
            //Χρήση JSOUP
            Document doc = Jsoup.connect(url).userAgent("Mozilla").ignoreContentType(true).timeout(1000).get();
            String responseText = doc.text();
            JSONParser parser = new JSONParser();
            //Το response σε μορφή JSONObject (απο την βιβλιοθήκη JSONSimple)
            response = (JSONObject) parser.parse(responseText);
            System.out.println(url);
        } catch (org.jsoup.HttpStatusException e) {     
            e.printStackTrace();
        }

        //Επιστρέφει το JSON response 
        return response;

    }
    
    //Με βάση ένα JSONObject (response απο το MusicBrainz) χτίζουμε ένα Release->Album ή Release->Compilation
    public static Release makeRelease(JSONObject rel) throws ParseException, IOException {
        Release release = null;
    //Release
        String id = (String) rel.get("id");             //id
        String title = (String) rel.get("title");       //title
        String status = (String) rel.get("status");     //status
        String releaseDate = (String) rel.get("date");  //date

        //language 
        String language = null;
        if (rel.get("text-representation") != null) {
            JSONObject json_language = (JSONObject) rel.get("text-representation");
            language = (String) json_language.get("language");
        }
        //format & tracks
        String format = null;
        long trackCount = 0;
        if (rel.get("media") != null) {
            JSONArray json_media = (JSONArray) rel.get("media");
            JSONObject json_format = (JSONObject) json_media.get(0);
            format = (String) json_format.get("format");
            trackCount = (long) rel.get("track-count");
        }

        String type = "";
        JSONObject relgroup = (JSONObject) rel.get("release-group");
        //Έλενχος στο release-group αν υπάρχει το primary-type 
        if (relgroup.get("primary-type") == null) {
            System.out.println("No Primary Type for Release");
        } else {
            type = (String) relgroup.get("primary-type");
        }
        //Παίρνουμε και τα secondary types για να δούμε ποια είναι Compilation
        JSONArray sec_types = (JSONArray) relgroup.get("secondary-types");

        if (type.equals("Album")) { //Αν το primary-type είναι Album

            //Πάρε το artist-credit, και δημιούργησε ένα Artist με id και name
            JSONArray artCredits = (JSONArray) rel.get("artist-credit");
            JSONObject artCredit = (JSONObject) artCredits.get(0);
            JSONObject arti = (JSONObject) artCredit.get("artist");
            Artist art = new Artist();
            art.setId((String) arti.get("id"));
            art.setName((String) arti.get("name"));
            
            //Αν το secondary-type είναι διάφορο του Compilation 
            if (sec_types == null || !(sec_types.get(0).toString().equals("Compilation"))) {
                //τότε φτιάξε Album
                release = new Album(art, id, title, status, language, releaseDate, format, (int) trackCount, type);
            //Αλλιως αν είναι ίσο του Compilation     
            } else if (sec_types.get(0).toString().equals("Compilation")) {
                ArrayList<Artist> artArr = new ArrayList();
                artArr.add(art);
                type = "Compilation";
                //τότε φτιάξε Compilation
                release = new Compilation(artArr, id, title, status, language, releaseDate, format, (int) trackCount, type);

            }
            //Γύρνα το πλέον φτιαγμένο Release->Album,Compilation
            return release;
        }
        //Αν δεν είναι Album το primary-type γύρνα null
        return null;

    }
    //Με βάση ένα JSONObject (response απο το MusicBrainz) χτίζουμε ένα Artist->Person ή Artist->Group
    public static Artist makeArtist(JSONObject art) throws ParseException, IOException, NullPointerException {
        Artist arti = null;

        ArrayList<String> aliases = new ArrayList();
        ArrayList<String> tags = new ArrayList();
    //Artist
        try {

            String type = (String) art.get("type");         //type
            String id = (String) art.get("id");             //id
            String name = (String) art.get("name");         //name
            String country = (String) art.get("country");   //country
            //Cities
            String cities = null;
            JSONObject area = (JSONObject) art.get("begin-area");
            if (!(area == null)) {
                cities = (String) area.get("name");
            }
            //Aliases
            JSONArray jsonAliases = (JSONArray) art.get("aliases");
            if (!(jsonAliases == null)) {
                aliases = new ArrayList(jsonAliases.size());
                for (int a = 0; a < jsonAliases.size(); a++) {

                    JSONObject alias = (JSONObject) jsonAliases.get(a);
                    aliases.add((String) alias.get("name"));
                }
            }
            //Tags
            JSONArray jsonTags = (JSONArray) art.get("tags");
            if (!(jsonTags == null)) {
                tags = new ArrayList(jsonTags.size());
                for (int a = 0; a < jsonTags.size(); a++) {
                    JSONObject tag = (JSONObject) jsonTags.get(a);
                    tags.add((String) tag.get("name"));
                }
            }
            //Begin + End Date
            String begin;
            String end;
            JSONObject life = (JSONObject) art.get("life-span");
            if (!(life == null)) { // για τον Person birth/deathDate
                begin = (String) life.get("begin");
                end = (String) life.get("end");
            } else {                //για το Group begin/endDate
                begin = (String) art.get("begin");
                end = (String) art.get("end");
            }

            //Αν το type είναι person ή member ενός Group 
            if (type.equals("Person") || type.equals("member of band")) {
                // τότε φτιάξε Person
                arti = new Person(begin, end,
                        (String) art.get("gender"), id, name, country, cities, aliases, tags, type);
            //Αλλιώς αν το type είναι Group;
            } else if (type.equals("Group")) {
                //τότε φτιάξε Group 
                arti = new Group(begin, end, null,
                        id, name, country, cities, aliases, tags, type);
                //και γέμισε με Members το Group
                getMembersOfGroup((Group) arti);
            
            } else {
                return null;
            }
            //return arti;
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
        return arti;
    }
}
