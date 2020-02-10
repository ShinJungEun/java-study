package chapter03;

public class Song {
	private String title;
	private String artist;
	private String album;
	private String composer;
	private int year;
	private int track;
	
	// 생성자 (생략 가능)
	public Song() {
	}
	
	public Song(String title, String artist, String album, String composer, int year, int track) {
		this();		// 다른 생성자를 호출할 때 사용가능.(Song())
		
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.composer = composer;
		this.year = year;
		this.track = track;
	}

	// this를 사용하여 코드 중복을 막을 수 있음
	public Song(String title, String artist) {
		this(title, artist, null, null, 0, 0);	// 다른 생성자 호출
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getComposer() {
		return composer;
	}
	public void setComposer(String composer) {
		this.composer = composer;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTrack() {
		return track;
	}
	public void setTrack(int track) {
		this.track = track;
	}
	
	public void show() {
		System.out.println(artist + " " + title + " ( " + album + ", " + year
				+ ", " + track + "번 track, " + composer + " 작곡 )");
	}
}
