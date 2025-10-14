package Domain;

public class BookDomain {
    private int isbn;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int copiesAvailable;
    private float priceRef;

    public BookDomain() {}

    public BookDomain(int isbn, String title, String author, String category, int totalCopies, int copiesAvailable, float priceRef) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.copiesAvailable = copiesAvailable;
        this.priceRef = priceRef;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public float getPriceRef() {
        return priceRef;
    }

    public void setPriceRef(float priceRef) {
        this.priceRef = priceRef;
    }
}
