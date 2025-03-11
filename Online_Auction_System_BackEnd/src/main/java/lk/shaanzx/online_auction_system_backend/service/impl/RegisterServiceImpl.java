package lk.shaanzx.online_auction_system_backend.service.impl;

import jakarta.transaction.Transactional;
import lk.shaanzx.online_auction_system_backend.dto.AuthDTO;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import lk.shaanzx.online_auction_system_backend.entity.User;
import lk.shaanzx.online_auction_system_backend.repo.RegisterRepo;
import lk.shaanzx.online_auction_system_backend.service.RegisterService;
import lk.shaanzx.online_auction_system_backend.util.JwtUtil;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RegisterServiceImpl implements UserDetailsService, RegisterService {

    @Autowired
    private RegisterRepo registerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthDTO register(UserDTO userDTO) {
        // Step 1: Save the user
        int result = saveUser(userDTO);
        if (result != VarList.Created) {
            return null; // Return null if user registration fails
        }

        // Step 2: Load the registered user details
        UserDTO registeredUser = loadUserDetailsByUsername(userDTO.getUserEmail());
        if (registeredUser == null) {
            return null; // Return null if user details cannot be loaded
        }

        // Step 3: Generate JWT token
        String token = jwtUtil.generateToken(registeredUser);

        // Step 4: Create and return AuthDTO
        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(registeredUser.getUserEmail());
        authDTO.setToken(token);
        return authDTO;
    }

    public int saveUser(UserDTO userDTO) {
        if (registerRepo.existsByEmail(userDTO.getUserEmail())) {
            return VarList.Not_Acceptable; // Email already exists
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
            registerRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.Created; // User saved successfully
        }
    }

/*    @Override
    public UserDTO searchUser(String username) {
        if (registerRepo.existsByEmail(username)) {
            User user = registerRepo.findByEmail(username);
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null; // User not found
        }
    }*/

    @Override
    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = registerRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = registerRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), getAuthority(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }
}