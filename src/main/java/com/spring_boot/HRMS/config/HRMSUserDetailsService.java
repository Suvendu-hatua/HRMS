package com.spring_boot.HRMS.config;

import com.spring_boot.HRMS.dao.PersonDao;
import com.spring_boot.HRMS.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class HRMSUserDetailsService implements UserDetailsService {

    private PersonDao personDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public HRMSUserDetailsService(PersonDao personDao, PasswordEncoder passwordEncoder) {
        this.personDao = personDao;
        this.passwordEncoder=passwordEncoder;
    }

    /**
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person person =personDao.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("can't find Hr profile with email:"+email));

        //Getting list of authorities.
        Collection<GrantedAuthority> authorities= List.of(new SimpleGrantedAuthority(person.getRole()));

        return new User(person.getEmail(),person.getPassword(),authorities);
    }
}
