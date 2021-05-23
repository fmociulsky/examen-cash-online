package examen.cashonline.usersloans.model;

public class PageDetail {

    private int page;
    private int size;

    public PageDetail() {
    }

    public PageDetail(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
