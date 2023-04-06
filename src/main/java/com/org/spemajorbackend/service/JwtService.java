package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dro.JwtRequest;
import com.org.spemajorbackend.dto.JwtResponse;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthMasterRepository authMasterRepository;
    @Autowired
    private AuthenticationManager authenticationManager;


    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();

        authenticate(username, password);

        UserDetails userDetails = loadUserByUsername(username);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        AuthMaster user = authMasterRepository.findById(username).get();

        return new JwtResponse(
                user.getUsername(),
                user.getRole(),
                newGeneratedToken
        );
    }
    private void authenticate(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private Set getAuthority(AuthMaster authMaster){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+authMaster.getRole()));
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthMaster user = authMasterRepository.findById(username).get();

        if(user!=null){
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthority(user)
            );
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
