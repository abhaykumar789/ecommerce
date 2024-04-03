package com.ecommerce.mailservice.service;

import com.ecommerce.mailservice.exception.OtpNotMatchedException;
import com.ecommerce.mailservice.exception.UserNotFoundException;
import com.ecommerce.mailservice.model.Verify;
import com.ecommerce.mailservice.repository.VerifyRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerifyServiceImpl implements VerifyService{
    @Autowired
    private VerifyRepoitory verifyRepoitory;


    @Override
    public Object verifyByUserNameAndOtp(Verify verify) throws UserNotFoundException, OtpNotMatchedException {
        Optional<Verify> verify1 = verifyRepoitory.findById(verify.getSenderEmail());

        if(verify1 != null){
            if (verify.getSendedOtp() != verify1.get().getSendedOtp()){
                throw new OtpNotMatchedException();
            }else {
                return "Matched";
            }
        }else {
            throw new UserNotFoundException() ;
        }
    }

    @Override
    public void deleteSenderEmailAndOtp(String senderEmail) {
        Verify verify= verifyRepoitory.findById(senderEmail).get();
        verifyRepoitory.delete(verify);
    }
}
