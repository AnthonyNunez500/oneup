package upc.edu.oneup.service;

import upc.edu.oneup.model.Device;
import upc.edu.oneup.model.Patient;

import java.util.List;

public interface DeviceService {
    List<Device> getAllDevices();
    Device getDeviceById(int id);
    Device getDeviceByPatientId(int id);
    Device saveDevice(Device device);
    void deleteDevice(int id);
    Patient getPatientByDeviceId(int id);
}
