package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositiries.PeopleRepository;
import ru.kata.spring.boot_security.demo.security.PersonDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Service
public class PersonDetailsService implements UserDetailsService {
    private PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetails(person.get());
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }




    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public Optional<Person> getPersonByName(String username) {
        return peopleRepository.findByUsername(username);
    }
    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
    @Transactional
    public void updateUserById(int id, Person personDetails) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            person.setUsername(personDetails.getUsername());
            person.setLastName(personDetails.getLastName());
            person.setAge(personDetails.getAge());
            person.setEmail(personDetails.getEmail());
            person.setRoles((Role) personDetails.getRoles());
            person.setPassword(passwordEncoder().encode(person.getPassword()));

            peopleRepository.save(person);
        } else {
            throw new RuntimeException("Пользователь с таким id не найден");
        }
    }

    public Person getById(int id) {
        return peopleRepository.getById(id);
    }



}
