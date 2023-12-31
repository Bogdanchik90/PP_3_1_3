package ru.kata.spring.boot_security.demo.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Override
    List<Role> findAllById(Iterable<Integer> integers);


    public

    @Override
    List<Role> findAll();
}
