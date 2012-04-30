/**
 * 
 */
package jfp.VidFlix;

/**
 * @author abhijeet
 */
public class MovieLibrary {

	private final int SIZE = 50;
	private int index = 0;
	private Movie[] m_library;

	/**
	 * A library of all movies
	 */
	public MovieLibrary() {
		m_library = new Movie[SIZE];
	}

	public void addMovie(Movie newMovie) {
		if (index < SIZE) {
			m_library[index++] = newMovie;
		}
	}

	public void addMovie(String name, int year, String[] genres,
			String[] formats) {
		if (index < SIZE) {
			m_library[index++] = new Movie(name, year, genres, formats);
		}
	}

	public void printList() {
		System.out.println("\n\n");
		for (int i = 0; i < index; i++) {
			System.out.println(m_library[i]);
		}
	}

	public Movie[] hasMoviesWithName(String movieName) {
		Movie[] tempList = new Movie[index];
		int listIndex = 0;
		for (Movie movie : m_library) {
			if (movie == null) {
				break;
			}
			if (movie.getName().toLowerCase().contains(movieName.toLowerCase())) {
				tempList[listIndex++] = movie;
			}
		}

		Movie[] matched = new Movie[listIndex];
		System.arraycopy(tempList, 0, matched, 0, listIndex);

		return matched;

	}

}
