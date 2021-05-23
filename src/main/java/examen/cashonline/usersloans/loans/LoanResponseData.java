package examen.cashonline.usersloans.loans;

import examen.cashonline.usersloans.model.PageDetail;
import examen.cashonline.usersloans.loans.entity.Loan;

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
