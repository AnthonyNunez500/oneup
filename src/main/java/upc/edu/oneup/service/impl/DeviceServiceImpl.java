package upc.edu.oneup.service.impl;

import upc.edu.oneup.model.Device;

import upc.edu.oneup.model.Patient;
import upc.edu.oneup.repository.DeviceRepository;
import upc.edu.oneup.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceById(int id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public Device getDeviceByPatientId(int id) {
        return deviceRepository.findByPatient_Id(id);
    }

    @Override
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(int id) {deviceRepository.deleteById(id);}

    @Override
    public Patient getPatientByDeviceId(int id) {
        return deviceRepository.findById(id).get().getPatient();
    }

}
