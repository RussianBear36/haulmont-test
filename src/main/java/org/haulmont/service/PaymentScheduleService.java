package org.haulmont.service;

import org.haulmont.dao.CreditOffer;
import org.haulmont.dao.PaymentSchedule;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentScheduleService {

    public List<PaymentSchedule> calculatePaymentSchedule(double interestRate, CreditOffer creditOffer, int sum, int term) {
        List<PaymentSchedule> list = new ArrayList<>();
        double totalPayments = 0.0;
        for (int i = 1; i <= term; i++) {
            double interestAmount = (interestRate / 100.0) * sum * ((double)(term - i + 1) / (double) term);
            double loanBodyAmount = (1.0 / term) * sum;
            double amountOfPayment = interestAmount + loanBodyAmount;
            String dateOfPayment = YearMonth.now().plusMonths(i).toString();
            PaymentSchedule paymentSchedule = new PaymentSchedule(dateOfPayment, amountOfPayment, interestAmount, loanBodyAmount, creditOffer);
            totalPayments += amountOfPayment;
            list.add(paymentSchedule);
        }
        creditOffer.setTotalPayments(totalPayments);
        return list;
    }
}
