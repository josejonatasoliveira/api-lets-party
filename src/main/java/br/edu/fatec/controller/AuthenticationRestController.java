package br.edu.fatec.controller;


import java.net.URI;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.fatec.exception.AppException;
import br.edu.fatec.jwt.JwtTokenUtil;
import br.edu.fatec.jwt.JwtUser;
import br.edu.fatec.model.Usuario;
import br.edu.fatec.repository.AuthorityRepository;
import br.edu.fatec.repository.UsuarioRepository;
import br.edu.fatec.request.ApiResponse;
import br.edu.fatec.request.JwtAuthenticationRequest;
import br.edu.fatec.request.SignUpRequest;
import br.edu.fatec.security.Authority;
import br.edu.fatec.security.AuthorityName;
import br.edu.fatec.service.JwtAuthenticationResponse;

@RestController("/app")
@Controller
public class AuthenticationRestController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private AuthorityRepository authorityRepository;


    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Usuario request){
    	if (usuarioRepository.existsByNome(request.getNome())){
    		 return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                     HttpStatus.BAD_REQUEST);
    	}
    	
    	if(usuarioRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
    	
    	Usuario usuario = new Usuario();
    	usuario.setId(request.getId());
    	usuario.setNome(request.getNome());
    	usuario.setSobrenome(request.getSobrenome());
    	usuario.setEmail(request.getEmail());
    	usuario.setSenha(request.getSenha());
    	usuario.setEnabled(request.getEnabled());
    	usuario.setLastPasswordResetDate(request.getLastPasswordResetDate());
    	usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    	
    	
    	Authority userAuthority = authorityRepository.findByName(AuthorityName.ROLE_ADMIN)
    			.orElseThrow(() -> new AppException("Authority nao gravado!"));
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/signup")
                .buildAndExpand(user.getNome()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    	
    	
    }
    
    
}
