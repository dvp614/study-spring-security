package org.zerock.myapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.myapp.domain.Users;


public interface UsersRepository extends JpaRepository<Users, String> {
	// 1. Query Methods With Various Operators

} // end interface

