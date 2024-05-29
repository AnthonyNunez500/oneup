package upc.edu.oneup.controller;

import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.exception.ValidationException;
import upc.edu.oneup.model.Device;

import upc.edu.oneup.model.Report;
import upc.edu.oneup.service.DeviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import upc.edu.oneup.service.UserService;

import java.util.List;

@Tag(name = "Devices", description = "the Devices API")
@RestController
@RequestMapping("/api/oneup/v1")
public class DeviceController {
    private final DeviceService  deviceService;
    private final UserService userService;

    @Autowired
    public  DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    // Endpoint: /api/oneup/v1/device
    // Method: GET
    // Obtiene todos los devices
    @Transactional(readOnly = true)
    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK);
    }

    // Endpoint: /api/oneup/v1/device/{id}
    // Method: GET
    // Obtiene el device por ID
    @Transactional(readOnly = true)
    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable int id) {
        return new ResponseEntity<>(deviceService.getDeviceById(id), HttpStatus.OK);
    }

    // Endpoint: /api/oneup/v1/device/users/{id}
    // Method: GET
    // Obtiene el device por ID de User
    @Transactional(readOnly = true)
    @GetMapping("/device/users/{id}")
    public ResponseEntity<Device> getDeviceByUserId(@PathVariable int id) {
        notFoundPatient(id);
        notFoundDeviceByPatientId(id);
        return new ResponseEntity<>(deviceService.getDeviceById(id), HttpStatus.OK);
    }

    // Endpoint: /api/oneup/v1/device
    // Method: POST
    // Crea el device
    @Transactional
    @PostMapping("/device")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        validateDevice(device);
        return new ResponseEntity<>(deviceService.saveDevice(device), HttpStatus.CREATED);
    }

    // Endpoint: /api/oneup/v1/device/{id}
    // Method: DELETE
    // Elimina el device por ID
    @DeleteMapping("/device/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable int id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>("Device with id: " + id + " was deleted", HttpStatus.OK);
    }

    public void validateDevice(Device device) {
        if (device.getPatient() == null) {
            throw new ValidationException("Patient is required");
        }
    }


    private void notFoundPatient(int id) {
        if (userService.getUserById(id) == null) {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
    }

    private void notFoundDeviceByPatientId(int id) {
        if (deviceService.getDeviceByPatientId(id) == null) {
            throw new ResourceNotFoundException("Device with user id: " + id + " not found");
        }
    }

}