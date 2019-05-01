package basics;

public class Album extends Release{
	private Artist art;
	//private Release rel;
	
	public Album(Artist art, String id, String title, String status, String language, String releaseDate, String format,
			int trackCount, String type) {
		super(id, title, status, language, releaseDate, format, trackCount, type);
		this.art = art;
	}



	@Override
	public String toString() {
		return "Album [art=" + art.toString() + "\n, [rel=" + super.toString() + "]]\n";
	}



	public Artist getArt() {
		return art;
	}
	public void setArt(Artist art) {
		this.art = art;
	}
	
	
}
