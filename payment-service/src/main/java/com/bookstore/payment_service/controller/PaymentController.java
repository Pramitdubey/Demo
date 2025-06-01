package com.bookstore.payment_service.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    private RazorpayClient razorpayClient;

    @PostConstruct
    public void init() throws RazorpayException {
        razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody Map<String, Object> request) {
        try {
            int amount = (int)((double) request.get("amount") * 100); // Convert to paise

            JSONObject options = new JSONObject();
            options.put("amount", amount);
            options.put("currency", "INR");
            options.put("receipt", "order_rcptid_" + UUID.randomUUID());

            Order order = razorpayClient.orders.create(options);

            Map<String, String> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", String.valueOf(order.get("amount")));
            response.put("currency", order.get("currency"));
            response.put("key", razorpayKey);

            return ResponseEntity.ok(response);

        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Payment creation failed: " + e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
        String orderId = paymentDetails.get("razorpay_order_id");
        String paymentId = paymentDetails.get("razorpay_payment_id");
        String signature = paymentDetails.get("razorpay_signature");

        try {
            String data = orderId + "|" + paymentId;
            String generatedSignature = hmacSha256(data, razorpaySecret);

            if (generatedSignature.equals(signature)) {
                // Save successful payment to DB (optional)
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Verification failed: " + e.getMessage());
        }
    }

    private String hmacSha256(String data, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
    }
} 

