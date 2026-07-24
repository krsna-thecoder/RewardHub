package com.amex.benefit_activation_engine.repository;

import com.amex.benefit_activation_engine.model.ClaimAuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimAuditRepository extends JpaRepository<ClaimAuditEvent, Long> {

    /** Full audit trail for a claim, oldest event first. */
    List<ClaimAuditEvent> findByClaimIdOrderByOccurredAtAscIdAsc(Long claimId);
}
