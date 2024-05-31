package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.Patient;
import upc.edu.oneup.model.Report;
import upc.edu.oneup.repository.PatientRepository;
import upc.edu.oneup.service.ReportService;
import upc.edu.oneup.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reports", description = "the report API")
@RestController
@RequestMapping("/api/oneup/v1")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final PatientRepository patientRepository;

    @Autowired
    public ReportController(ReportService reportService, UserService userService, PatientRepository patientRepository) {
        this.reportService = reportService;
        this.userService = userService;
        this.patientRepository = patientRepository;
    }

    // Endpoint: /api/oneup/v1/reports
    // Method: GET
    // Obtiene todos los reports
    @Transactional(readOnly = true)
    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        return new ResponseEntity<>(reportService.getAllReports(), HttpStatus.OK);
    }

    // Endpoint: /api/oneup/v1/reports/{id}
    // Method: GET
    // Obtiene el report por ID
    @Transactional(readOnly = true)
    @GetMapping("/reports/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable int id) {
        return new ResponseEntity<>(reportService.getReportById(id), HttpStatus.OK);
    }

    // Endpoint: /api/oneup/v1/report/users/{id}
    // Method: GET
    // Obtiene el Report por ID de User
    @Transactional(readOnly = true)
    @GetMapping("/report/users/{id}")
    public ResponseEntity<Report> getReportByUserId(@PathVariable int id) {
        notFoundUser(id);
        notFoundReportByUserId(id);
        return new ResponseEntity<>(reportService.getReportById(id), HttpStatus.OK);
    }

    // Endpoint: /api/oneup/v1/report/{patientId}
    // Method: POST
    // Crea el Report
    @Transactional
    @PostMapping("/report/{patientId}")
    public ResponseEntity<Report> createReport(@RequestBody Report report, @PathVariable int patientId) {
        Patient patient = patientRepository.findById(patientId)
                 .orElseThrow(() -> new ValidationException("Patient not found"));
        report.setPatient(patient);
        validateReport(report);
        notFoundUser(report.getPatient().getId());
        return new ResponseEntity<>(reportService.saveReport(report), HttpStatus.CREATED);
    }

    // Endpoint: /api/oneup/v1/report/{id}
    // Method: DELETE
    // Elimina el Report por ID
    @DeleteMapping("/report/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable int id) {
        reportService.deleteReport(id);
        return new ResponseEntity<>("Report with id: " + id + " was deleted", HttpStatus.OK);
    }

    public void validateReport(Report report) {
        if (report.getPatient() == null) {
            throw new ValidationException("User is required");
        }
    }


    private void notFoundUser(int id) {
        if (userService.getUserById(id) == null) {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
    }

    private void notFoundReportByUserId(int id) {
        if (reportService.getReportByUserId(id) == null) {
            throw new ResourceNotFoundException("ShoppingCart with user id: " + id + " not found");
        }
    }

}
