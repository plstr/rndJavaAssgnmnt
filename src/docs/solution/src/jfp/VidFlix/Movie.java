package jfp.VidFlix;

public class Movie {
    private String   m_name = "";
    private String[] m_genre;
    private int      m_year;
    private String[] m_status;
    
    public Movie(String name, int year, String[] genres, String[] status) {
        m_name = name;
        m_year = year;
        m_genre = genres;
        m_status = status;
    }
    
    public String getName() {
        return m_name;
    }
    
    public int getYear() {
        return m_year;
    }
    
    public String[] getGenres() {
        return m_genre;
    }
    
    public String[] getStatus() {
        return m_status;
    }
    
    public boolean isRentable() {
        boolean test = false;
        for (String status : m_status) {
            test = status.toLowerCase().contains("rent");
            if (test) break;
        }
        return test;
    }
    
    public boolean isPurchasable() {
        boolean test = false;
        for (String status : m_status) {
            test = status.toLowerCase().contains("buy");
            if (test) break;
        }
        return test;
    }

    @Override
    public boolean equals(Object obj) {
        Movie cmpTo = Movie.class.cast(obj);
        return m_name.toLowerCase().equals(cmpTo.m_name.toLowerCase()) && 
                m_year == cmpTo.m_year;
    }

    @Override
    public int hashCode() {
        return m_name.hashCode() + m_year;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(m_name).append(" released in ").append(m_year).append(" listed under ");
        for (String genre : m_genre) {
            sBuilder.append(genre).append(' ');
        }
        sBuilder.append(" is available to ");
        for (String format : m_status) {
            sBuilder.append(format).append(' ');
        }
        return sBuilder.toString();
    }
    
    
}
