package upc.edu.oneup.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.oneup.exception.ResourceNotFoundException;
import upc.edu.oneup.model.PaymentMethod;
import upc.edu.oneup.model.User;
import upc.edu.oneup.repository.PaymentMethodRepository;
import upc.edu.oneup.service.PaymentMethodService;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getPaymentMethodById(int id) {
        return paymentMethodRepository.findByUser_Id(id);
    }

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod updatePaymentMethod(int id, PaymentMethod paymentMethod) {
        PaymentMethod existingPaymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Method not found with id: " + id));
        paymentMethod.setCardNumber(existingPaymentMethod.getCardNumber());
        paymentMethod.setUser(existingPaymentMethod.getUser());
        paymentMethod.setProducts(existingPaymentMethod.getProducts());
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void deletePaymentMethod(int id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            paymentMethodRepository.delete(paymentMethodOptional.get());
        } else {
            throw new ResourceNotFoundException("Payment Method not found with id: " + id);
        }
    }

    @Override
    public PaymentMethod getPaymentMethodByUserId(int userId) {
        return paymentMethodRepository.findByUser_Id(userId);
    }
}
