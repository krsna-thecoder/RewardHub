package com.amex.benefit_activation_engine.model;

/**
 * Lifecycle states of a claim as it moves through the submission and
 * approval workflow.
 *
 * <p>Typical happy path:
 * {@code DRAFT -> SUBMITTED -> UNDER_REVIEW -> APPROVED -> PAID}.
 * A claim may also end in {@code REJECTED}.</p>
 */
public enum ClaimStatus {

    /** Auto-generated and pre-filled, not yet submitted by the card member. */
    DRAFT,

    /** Submitted for processing. */
    SUBMITTED,

    /** Being assessed by the claims workflow. */
    UNDER_REVIEW,

    /** Approved for payout. */
    APPROVED,

    /** Declined; see the decision reason. */
    REJECTED,

    /** Approved and reimbursed to the card member. */
    PAID
}
