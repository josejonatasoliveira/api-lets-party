package br.edu.fatec.jwt;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.edu.fatec.model.Usuario;
import br.edu.fatec.security.Authority;


public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    @SuppressWarnings("unchecked")
	public static JwtUser create(Usuario usuario) {
        return new JwtUser(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getSenha(),
                mapToGrantedAuthorities(usuario.getAuthorities()),
                usuario.getEnabled(),
                usuario.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Authority> set) {
        return set.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
