package com.ecommerce.mailservice.controller;

import com.ecommerce.mailservice.exception.OtpNotMatchedException;
import com.ecommerce.mailservice.exception.UserNotFoundException;
import com.ecommerce.mailservice.model.Verify;
import com.ecommerce.mailservice.repository.VerifyRepoitory;
import com.ecommerce.mailservice.service.EmailService;
import com.ecommerce.mailservice.service.VerifyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class VerifyController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerifyRepoitory verifyRepoitory;
    @Autowired
     private VerifyService verifyService;

    // ScheduledExecutorService to schedule deletion task
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    Random random = new Random(1000);

    @PostMapping("/sendOTP")
    public ResponseEntity<String> sendOtp(@RequestParam("email") String email, HttpSession httpSession){
        System.out.println("Email" + email);

//        Generating the random 4 digit otp
        int otp = random.nextInt(9999);
        System.out.println("OTP_" + otp);

        String subject = "OTP from ecommerce website";
        String message = "OTP_"+ otp;
        String to = email;

        boolean flag  = this.emailService.sendEmail(subject, message, to);
        if (flag){
            Verify verify=new Verify();
            verify.setSendedOtp(otp);
            verify.setSenderEmail(email);
            verifyRepoitory.save(verify);

            // Schedule deletion of sender email after 2 minutes
            executorService.schedule(() -> deleteSenderEmailAndOtp(verify.getSenderEmail()), 2, TimeUnit.MINUTES);

//            httpSession.setAttribute("myOtp", otp);
//            httpSession.setAttribute("email", email);
            return new ResponseEntity<String>("Successfully sent", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Otp not sent",HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/verifyotp/fromdatabase")
    public ResponseEntity verifyByUserEmailAndOtp(@RequestBody Verify verify) throws UserNotFoundException, OtpNotMatchedException {


        try{
            ResponseEntity responseEntity = new ResponseEntity(verifyService.verifyByUserNameAndOtp(verify), HttpStatus.OK);

            // Schedule deletion of sender email after 2 minutes
//            executorService.schedule(() -> deleteSenderEmailAndOtp(verify.getSenderEmail()), 2, TimeUnit.MINUTES);

            return responseEntity;
        }catch (UserNotFoundException e){
            return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
        }catch (OtpNotMatchedException e){
            return new ResponseEntity("Otp Not Matched", HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{senderEmail}")
    public ResponseEntity<?> deleteSenderEmailAndOtp(@PathVariable String senderEmail){
        this.verifyService.deleteSenderEmailAndOtp(senderEmail);
        return new ResponseEntity<>("Sender Email deleted successfully", HttpStatus.OK);
    }

}
