package com.example.project_management_system.security;

import com.example.project_management_system.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

//chodiztutaj o to z uzywamy SoC
//rozdzielenie odpoweidzialnosci
// mamy juz Usera ktory odpowaida za usera w ab zie
//a ten tutaj to jest user ktory jest ziwsazany zs bezpioecznestwiem
// cyzli rozdzielenie odpwiedzialnosci
//troche taki adapter co tlumaczy dla security
//prezz co latwiej wyglada to w serwisie ijest standardem branzy
public class UserSecurity implements UserDetails {
    private final User user;

    public UserSecurity(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //nadanie prostej roli
        return Collections.singletonList(() -> ("ROLE_USER"));
    }


    //narazie zwracamy tylko true bedzie mozna to rozbudowac
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
