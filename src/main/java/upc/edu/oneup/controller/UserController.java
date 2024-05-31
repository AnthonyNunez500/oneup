package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.Patient;
import upc.edu.oneup.model.User;
import upc.edu.oneup.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Users", description = "the user API")
@RestController
@RequestMapping("/api/oneup/v1") //@RequestMapping("/api/oneup/v1")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Este método recibe un objeto JSON con las credenciales de un usuario y verifica si el usuario existe en la base de datos
    @PostMapping("/user/authenticate")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = userService.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(user.getId()));
            response.put("username", user.getUsername());
            response.put("password", user.getPassword());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    // Obtiene todos los Users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Obtiene el User por ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Obtiene Los pacientes de un determinado User
    @GetMapping("/users/{id}/patients")
    public List<Patient> getPatientsByUserId(@PathVariable int id) {
        return userService.getPatientsByUserId(id);
    }

    // Crea el User
    @PostMapping("/users")
    @Transactional
    public User createUser(@RequestBody  User user) {
        validateUser(user);
        return userService.createUser(user);
    }

    // Actualiza el User
    @PutMapping("users/{id}")
    @Transactional
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        validateUser(updatedUser);
        return userService.updateUser(id, updatedUser);
    }

    // Elimina el User
    @DeleteMapping("users/{id}")
    @Transactional
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }


    // User Post Validation
    //Este método valida que los campos del objeto User no estén vacíos y que no excedan el límite de caracteres
    public void validateUser(User user) {
        // Validate username, password, name, lastname, email and phone

        // Username Validation
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");

        }
        if (user.getUsername().length()>30) {
            throw new ValidationException("Username must not be more than 30 characters");
        }

        // Password Validation
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (user.getPassword().length()>30) {
            throw new ValidationException("Password must not be more than 30 characters");
        }

        // Name Validation
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        if (user.getName().length()>30) {
            throw new ValidationException("Name must not be more than 30 characters");
        }

        // Lastname Validation
        if (user.getLastname() == null || user.getLastname().trim().isEmpty()) {
            throw new ValidationException("Lastname is required");
        }
        if (user.getLastname().length()>30) {
            throw new ValidationException("Lastname must not be more than 30 characters");
        }

        // Email Validation
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (user.getEmail().length()>50) {
            throw new ValidationException("Email must not be more than 50 characters");
        }

        // Phone Validation
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new ValidationException("Phone is required");
        }
        if (user.getPhone().length()>9) {
            throw new ValidationException("Phone must not be more than 9 characters");
        }
        // return contador;
    }

}