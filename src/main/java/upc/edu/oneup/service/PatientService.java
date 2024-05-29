package upc.edu.oneup.service;

import upc.edu.oneup.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(int id);
    Patient savePatient(Patient patient);
    void deletePatient(int id);
}
