package lk.shaanzx.online_auction_system_backend.service.impl;

import jakarta.transaction.Transactional;
import lk.shaanzx.online_auction_system_backend.dto.UserDTO;
import lk.shaanzx.online_auction_system_backend.entity.User;
import lk.shaanzx.online_auction_system_backend.repo.RegisterRepo;
import lk.shaanzx.online_auction_system_backend.service.RegisterService;
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

    @Override
    public int saveUser(UserDTO userDTO) {
        if (registerRepo.existsByEmail(userDTO.getUserEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
            registerRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }


    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
