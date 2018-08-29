package br.edu.fatec.jwt;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.fatec.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    
    private static final String secretKey = "spring.jwt.sec";
    
    public static String generateToken(Usuario usuario) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String usuarioJson = mapper.writeValueAsString(usuario);
        Date agora = new Date();
        Long hora = 1000L * 60L * 150L;//2h : 30m
        return Jwts.builder().claim("userDetails", usuarioJson)
            .setIssuer("br.gov.sp.fatec")
            .setSubject(usuario.getNome())
            .setExpiration(new Date(agora.getTime() + hora))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }
    
    public static Usuario parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userDetails", String.class);
        return mapper.readValue(credentialsJson, Usuario.class);
    }

}
