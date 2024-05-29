package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.Patient;
import upc.edu.oneup.model.User;
import upc.edu.oneup.repository.UserRepository;
import upc.edu.oneup.service.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.oneup.service.UserService;

import java.util.List;

@Tag(name = "Patients", description = "the Patient API")
@RestController
@RequestMapping("/api/oneup/v1") //@RequestMapping("/api/oneup/v1")
public class PatientController {
    private final PatientService patientService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public PatientController(PatientService patientService, UserService userService, UserRepository userRepository) {
        this.patientService = patientService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Obtiene el patient por ID
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtiene todos los Patients
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    // Crea el Patient
    @PostMapping("/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        User user = userRepository.findById(patient.getUser().getId())
                                    .orElseThrow(() -> new ValidationException("User not found"));
        Patient newPatient = patientService.savePatient(patient);
        newPatient.setUser(user);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    //elimina el patient por ID
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
