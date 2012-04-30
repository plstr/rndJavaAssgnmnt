package jfp.VidFlix;

import java.io.Console;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 */

/**
 * @author abhijeet
 */
public class VidFlixManager {

	private MovieLibrary m_library = null;
	private Console m_console = null;
	private Movie[] m_rented = null;
	private final int RENT_LIMIT = 2;
	private Movie[] m_purchased = null;
	private final int PURCHASE_LIMIT = 50;
	private int m_rentCount = 0;
	private int m_purchaseCount = 0;

	private String m_menuSeparator = "================================================================================";
	private String m_subMenuSeparator = "--------------------------------------------------------------------------------";

	private Movie[] defaultMovieList = new Movie[] {

			new Movie("The Avengers", 2012, new String[] { "Action",
					"Adventure", "Sci-Fi" }, new String[] { "Rent" }),
			new Movie("Blade Runner", 1982, new String[] { "Drama", "Sci-Fi",
					"Thriller" }, new String[] { "Rent", "Buy" }),
			new Movie("Chronicle", 2012, new String[] { "Action", "Drama",
					"Sci-Fi" }, new String[] { "Rent" }),
			new Movie("The Matrix", 1999, new String[] { "Action", "Adventure",
					"Sci-Fi" }, new String[] { "Rent", "Buy" }),
			new Movie("The Matrix Reloaded", 2003, new String[] { "Action",
					"Adventure", "Sci-Fi" }, new String[] { "Rent", "Buy" }),
			new Movie("The Matrix Revolutions", 2003, new String[] { "Action",
					"Adventure", "Sci-Fi" }, new String[] { "Rent" }),
			new Movie("Star Wars: A new Hope", 1977, new String[] { "Action",
					"Adventure", "Fantasy" }, new String[] { "Rent", "Buy" }),

	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VidFlixManager m_manager = new VidFlixManager();
		m_manager.loadDefaultMovieList();
		m_manager.startStore();

	}

	private void loadDefaultMovieList() {
		for (Movie movie : defaultMovieList) {
			m_library.addMovie(movie);
		}
	}

	public VidFlixManager() {
		m_library = new MovieLibrary();
		m_console = System.console();
		m_rented = new Movie[RENT_LIMIT];
		m_purchased = new Movie[PURCHASE_LIMIT];

		if (m_console == null) {
			System.out
					.println("No Console Present. Launch the program from an interactive session.");
			System.exit(1);
		}

	}

	public void startStore() {
		m_library.printList();

		int selectedMenuItem = -1;
		while (selectedMenuItem != 0) {
			selectedMenuItem = displayMainMenu();
			switch (selectedMenuItem) {
			case 1:
				System.out.println("Rent a Movie");
				rentMovie();
				// Rent
				break;

			case 2:
				System.out.println("Buy a movie");
				buyMovie();
				// Buy
				break;

			case 3:
				System.out.println("List of Rentals");
				listRentals();
				// List Rentals
				break;

			case 4:
				System.out.println("List of Purchases");
				listPurchases();
				// List Purchase

			default:
				break;
			}
		}
	}

	private int displayMainMenu() {
		int selected = -1;

		while (selected == -1) {
			System.out.println(m_menuSeparator);
			System.out.println("MAIN MENU");
			System.out.println(m_menuSeparator);
			System.out.println("\t1. Rent Movie");
			System.out.println("\t2. Buy Movie");
			System.out.println("\t3. List Current Rentals");
			System.out.println("\t4. List Purchased Movies");
			System.out.println("");
			System.out.println("\t0. Exit the store"); // Exit the store
			System.out.println("");
			String option = m_console.readLine("> Please enter an option: ");
			System.out.println(m_menuSeparator);

			try {
				selected = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				selected = -1;
			}
			if (selected < 0 || selected > 4) {
				System.out.println("\n\nWrong selection. Try again.\n\n");
				selected = -1;
			}
		}
		return selected;
	}

	private void rentMovie() {
		String movieSearch = m_console
				.readLine("> Please enter the title of the movie you would like to rent: ");
		System.out.println(m_subMenuSeparator);

		Movie[] moviesFound = m_library.hasMoviesWithName(movieSearch);

		while (moviesFound.length == 0) {
			System.out.println("Couldn't find the movie. Try again.");
			movieSearch = m_console
					.readLine("> Please enter the title of the movie you would like to rent: ");
			System.out.println(m_subMenuSeparator);
			moviesFound = m_library.hasMoviesWithName(movieSearch);
		}

		System.out.println("\tThe following matches were found for \""
				+ movieSearch + "\":");
		int movieIndex = 0;
		StringBuilder sb_movie = new StringBuilder();
		Movie[] rentableMovies = new Movie[moviesFound.length];
		for (Movie movie : moviesFound) {
			if (!movie.isRentable())
				continue; // Skip the movie if it cannot be rented

			rentableMovies[movieIndex++] = movie;
			sb_movie.append("\t").append(movieIndex).append(". ")
					.append(movie.getName());
			sb_movie.append(' ').append("(Rent");
			if (movie.isPurchasable()) {
				sb_movie.append(" | Buy)");
			} else {
				sb_movie.append(" Only)");
			}

			System.out.println(sb_movie.toString()); // Output Name
			sb_movie.delete(0, sb_movie.length());
		}
		System.out.println("");
		System.out.println("\t0. BACK TO THE MAIN MENU"); // MAIN MENU

		String option = m_console
				.readLine("> Please enter the index number for the movie you would like to rent: ");
		System.out.println(m_subMenuSeparator);

		int selected = -1;
		while (selected == -1) {
			try {
				selected = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				selected = -1;
			}
			if (selected < 0 || selected > movieIndex) {
				System.out.println("\n\nWrong selection " + selected
						+ ". Try again.\n\n");
				selected = -1;
				option = m_console
						.readLine("> Please enter the index number for the movie you would like to rent: ");
				System.out.println(m_subMenuSeparator);
			}
		}

		if (selected == 0)
			return; // Exit Menu

		// Check number of movies rented previously
		if (m_rentCount < RENT_LIMIT) {
			int index = -1;
			for (Movie movie : m_rented) {
				index++;
				if (movie == null) {
					m_rented[index] = rentableMovies[selected - 1];
					m_rentCount++;
					break;
				}
			}
			System.out.println("Thank you for renting \""
					+ rentableMovies[selected - 1].getName()
					+ "\". It has now been added to your rental list"); // Success
		} else {
			System.out.println("You have already rented " + RENT_LIMIT
					+ " movies. Delete one of your rentals first.");
			System.out.println(m_subMenuSeparator);
		}

	}

	private void buyMovie() {
		String movieSearch = m_console
				.readLine("> Please enter the title of the movie you would like to buy: ");
		System.out.println(m_subMenuSeparator);

		Movie[] moviesFound = m_library.hasMoviesWithName(movieSearch);

		while (moviesFound.length == 0) {
			System.out.println("Couldn't find the movie. Try again.");
			movieSearch = m_console
					.readLine("> Please enter the title of the movie you would like to buy: ");
			System.out.println(m_subMenuSeparator);
			moviesFound = m_library.hasMoviesWithName(movieSearch);
		}

		System.out.println("\tThe following matches were found for \""
				+ movieSearch + "\":");
		int movieIndex = 0;
		StringBuilder sb_movie = new StringBuilder();
		Movie[] purchasableMovies = new Movie[moviesFound.length];
		for (Movie movie : moviesFound) {
			if (!movie.isPurchasable())
				continue; // Skip the movie if it cannot be bought

			purchasableMovies[movieIndex++] = movie;
			sb_movie.append("\t").append(movieIndex).append(". ")
					.append(movie.getName());
			sb_movie.append(' ').append("(Buy");
			if (movie.isRentable()) {
				sb_movie.append(" | Rent)");
			} else {
				sb_movie.append(" Only)");
			}

			System.out.println(sb_movie.toString()); // Output Name
			sb_movie.delete(0, sb_movie.length());
		}
		System.out.println("");
		System.out.println("\t0. Exit to the previous menu"); // Exit this menu

		String option = m_console
				.readLine("> Please enter the index number of movie you would like to purchase: ");
		System.out.println(m_subMenuSeparator);

		int selected = -1;
		while (selected == -1) {
			try {
				selected = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				selected = -1;
			}
			if (selected < 0 || selected > movieIndex) {
				System.out.println("\n\nWrong selection. Try again.\n\n");
				selected = -1;
				option = m_console
						.readLine("> Please enter the index number of movie you would like to purchase: ");
				System.out.println(m_subMenuSeparator);
			}
		}

		if (selected == 0)
			return; // Exit Menu

		// Check number of movies bought previously
		if (m_purchaseCount < PURCHASE_LIMIT) {
			int index = -1;
			for (Movie movie : m_purchased) {
				index++;
				if (movie == null) {
					m_purchased[index] = purchasableMovies[selected - 1];
					m_purchaseCount++;
					break;
				}
			}
			System.out.println("Thank you for purchasing \""
					+ purchasableMovies[selected - 1].getName()
					+ "\". It has now been added to your rental list"); // Success
		} else {
			System.out.println("You have already purchased " + PURCHASE_LIMIT
					+ " movies.");
		}
	}

	private void listRentals() {
		int movieIndex = 0;
		System.out.println("You have rented the following movies.");
		for (Movie movie : m_rented) {
			if (movie != null)
				System.out
						.println("\t" + ++movieIndex + ". " + movie.getName());
		}
		System.out.println("");
		System.out.println("\t0. BACK TO THE MAIN MENU"); // MAIN MENU
		System.out.println(m_subMenuSeparator);

		String option = m_console
				.readLine("> Please enter the index number of rental to delete: ");
		System.out.println(m_subMenuSeparator);

		int selected = -1;
		while (selected == -1) {
			try {
				selected = Integer.parseInt(option);
			} catch (NumberFormatException e) {
				selected = -1;
			}
			if (selected < 0 || selected > movieIndex) {
				System.out.println("\n\nWrong selection. Try again.\n\n");
				selected = -1;
				option = m_console
						.readLine("> Please enter the index number of rental to delete: ");
				System.out.println(m_subMenuSeparator);
			}
		}

		if (selected == 0)
			return; // Exit Menu

		m_rented[selected - 1] = null;
		m_rentCount--;

		for (int i = selected - 1; i < m_rentCount; i++) {
			m_rented[i] = m_rented[i + 1];
		}
		m_rented[m_rentCount] = null;
		System.out.println("Successfully deleted.");
	}

	private void listPurchases() {
		int movieIndex = 0;
		System.out.println("You have purchased the following movies.");
		for (Movie movie : m_purchased) {
			if (movie != null)
				System.out
						.println("\t" + ++movieIndex + ". " + movie.getName());
		}
	}

}
