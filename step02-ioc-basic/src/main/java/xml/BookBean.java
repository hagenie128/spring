package xml;

public class BookBean {

    private String title;
    private String author;
    private int price;

    // 기본 생성자 — setter 주입(<property>) 방식에서 필요
    public BookBean() {}

    // 전체 생성자 — 생성자 주입(<constructor-arg>) 방식에서 사용
    public BookBean(String title, String author, int price) {
        this.title  = title;
        this.author = author;
        this.price  = price;
    }

    public String getTitle()        { return title; }
    public void   setTitle(String title)   { this.title = title; }

    public String getAuthor()       { return author; }
    public void   setAuthor(String author) { this.author = author; }

    public int    getPrice()        { return price; }
    public void   setPrice(int price)      { this.price = price; }

    @Override
    public String toString() {
        return String.format("제목: %s | 저자: %s | 가격: %,d원", title, author, price);
    }
}
