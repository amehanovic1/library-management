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
    @PreAuthorize("hasRole('Admin')")
    public Users addUserByAdmin(@RequestBody Users user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new IllegalArgumentException("At least one role must be assigned to the user.");
        }

        Set<Role> rolesToAssign = new HashSet<>();

        for (Role incomingRole : user.getRole()) {
            Role roleFromDb = roleRepository.findByRoleName(incomingRole.getRoleName())
                    .orElseThrow(() -> new NotFoundException("Role not found: " + incomingRole.getRoleName()));
            rolesToAssign.add(roleFromDb);
        }
        user.setRole(rolesToAssign);

        String password = user.getPassword();
        String encryptPassword = passwordEncoder.encode(password);
        user.setPassword(encryptPassword);

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
        user.setUsername(userDetails.getUsername());

        if (userDetails.getRole() == null || userDetails.getRole().isEmpty()) {
            throw new IllegalArgumentException("At least one role must be assigned to the user.");
        }
        Set<Role> rolesToAssign = new HashSet<>();
        for (Role incomingRole : userDetails.getRole()) {
            // Provjeri postoji li ova rola u bazi
            Role roleFromDb = roleRepository.findByRoleName(incomingRole.getRoleName())
                    .orElseThrow(() -> new NotFoundException("Role not found: " + incomingRole.getRoleName()));
            rolesToAssign.add(roleFromDb);
        }

        user.setRole(rolesToAssign);

        Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist."));

        usersRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully.");
    }

}
