package org.example.commons.repositories.api;

import org.example.commons.api.FilteredRepository;
import org.example.commons.api.ProjectedRepository;
import org.example.commons.api.TypedRepository;
import org.example.commons.entities.User;
import org.example.commons.entities.dtos.UserDataTransfer;

public interface UserRepository extends TypedRepository<User, Long>, ProjectedRepository<User, Long, UserDataTransfer>, FilteredRepository<User> {
}
