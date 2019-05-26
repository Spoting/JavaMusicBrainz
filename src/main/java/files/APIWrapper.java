/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import basics.Album;
import basics.Artist;
import basics.Compilation;
import basics.Group;
import basics.Person;
import basics.Release;

public class APIWrapper {

	public static String artistURL = "http://musicbrainz.org/ws/2/artist/";
	public static String releaseURL = "http://musicbrainz.org/ws/2/release/";
	public static String and = "%20AND%20";
	public static String endURL = "&fmt=json";
	public static String query = "?query=";
	public static String inc = "?inc=";
//FOR VARIOUS ARTISTS URL == http://musicbrainz.org/ws/2/release/3f89e2e2-9489-4cfa-a5e2-1e081b144cb6?inc=artist-credits+recordings&fmt=json
	// Βρες Releases με βάση το Type (Album, Compilation)

	public static ArrayList<Release> getReleasesByType(String relTitleIn, String arg)
			throws ParseException, IOException, java.text.ParseException {
		ArrayList<Release> relArr = new ArrayList();
		String url;
		if (!arg.equals("")) { // χτίσε το url
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
		// για κάθε jsonrelease δημιούργησε Release αντικείμενα και βάλτα σε array
		for (int i = 0; i < jsonReleases.size(); i++) {
			JSONObject release = (JSONObject) jsonReleases.get(i);
			Release a = makeRelease(release);
			if (a != null) {
				relArr.add(a);
			}
		}
		// γύρνα τον πίνακα με τα releases
		return relArr;
	}

	// Παρομοίως με getReleasesByType (σειρα 27)
	public static ArrayList<Release> getReleasesByStatus(String relTitleIn, String arg)
			throws ParseException, IOException {
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

	// Βρες Artists με βάση το country
	public static ArrayList<Artist> getArtistsFromCountry(String artNameIn, String arg)
			throws IOException, ParseException, NullPointerException, java.text.ParseException {
		ArrayList<Artist> artArr = new ArrayList();
		String url;
		if (!arg.equals("")) { // χτίσε το url
			url = artistURL + query + "artist:" + artNameIn + and + "country:" + arg + endURL;
		} else {
			url = artistURL + query + "artist:" + artNameIn + endURL;
		}
		JSONObject response = getResponse(url); // Get JSON Response
		JSONArray jsonArtists = (JSONArray) response.get("artists"); // Get JSONArray artists
		if (jsonArtists.isEmpty()) {
			System.out.println("Search returned zero results");
			return null;
		}
		// για κάθε jsonartist δημιούργησε Artist αντικείμενα και βάλτα σε array
		for (int i = 0; i < jsonArtists.size(); i++) {
			JSONObject art = (JSONObject) jsonArtists.get(i);
			Artist a = makeArtist(art);
			if (a != null) {
				artArr.add(a);
			}
		}
		// γύρνα τον πίνακα με τα Releases
		return artArr;
	}

	// Παρομοίως με getArtistsFromCountry(γραμμη 79)
	public static ArrayList<Artist> getArtistsFromTags(String artNameIn, String arg)
			throws ParseException, IOException, NullPointerException, java.text.ParseException {
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

	// Χρησιμοποιείται για να παίρνουμε στοιχεία για τους Members ενώς Group.
	// Παίρνει ως όρισμα ένα Artist->Group και του setάρει το Members με
	// Artist->Persons
	public static void getMembersOfGroup(Group group) throws ParseException, IOException, NullPointerException {
		ArrayList<Person> groupMembers = new ArrayList();

		if (group.getMembers() == null) {
			// Χτίζουμε το url βάζωντας το ?inc=artist-rels για να πάρουμε τα
			// artist-relations του group
			String url = artistURL + group.getId() + inc + "artist-rels+aliases+tags" + endURL;

			JSONObject response = getResponse(url);
			JSONArray relations = (JSONArray) response.get("relations"); // Get JSONArray relations

			for (int i = 0; i < relations.size(); i++) { // για κάθε relation
				JSONObject relation = (JSONObject) relations.get(i);
				JSONObject art = (JSONObject) relation.get("artist"); // Get JSON artist

				Person memb = new Person(); // χτίσε Person με
				memb.setId((String) art.get("id")); // id
				memb.setName((String) art.get("name")); // name
				memb.setType((String) relation.get("type")); // type
				memb.setBirthDate((Date) relation.get("begin")); // begin
				memb.setDeathDate((Date) relation.get("end")); // end

				if (memb != null && memb.getType().equals("member of band")) {
					groupMembers.add(memb); // βάλε το person στον πίνακα
				}
			}
			// setαρε τον πίνακα στο Group
			group.setMembers(groupMembers);
		}
	}

	// Χρησιμοποιείται για να παίρνουμε πληροφορίες για τον Artist ενός Album πέρα
	// απο id & name
	public static Artist fillArtistAlbum(Artist art)
			throws ParseException, IOException, NullPointerException, java.text.ParseException {
		Artist tmp = null;
		String url = artistURL + art.getId() + "?" + endURL;
		JSONObject response = getResponse(url);
		tmp = makeArtist(response);
		return tmp;
	}

	// Βασική κλάση που χρησιμοποιεί την JSOUP βιλβιοθήκη για να παίρνουμε response
	// απο το MusicBrainz δίνοντας το κατάλληλο url
	public static JSONObject getResponse(String url) throws ParseException, IOException, org.jsoup.HttpStatusException {

		JSONObject response = null;
		try {
			// Χρήση JSOUP
			Document doc = Jsoup.connect(url).userAgent("Mozilla").ignoreContentType(true).timeout(5000).get();
			String responseText = doc.text();
			JSONParser parser = new JSONParser();
			// Το response σε μορφή JSONObject (απο την βιβλιοθήκη JSONSimple)
			response = (JSONObject) parser.parse(responseText);
			System.out.println(url);
		} catch (org.jsoup.HttpStatusException e) {
			e.printStackTrace();
		}

		// Επιστρέφει το JSON response
		return response;

	}

	// Με βάση ένα JSONObject (response απο το MusicBrainz) χτίζουμε ένα
	// Release->Album ή Release->Compilation
	public static Release makeRelease(JSONObject rel) throws ParseException, IOException {
		Release release = null;
		// Release
		String id = (String) rel.get("id"); // id
		String title = (String) rel.get("title"); // title
		String status = (String) rel.get("status"); // status
		String releaseDate = (String) rel.get("date"); // date

		// language
		String language = null;
		if (rel.get("text-representation") != null) {
			JSONObject json_language = (JSONObject) rel.get("text-representation");
			language = (String) json_language.get("language");
		}
		// format & tracks
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
		// Έλενχος στο release-group αν υπάρχει το primary-type
		if (relgroup.get("primary-type") == null) {
			System.out.println("No Primary Type for Release");
		} else {
			type = (String) relgroup.get("primary-type");
		}
		// Παίρνουμε και τα secondary types για να δούμε ποια είναι Compilation
		JSONArray sec_types = (JSONArray) relgroup.get("secondary-types");

		if (type.equals("Album")) { // Αν το primary-type είναι Album

			// Πάρε το artist-credit, και δημιούργησε ένα Artist με id και name
			JSONArray artCredits = (JSONArray) rel.get("artist-credit");
			JSONObject artCredit = (JSONObject) artCredits.get(0);
			JSONObject arti = (JSONObject) artCredit.get("artist");
//            JSONObject type = (JSONObject) artCredit.
			Artist art = new Artist();
			art.setId((String) arti.get("id"));
			art.setName((String) arti.get("name"));
//            String arid = (String) arti.get("id");
//            String arname = (String) arti.get("name");
//           
			// Αν το secondary-type είναι διάφορο του Compilation
			if (sec_types == null || !(sec_types.get(0).toString().equals("Compilation"))) {
				// τότε φτιάξε Album
				// Artist art = getArtistByID(arid);
				release = new Album(art, id, title, status, language, releaseDate, format, (int) trackCount, type);
				// Αλλιως αν είναι ίσο του Compilation
			} else if (sec_types.get(0).toString().equals("Compilation")) {
				ArrayList<Artist> artArr;
				// artArr.add(art);
				type = "Compilation";
				artArr = getVariousArtistsOfCompilation(id);
				// τότε φτιάξε Compilation
				release = new Compilation(artArr, id, title, status, language, releaseDate, format, (int) trackCount,
						type);

			}
			// Γύρνα το πλέον φτιαγμένο Release->Album,Compilation
			return release;
		}
		// Αν δεν είναι Album το primary-type γύρνα null
		return null;

	}

	public static ArrayList<Artist> getVariousArtistsOfCompilation(String compiID) throws ParseException, IOException {
		ArrayList<Artist> artArr = new ArrayList();
//Xρησιμοποιουμε το "?inc=artist-credits+recordings" για να βρύμε ποιοι artist συμμετείχαν        
		// στο Compilation
		String url_compilation = releaseURL + compiID + inc + "artist-credits+recordings" + endURL;
		JSONObject response = getResponse(url_compilation);
		JSONArray medias = (JSONArray) response.get("media");
		JSONObject media = (JSONObject) medias.get(0);
		JSONArray tracks = (JSONArray) media.get("tracks");
		for (int i = 0; i < tracks.size(); i++) {
			JSONObject track = (JSONObject) tracks.get(i);
			JSONArray artcredit = (JSONArray) track.get("artist-credit");
			JSONObject artCredit = (JSONObject) artcredit.get(0);
			JSONObject artist = (JSONObject) artCredit.get("artist");

			String type = (String) artist.get("type");
			String id = (String) artist.get("id");
			String name = (String) artist.get("name");
			Artist art = new Artist();
			art.setId(id);
			art.setName(name);
			art.setType(type);
			// Artist a = getArtistByID(id);
			artArr.add(art);
		}
		// Artist tmpart = getArtistByID(artArr.get(0).getId());
		// artArr.set(0, tmpart);
		return artArr;
	}

	// Με βάση ένα JSONObject (response απο το MusicBrainz) χτίζουμε ένα
	// Artist->Person ή Artist->Group
	public static Artist makeArtist(JSONObject art)
			throws ParseException, IOException, NullPointerException, java.text.ParseException {
		Artist arti = null;

		ArrayList<String> aliases = new ArrayList();
		ArrayList<String> tags = new ArrayList();
		// Artist
		try {

			String type = (String) art.get("type"); // type
			String id = (String) art.get("id"); // id
			String name = (String) art.get("name"); // name
			String country = (String) art.get("country"); // country
			// Cities
			String cities = null;
			JSONObject area = (JSONObject) art.get("begin-area");
			if (!(area == null)) {
				cities = (String) area.get("name");
			}
			// Aliases
			JSONArray jsonAliases = (JSONArray) art.get("aliases");
			if (!(jsonAliases == null)) {
				aliases = new ArrayList(jsonAliases.size());
				for (int a = 0; a < jsonAliases.size(); a++) {

					JSONObject alias = (JSONObject) jsonAliases.get(a);
					aliases.add((String) alias.get("name"));
				}
			}
			// Tags
			JSONArray jsonTags = (JSONArray) art.get("tags");
			if (!(jsonTags == null)) {
				tags = new ArrayList(jsonTags.size());
				for (int a = 0; a < jsonTags.size(); a++) {
					JSONObject tag = (JSONObject) jsonTags.get(a);
					tags.add((String) tag.get("name"));
				}
			}
			// Begin + End Date
			String jbegin;
			String jend;
			JSONObject life = (JSONObject) art.get("life-span");
			if (!(life == null)) { // για τον Person birth/deathDate
				jbegin = (String) life.get("begin");
				jend = (String) life.get("end");
			} else { // για το Group begin/endDate
				jbegin = (String) art.get("begin");
				jend = (String) art.get("end");
			}

			Date begin = getDate(jbegin);
			Date end = getDate(jend);
			// Αν το type είναι person ή member ενός Group
			if (type.equals("Person") || type.equals("member of band")) {
				// τότε φτιάξε Person
				arti = new Person(id, name, country, aliases, tags, type, cities, begin, end,
						(String) art.get("gender"));
				// Αλλιώς αν το type είναι Group;
			} else if (type.equals("Group")) {
				// τότε φτιάξε Group
				arti = new Group(id, name, country, aliases, tags, type, cities, begin, end, null);
				// και γέμισε με Members το Group
				getMembersOfGroup((Group) arti);

			} else {
				return null;
			}
			// return arti;
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}
		return arti;
	}

	public static Artist getArtistByID(String id)
			throws ParseException, IOException, NullPointerException, java.text.ParseException {
		Artist art;
		String url = artistURL + id + "?" + endURL;
		JSONObject response = getResponse(url);
		art = makeArtist(response);
		return art;

	}

	public static Release getReleaseByID(String id) throws ParseException, IOException {
		Release rel;
		String url = artistURL + id + "?" + endURL;
		JSONObject response = getResponse(url);
		rel = makeRelease(response);
		return rel;
	}
	
	private static Date getDate(String in) throws java.text.ParseException {
		Date out = null;
		DateFormat formatter;
		//System.out.println(in);
//		DateTimeFormatter dateFormatter;
		if (in.length() <= 4) {
			formatter = new SimpleDateFormat("yyyy");
//			dateFormatter = DateTimeFormatter.ofPattern("uuuu");
		} else {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			//dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		} 
		out = formatter.parse(in);
	
		return out;
	}
}
