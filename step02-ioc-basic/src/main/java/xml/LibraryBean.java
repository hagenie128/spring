package xml;

import java.util.List;
import java.util.Map;

public class LibraryBean {

    private String              libraryName;
    private BookBean            featuredBook;   // ref 주입
    private List<String>        genres;         // list 주입
    private Map<String, String> bookPrices;     // map 주입

    public void printInfo() {
        System.out.println("[Library - ref + 컬렉션 주입]");
        System.out.println("  도서관명: " + libraryName);
        System.out.println("  추천 도서: " + featuredBook.getTitle());
        System.out.println("  보유 장르: " + genres);
        System.out.println("  도서 가격표: " + bookPrices);
    }

    public String getLibraryName()               { return libraryName; }
    public void   setLibraryName(String n)       { this.libraryName = n; }

    public BookBean getFeaturedBook()            { return featuredBook; }
    public void     setFeaturedBook(BookBean b)  { this.featuredBook = b; }

    public List<String> getGenres()              { return genres; }
    public void         setGenres(List<String> g){ this.genres = g; }

    public Map<String, String> getBookPrices()              { return bookPrices; }
    public void                setBookPrices(Map<String, String> m) { this.bookPrices = m; }
}
