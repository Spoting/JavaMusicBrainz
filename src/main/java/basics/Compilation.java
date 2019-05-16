package basics;

import java.io.Serializable;
import java.util.ArrayList;

public class Compilation extends Release { //implements Serializable
	private ArrayList<Artist> artists;
	//private Release release;

	
	public Compilation(ArrayList<Artist> artists ,String id, String title, String status, String language, String releaseDate, String format,
			int trackCount, String type) {
		super(id, title, status, language, releaseDate, format, trackCount, type);
		
		this.artists = artists;
	}


	@Override
	public String toString() {
		return "Compilation [artists=" + artists + "\n, release=" + super.toString() + "]\n";
	}
        

	public ArrayList<Artist> getArtists() {
		return artists;
	}

	public void setArtists(ArrayList<Artist> artists) {
		this.artists = artists;
	}
	
	
}
