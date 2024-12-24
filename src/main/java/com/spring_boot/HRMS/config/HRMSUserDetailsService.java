package com.spring_boot.HRMS.config;

import com.spring_boot.HRMS.dao.HrDao;
import com.spring_boot.HRMS.entity.HR;
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

    private HrDao hrDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public HRMSUserDetailsService(HrDao hrDao,PasswordEncoder passwordEncoder) {
        this.hrDao = hrDao;
        this.passwordEncoder=passwordEncoder;
    }

    /**
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        HR hr=hrDao.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("can't find Hr profile with email:"+email));

        log.info(hr.toString());

        //Getting list of authorities.
        Collection<GrantedAuthority> authorities= List.of(new SimpleGrantedAuthority(hr.getRole()));

        return new User(hr.getEmail(),hr.getPassword(),authorities);
    }
}
