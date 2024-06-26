package com.awb.MyLibrary.repository;

import com.awb.MyLibrary.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
