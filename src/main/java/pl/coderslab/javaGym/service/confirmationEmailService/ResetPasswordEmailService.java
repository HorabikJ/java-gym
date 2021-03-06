package pl.coderslab.javaGym.service.confirmationEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.javaGym.entity.confirmationEmail.ResetPasswordEmailDetails;
import pl.coderslab.javaGym.entity.user.User;
import pl.coderslab.javaGym.error.customException.LinkExpiredException;
import pl.coderslab.javaGym.error.customException.ResourceNotFoundException;
import pl.coderslab.javaGym.repository.ResetPasswordEmailRepository;
import pl.coderslab.javaGym.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResetPasswordEmailService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private ResetPasswordEmailRepository resetPasswordEmailRepository;

    @Autowired
    public ResetPasswordEmailService(ResetPasswordEmailRepository resetPasswordEmailRepository,
                                     UserRepository userRepository,
                                     BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.resetPasswordEmailRepository = resetPasswordEmailRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResetPasswordEmailDetails save(ResetPasswordEmailDetails newResetPasswordEmailDetails) {
        Long userId = newResetPasswordEmailDetails.getUser().getId();
        ResetPasswordEmailDetails oldResetPasswordEmailDetails = resetPasswordEmailRepository
                .findByUserId(userId);
        if (oldResetPasswordEmailDetails == null) {
            return resetPasswordEmailRepository.save(newResetPasswordEmailDetails);
        } else {
            newResetPasswordEmailDetails.setId(oldResetPasswordEmailDetails.getId());
            return resetPasswordEmailRepository.save(newResetPasswordEmailDetails);
        }
    }

    @Transactional
    public Boolean resetPassword(String param, String password) {
        ResetPasswordEmailDetails emailDetails = resetPasswordEmailRepository.findByParam(param);
        if (emailDetails != null) {
            if (isLinkActive(emailDetails)) {
                resetPassword(password, emailDetails);
                return true;
            } else {
                throw new LinkExpiredException();
            }
        } else {
            throw new ResourceNotFoundException();
        }
    }

    private Boolean isLinkActive(ResetPasswordEmailDetails emailDetails) {
        Integer linkExpirationTimeInMinutes = emailDetails.getMinutesExpirationTime();
        LocalDateTime sendTime = emailDetails.getSendTime();
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.isBefore(sendTime.plusMinutes(linkExpirationTimeInMinutes));
    }

    private void resetPassword(String password, ResetPasswordEmailDetails emailDetails) {
        User user = emailDetails.getUser();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        emailDetails.setParam(UUID.randomUUID().toString());
        resetPasswordEmailRepository.save(emailDetails);
    }
}
