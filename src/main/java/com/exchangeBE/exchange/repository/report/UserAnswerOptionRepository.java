package com.exchangeBE.exchange.repository.report;

import com.exchangeBE.exchange.entity.Report.UserAnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerOptionRepository extends JpaRepository<UserAnswerOption, Long> {
}
