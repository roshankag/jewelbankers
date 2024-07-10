package com.balaji.springjwt.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balaji.springjwt.dto.JwtLogin;
import com.balaji.springjwt.dto.LoginResponse;
import com.balaji.springjwt.dto.TokenDto;
import com.balaji.springjwt.models.Role;
import com.balaji.springjwt.models.User;
import com.balaji.springjwt.security.jwt.JwtUtils;
import com.balaji.springjwt.security.services.UserDetailsImpl;
import com.balaji.springjwt.services.RoleService;
import com.balaji.springjwt.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// http://localhost:8080
@RestController
@RequestMapping("/social")
@CrossOrigin("http://localhost:4200")
//http://localhost:8080/social
public class SocialController {

    @Autowired
    AuthenticationManager authenticationManager;
    
    private static final String GOOGLE_PUBLIC_KEYS_URL = "https://www.googleapis.com/oauth2/v3/certs";

    private UserService userService;

    private RoleService roleService;

    private JwtUtils tokenService;

    private PasswordEncoder passwordEncoder;

    private String email;


    @Value("${google.id}")
    private String idClient;

    @Value("${mySecret.password}")
    private String password;

    @Autowired
    public SocialController(UserService userService,RoleService roleService,JwtUtils tokenService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/loginSuccess")
    public void getLoginInfo(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
  // Read the request body
  String requestBody = "";
try {
    requestBody = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream()))
      .lines()
      .collect(Collectors.joining(System.lineSeparator()));
} catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}

System.out.println("Request Body: " + requestBody);

// Parse the request body to extract parameters
Map<String, String> params = parseRequestBody(requestBody);

String credential = params.get("credential");
String gCsrfToken = params.get("g_csrf_token");

System.out.println("Credential: " + credential);
System.out.println("g_csrf_token: " + gCsrfToken);

// Validate the ID token
boolean isValid = validateGoogleIdToken(credential);
if (isValid) {

    System.out.println("Valid token");


// Handle the authentication response here
// You can extract user details from the token if needed

// Redirect to the frontend dashboard
httpServletResponse.setHeader("Location", "http://localhost:4200/admin-dashboard");
httpServletResponse.setStatus(HttpServletResponse.SC_FOUND); // 302 status code
} else {
httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code
}
    }

    private User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        List<Role> roles = roleService.getRoles();
        user.getRoles().add(roles.get(0));
        return userService.saveUser(user);
    }

    private Map<String, String> parseRequestBody(String requestBody) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = requestBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = java.net.URLDecoder.decode(keyValue[0], java.nio.charset.StandardCharsets.UTF_8);
                String value = java.net.URLDecoder.decode(keyValue[1], java.nio.charset.StandardCharsets.UTF_8);
                params.put(key, value);
            }
        }
        return params;
    }

    private boolean validateGoogleIdToken(String jwt) {
        try {
            // Fetch Google public keys
            Map<String, PublicKey> publicKeys = fetchGooglePublicKeys();

            // Parse and validate the JWT using the appropriate public key
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKeyResolver(new SigningKeyResolverAdapter() {
                        @Override
                        public Key resolveSigningKey(JwsHeader header, Claims claims) {
                            return publicKeys.get(header.getKeyId());
                        }
                    })
                    .build()
                    .parseClaimsJws(jwt);

            // Print user information
            Claims claims = claimsJws.getBody();
            String userId = claims.getSubject();
            String email = claims.get("email", String.class);
            String name = claims.get("name", String.class);
            String pictureUrl = claims.get("picture", String.class);

            System.out.println("User ID: " + userId);
            System.out.println("Email: " + email);
            System.out.println("Name: " + name);
            System.out.println("Picture URL: " + pictureUrl);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Map<String, PublicKey> fetchGooglePublicKeys() throws IOException {
        URL url = new URL(GOOGLE_PUBLIC_KEYS_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(connection.getInputStream());
        Map<String, PublicKey> publicKeys = new HashMap<>();

        for (JsonNode keyNode : rootNode.get("keys")) {
            String kid = keyNode.get("kid").asText();
            String modulus = keyNode.get("n").asText();
            String exponent = keyNode.get("e").asText();

            byte[] modulusBytes = Base64.getUrlDecoder().decode(modulus);
            byte[] exponentBytes = Base64.getUrlDecoder().decode(exponent);

            KeySpec keySpec = new RSAPublicKeySpec(new BigInteger(1, modulusBytes), new BigInteger(1, exponentBytes));
            KeyFactory keyFactory = null;
            try {
                keyFactory = KeyFactory.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            PublicKey publicKey = null;
            try {
                publicKey = keyFactory != null ? keyFactory.generatePublic(keySpec) : null;
            } catch (InvalidKeySpecException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            publicKeys.put(kid, publicKey);
        }
        return publicKeys;
    }

}   