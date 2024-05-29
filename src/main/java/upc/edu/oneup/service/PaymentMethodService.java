package upc.edu.oneup.service;

import upc.edu.oneup.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> getAllPaymentMethods();
    PaymentMethod getPaymentMethodById(int id);
    PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);
    PaymentMethod updatePaymentMethod(int id, PaymentMethod paymentMethod);
    void deletePaymentMethod(int id);

    PaymentMethod getPaymentMethodByUserId(int userId);
}
