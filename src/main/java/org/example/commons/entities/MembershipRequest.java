package org.example.commons.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class MembershipRequest {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Group group;

    @ManyToOne
    private User user;
}
