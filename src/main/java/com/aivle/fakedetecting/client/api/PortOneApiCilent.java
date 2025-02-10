package com.aivle.fakedetecting.client.api;

import com.aivle.fakedetecting.config.FeignConfig;
import com.aivle.fakedetecting.config.PaymentFeignConfig;
import com.aivle.fakedetecting.dto.ResponsePayment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "PortOneApiClient",
        url = "https://api.portone.io", // 구축할 서버 주소
        configuration = PaymentFeignConfig.class
)
@Component
public interface PortOneApiCilent {
//    , headers = {"Authorization=PortOne qTm6GDWqy2sFy0US9r7ojMICjWdstrnDSDGpL28UFSHJQxpDQI3VJEBT6FKvDnccFCUKBnn5nnePUOue", "Content-Type=application/json"}
    @GetMapping(value = "/payments/{paymentId}")
    ResponsePayment getPayment(@PathVariable("paymentId") String paymentId);

}
