package com.example.usermanagement.repository;

import com.example.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByName(String name);

    public Role findByName(Enum name);

    @Query("select r.name from Role r where r.id = ?1")
    public String getNameById(Long id);
}
