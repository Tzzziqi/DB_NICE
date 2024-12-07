package com.edu.trip.controller.user;

import com.edu.trip.data_view.BookingView;
import com.edu.trip.data_view.InvoiceView;
import com.edu.trip.jpa.*;
import com.edu.trip.model.*;
import com.edu.trip.params.BookingCreateParam;
import com.edu.trip.params.PayParam;
import com.edu.trip.po.ChargeType;
import com.edu.trip.po.ResourceStatus;
import com.edu.trip.po.UserGender;
import com.edu.trip.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/customer/invoice")
@RestController
public class InvoiceController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/read")
    public ResponseEntity read(
            @RequestAttribute(value = "userId") Long userId,
            @RequestParam(value = "status") boolean paid) {
        List<InvoiceEntity> invoiceEntities = paid ? invoiceRepository.findAllByUserIdPaid(userId) : invoiceRepository.findAllByUserIdNoPayment(userId);
        return ResponseUtil.ok(
                invoiceEntities.stream().map(x -> new InvoiceView(
                        x, x.getBooking().getBookingId(), x.getBooking().getTrip().getTripId(), x.getBooking().getTrip().getName()
                )).collect(Collectors.toList())
        );
    }


    @Transactional
    @PostMapping("/update")
    public ResponseEntity update(
            @RequestAttribute(value = "userId") long userId,
            @RequestBody PayParam param
    ) {
        if (param.getInvoiceId() == null) {
            return ResponseUtil.fail("Invoice not exists");
        }
        if (StringUtils.isBlank(param.getPayMethod())) {
            return ResponseUtil.fail("Invalid pay method");
        }
        Optional<InvoiceEntity> invoiceOpt = invoiceRepository.findById(param.getInvoiceId());
        if (invoiceOpt.isEmpty()) {
            return ResponseUtil.fail("Invoice not exists");
        }
        InvoiceEntity invoiceEntity = invoiceOpt.get();
        if (invoiceEntity.getBooking().getUser().getId() != userId) {
            return ResponseUtil.fail("Invoice not exists");
        }
        if (invoiceEntity.getPayment() != null) {
            return ResponseUtil.fail("Invoice paid already");
        }
        PaymentEntity payment = new PaymentEntity();
        payment.setInvoice(invoiceEntity);
        payment.setBooking(invoiceEntity.getBooking());
        payment.setPaymentId(0L);
        payment.setPaymentAmount(invoiceEntity.getMoney());
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(param.getPayMethod());
        paymentRepository.save(payment);
        return ResponseUtil.ok(null);
    }


}
