package com.spring.problem04;

public class CheckoutService {

    private final PaymentProcessor paymentProcessor;
    private final ReceiptPrinter receiptPrinter;

    public CheckoutService(PaymentProcessor paymentProcessor, ReceiptPrinter receiptPrinter) {
        this.paymentProcessor = paymentProcessor;
        this.receiptPrinter = receiptPrinter;
    }

    public void checkout(int amount) {
        String result = paymentProcessor.process(amount);
        receiptPrinter.print(result);
    }
}
