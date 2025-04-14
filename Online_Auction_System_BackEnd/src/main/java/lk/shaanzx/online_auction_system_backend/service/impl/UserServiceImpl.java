package lk.shaanzx.online_auction_system_backend.service.impl;


import jakarta.transaction.Transactional;
import lk.shaanzx.online_auction_system_backend.dto.CategoryDTO;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import lk.shaanzx.online_auction_system_backend.entity.User;
import lk.shaanzx.online_auction_system_backend.repo.UserRepository;
import lk.shaanzx.online_auction_system_backend.service.UserService;
import lk.shaanzx.online_auction_system_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable; // Email already exists
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.Created; // User saved successfully
        }
    }

    @Override
    public List<UserDTO> getUsers() {
        if (userRepo.findAll().isEmpty()) {
            return null;
        }else {
            return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>(){}.getType());
        }
    }

    @Transactional
    @Override
    public int deleteUser(String email) {
        if (userRepo.existsByEmail(email)) {
            userRepo.deleteByEmail(email);
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int updateUser(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public int updateUserStatus(UserDTO userDTO) {
        if (userRepo.existsByEmail(userDTO.getEmail())) {
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }

    @Override
    public UserDTO getUserById(String email) {
        if (userRepo.existsByEmail(email)) {
            User user = userRepo.findByEmail(email);
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null; // User not found
        }
    }

    @Override
    public int updateUserByEmail(UserDTO userDTO) {
        User existingUser = userRepo.findByEmail(userDTO.getEmail());

        if (existingUser != null) {
            // ID එක keep කරලා map කරනවා - update වෙන්න ඔනි
            User updatedUser = modelMapper.map(userDTO, User.class);
            updatedUser.setId(existingUser.getId());
            if (userDTO.getPassword() != null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                updatedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }else {
                updatedUser.setPassword(existingUser.getPassword());
            }
            updatedUser.setRole(existingUser.getRole());
            updatedUser.setStatus(existingUser.getStatus());

            userRepo.save(updatedUser);
            return VarList.OK;
        } else {
            return VarList.Not_Found;
        }
    }



    @Override
    public UserDTO searchUser(String username) {
        if (userRepo.existsByEmail(username)) {
            User user = userRepo.findByEmail(username);
            return modelMapper.map(user, UserDTO.class);
        } else {
            return null; // User not found
        }
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }
}