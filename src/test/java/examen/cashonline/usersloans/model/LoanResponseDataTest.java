package examen.cashonline.usersloans.model;

import examen.cashonline.usersloans.loans.entity.Loan;

import java.io.Serializable;
import java.util.List;

public class LoanResponseDataTest implements Serializable {

    private static final long serialVersionUID = 4988604118789500244L;
    private List<LoanTest> items;
    private PageDetail paging;

    public LoanResponseDataTest() {
    }

    public LoanResponseDataTest(List<LoanTest> items, PageDetail paging) {
        this.items = items;
        this.paging = paging;
    }

    public List<LoanTest> getItems() {
        return items;
    }

    public void setItems(List<LoanTest> items) {
        this.items = items;
    }

    public PageDetail getPaging() {
        return paging;
    }

    public void setPaging(PageDetail paging) {
        this.paging = paging;
    }

    private static class LoanTest{
        private Long id;
        private Double total;
        private Long userId;

        public LoanTest() {
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
