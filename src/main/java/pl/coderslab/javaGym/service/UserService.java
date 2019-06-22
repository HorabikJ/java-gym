package pl.coderslab.javaGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.javaGym.entity.Role;
import pl.coderslab.javaGym.entity.User;
import pl.coderslab.javaGym.enumClass.RoleEnum;
import pl.coderslab.javaGym.error.customException.DomainObjectException;
import pl.coderslab.javaGym.repository.RoleRepository;
import pl.coderslab.javaGym.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements AbstractService<User> {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        try {
            setUserProperties(user, false);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DomainObjectException();
        }
    }

    public User saveAsAdmin(User user) {
        try {
            setUserProperties(user, true);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DomainObjectException();
        }
    }

    private void setUserProperties(User user, boolean asAdmin) {
        Set<Role> roles = new HashSet<>();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(RoleEnum.ROLE_USER.toString());
        roles.add(userRole);
        if (asAdmin) {
            Role adminRole = roleRepository.findByRole(RoleEnum.ROLE_ADMIN.toString());
            roles.add(adminRole);
        }
        user.setRoles(roles);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<String> getAllUsersEmails() {
        return userRepository.getAllUsersEmails();
    }



}
