package com.ecommerce.mailservice.service;

import com.ecommerce.mailservice.exception.OtpNotMatchedException;
import com.ecommerce.mailservice.exception.UserNotFoundException;
import com.ecommerce.mailservice.model.Verify;

public interface VerifyService {
    Object verifyByUserNameAndOtp(Verify verify) throws UserNotFoundException, OtpNotMatchedException;

    void deleteSenderEmailAndOtp(String senderEmail);
}
