package examen.cashonline.usersloans.model;

import examen.cashonline.usersloans.entity.Loan;
import org.springframework.data.domain.Page;

import java.util.List;

public class LoanResponseData {

    private List<Loan> items;
    private PageDetail paging;

    public LoanResponseData() {
    }

    public LoanResponseData(List<Loan> items, PageDetail paging) {
        this.items = items;
        this.paging = paging;
    }

    public List<Loan> getItems() {
        return items;
    }

    public void setItems(List<Loan> items) {
        this.items = items;
    }

    public PageDetail getPaging() {
        return paging;
    }

    public void setPaging(PageDetail paging) {
        this.paging = paging;
    }
}
