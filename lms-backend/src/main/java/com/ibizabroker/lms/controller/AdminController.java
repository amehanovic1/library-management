package com.ibizabroker.lms.controller;

import com.ibizabroker.lms.dao.UsersRepository;
import com.ibizabroker.lms.dao.RoleRepository;
import com.ibizabroker.lms.entity.Role;
import com.ibizabroker.lms.entity.Users;
import com.ibizabroker.lms.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users")
    public Users addUserByAdmin(@RequestBody Users user) {
        // Provjeri postoje li role u korisnikovom unosu
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new IllegalArgumentException("At least one role must be assigned to the user.");
        }

        // Skup za pohranu validiranih rola
        Set<Role> rolesToAssign = new HashSet<>();

        // Prođi kroz korisnički unesene role
        for (Role incomingRole : user.getRole()) {
            // Dohvati rolu iz baze prema nazivu (pretpostavljamo da postoji `RoleRepository`)
            Role roleFromDb = roleRepository.findByRoleName(incomingRole.getRoleName())
                    .orElseThrow(() -> new NotFoundException("Role not found: " + incomingRole.getRoleName()));
            rolesToAssign.add(roleFromDb);
        }

        // Postavi validirane role korisniku
        user.setRole(rolesToAssign);

        // Šifriraj lozinku korisnika
        String password = user.getPassword();
        String encryptPassword = passwordEncoder.encode(password);
        user.setPassword(encryptPassword);

        // Spremi korisnika u bazu
        return usersRepository.save(user);
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('Admin')")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+ id +" does not exist."));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userDetails) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+ id +" does not exist."));

        user.setName(userDetails.getName());
        user.setRole(userDetails.getRole());
        user.setUsername(userDetails.getUsername());

        Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
}
