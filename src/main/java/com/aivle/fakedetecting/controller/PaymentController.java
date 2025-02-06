package com.aivle.fakedetecting.controller;

import com.aivle.fakedetecting.client.api.PortOneApiCilent;
import com.aivle.fakedetecting.dto.ResponsePayment;
import io.portone.sdk.server.payment.Payment;
import io.portone.sdk.server.payment.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PortOneApiCilent portOneApiCilent;

    @PostMapping("/payment/complete")
    public void verifyPayment(@RequestBody Map<String, String> payload) {
        ResponsePayment responsePayment = portOneApiCilent.getPayment(payload.get("paymentId"));


        System.out.printf("Payment Id: %s\n", responsePayment.getId());
    }
}
