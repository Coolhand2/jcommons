package org.example.commons.repositories.api;

import org.example.commons.api.FilteredRepository;
import org.example.commons.api.TypedRepository;
import org.example.commons.entities.Group;

public interface GroupRepository extends TypedRepository<Group, Long>, FilteredRepository<Group> {
}
