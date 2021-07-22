package com.example.usermanagement.repository;

import com.example.usermanagement.model.ERole;
import com.example.usermanagement.model.Role;
import com.example.usermanagement.model.Section;
import com.example.usermanagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByFullName(String fullName);
//    @Query("select u from User u")
//    List<User> getAllUsers();
    Optional<User> findUserByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    @Query("Select u from User u where u.role.name = ?1")
    Optional<List<User>> findByRole(ERole name);

    @Query("Select u from User u where u.role.name = ?1")
    Page<User> findByRole(ERole name, Pageable pageable);
    // Search by fullname and role user
    Optional<List<User>> findByFullNameContainingAndRole(String fullName, Role role);
    // Search by address
    Optional<List<User>> findByAddressContainingAndRole(String address, Role role);
    // Search by description
    Optional<List<User>> findByDescriptionContainingAndRole(String description, Role role);
    // Search by Section and role user
    Optional<List<User>> findBySectionAndRole(Section section, Role role);
    @Query("select u from User u where lower(u.fullName) like lower(concat('%',:fullName,'%')) and " +
            " lower(u.address) like lower(concat('%',:address,'%')) and " +
            " u.dateOfBirth >= :fromDate and u.dateOfBirth <= :toDate" )
    Optional<List<User>> advanceSearch(String fullName, String address, Date fromDate, Date toDate);

    Page<User> findByAddressContaining(String address, Pageable pageable);

    Page<User> findByDescriptionContaining(String address, Pageable pageable);

    Page<User> findUserByDateOfBirthBetween(Date startDate, Date endDate, Pageable pageable);

    Page<User> findUsersByFullName (String fullName, Pageable pageable);

    Page<User> findByFullNameContainingAndAddressContainingAndDescriptionContaining(String fullName, String Address, String Description, Pageable pageable);

//    Page<User> findUsersByAddress(String address, Pageable pageable);
//
//    Page<User> findUsersByDescription(String description, Pageable pageable);
}
