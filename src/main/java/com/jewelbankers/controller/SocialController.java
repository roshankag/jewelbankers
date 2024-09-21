package com.jewelbankers.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jewelbankers.configuration.DataSourceService;
import com.jewelbankers.entity.Role;
import com.jewelbankers.entity.User;
import com.jewelbankers.jwt.JwtUtils;
import com.jewelbankers.repository.UserRepository;
import com.jewelbankers.services.RoleService;
import com.jewelbankers.services.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// http://localhost:8080
@RestController
@RequestMapping("/social")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost"})
//http://localhost:8080/social
public class SocialController {

	 @Value("${datasource.secondary.url}")
	    private String secondaryDataSourceUrl;

	    @Value("${datasource.secondary.username}")
	    private String secondaryDataSourceUsername;

	    @Value("${datasource.secondary.password}")
	    private String secondaryDataSourcePassword;

	    
    @Autowired
    AuthenticationManager authenticationManager;
    
    private static final String GOOGLE_PUBLIC_KEYS_URL = "https://www.googleapis.com/oauth2/v3/certs";

    private UserService userService;

    private RoleService roleService;

    private JwtUtils tokenService;

    private PasswordEncoder passwordEncoder;

    private String email;

    @Autowired
    private UserRepository userRepository;

    private Claims claims;
    
    @Autowired
    DataSourceService dataSourceService;

    @Value("${mySecret.password}")
    private String password;
    @Autowired
  JwtUtils jwtUtils;

	/*
	 * @Autowired
	 * 
	 * @Qualifier("secondaryDataSource") private DataSource secondaryDataSource;
	 */
   
    
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
    String userEmail = claims.get("email", String.class);
    System.out.println(userEmail);
    
    User userDetails =  userRepository.findByEmail(userEmail);    
    List<String> roles = userDetails.getRoles().stream()
    .map(role -> role.getName().name()) // Extract role name from Enum
    .collect(Collectors.toList());

    System.out.println("Valid token");
    String token = jwtUtils.generateJwtToken(userEmail);
    // Split the email to get the domain name
    String[] emailParts = userEmail.split("@");
    String emailName = emailParts.length > 1 ? emailParts[0] : "";
    System.out.println("emailName"+emailName);
    String updatedJdbcUrl = secondaryDataSourceUrl.replace("krishnag",emailName);
    System.out.println(updatedJdbcUrl);
    dataSourceService.clearDataSource();
    dataSourceService.updateSecondaryDataSource(updatedJdbcUrl, secondaryDataSourceUsername, secondaryDataSourcePassword);
    dataSourceService.switchSecondaryDataSource();
    
    /*
	 * if (secondaryDataSource instanceof HikariDataSource) { // Close the existing
	 * data source HikariDataSource hikariDataSource = (HikariDataSource)
	 * secondaryDataSource; hikariDataSource.close(); // Close the existing
	 * connection pool // Create a new HikariDataSource with the updated JDBC URL //
	 * HikariDataSource newHikariDataSource = new HikariDataSource(); String
	 * updatedJdbcUrl = hikariDataSource.getJdbcUrl().replace("krishnag",
	 * emailName); switchSecondaryDataSource(); //dataSourceConfig.
	 * //reloadSecondaryDataSource(updatedJdbcUrl, "admin", "root"); //
	 * newHikariDataSource.setJdbcUrl(updatedJdbcUrl);
	 * //newHikariDataSource.setUsername(hikariDataSource.getUsername());
	 * //newHikariDataSource.setPassword(hikariDataSource.getPassword());
	 * //newHikariDataSource.setDriverClassName(hikariDataSource.getDriverClassName(
	 * ));
	 * 
	 * // Apply the same configuration settings to the new DataSource
	 * //newHikariDataSource.setMaximumPoolSize(hikariDataSource.getMaximumPoolSize(
	 * )); //newHikariDataSource.setMinimumIdle(hikariDataSource.getMinimumIdle());
	 * // ... (other configuration settings as needed)
	 * 
	 * // Update the reference to point to the new data source
	 * //this.secondaryDataSource = newHikariDataSource;
	 * 
	 * // Set the current data source context to use the updated data source
	 * //DataSourceContextHolder.setCurrentDataSource("secondary");
	 * 
	 * // Output the new URL to confirm //
	 * System.out.println("Updated DataSource URL: " +
	 * newHikariDataSource.getJdbcUrl()); } else { // Handle the case where the
	 * DataSource is not an instance of DriverManagerDataSource
	 * System.err.println("Cannot update URL on DataSource of type " +
	 * secondaryDataSource.getClass().getName()); }
	 */
    
    
 
// Handle the authentication response here
// You can extract user details from the token if needed
Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);

            responseBody.put("userId", userDetails.getId());
            responseBody.put("username", userDetails.getUsername());
            responseBody.put("email", userEmail);
            
          
            
            responseBody.put("roles", roles);
            // Convert the response body to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = "";
            try {
                jsonResponse = objectMapper.writeValueAsString(responseBody);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Set content type and write the JSON response
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            try {
                httpServletResponse.getWriter().write(jsonResponse);
                httpServletResponse.getWriter().flush();    
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

// Redirect to the frontend dashboard
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
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> params = new HashMap<>();
        
        try {
            // Parse JSON string to Map
            params = objectMapper.readValue(requestBody, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            // Handle parsing exception as needed
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
            claims = claimsJws.getBody();
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