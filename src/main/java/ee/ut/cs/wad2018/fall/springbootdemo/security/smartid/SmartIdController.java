package ee.ut.cs.wad2018.fall.springbootdemo.security.smartid;

import ee.sk.smartid.*;
import ee.sk.smartid.exception.SmartIdException;
import ee.sk.smartid.exception.UserAccountNotFoundException;
import ee.sk.smartid.exception.UserRefusedException;
import ee.sk.smartid.rest.dao.NationalIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/smart-id/authentication")
public class SmartIdController {

    public static final String AUTHENTICATION_RESULT_KEY = "SMART_ID_AUTHENTICATION_RESULT";

    private final SmartIdService smartIdService;

    @Autowired
    SmartIdController(SmartIdService smartIdService) {
        this.smartIdService = smartIdService;
    }

    @PostMapping(value = "/start")
    public Verification startAuthentication(@RequestBody NationalIdentity nationalIdentity, HttpSession httpSession) {
        // For security reasons a new hash value must be created for each new authentication request
        AuthenticationHash authenticationHash = smartIdService.getAuthenticationHash();

        httpSession.setAttribute("nationalIdentity", nationalIdentity);
        httpSession.setAttribute("authenticationHash", authenticationHash);

        Verification verification = new Verification();
        verification.setCode(authenticationHash.calculateVerificationCode());
        return verification;
    }

    @PostMapping(value = "/poll")
    public SmartIdAuthenticationResult pollAuthenticationResult(HttpSession httpSession) {
        NationalIdentity nationalIdentity = (NationalIdentity) httpSession.getAttribute("nationalIdentity");
        AuthenticationHash authenticationHash = (AuthenticationHash) httpSession.getAttribute("authenticationHash");
        SmartIdAuthenticationResult authenticationResult = smartIdService.authenticate(nationalIdentity, authenticationHash);

        //if (authenticationResult.isValid()) {
            httpSession.setAttribute(AUTHENTICATION_RESULT_KEY, authenticationResult);
        //}
        return authenticationResult;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SmartIdException.class)
    public Map<String, String> smartIdExceptionHandler(SmartIdException smartIdException) {
        Map<String, String> errorMap = new HashMap<>();
        if (smartIdException instanceof UserAccountNotFoundException) {
            errorMap.put("errorMessage", "Account not found!");
        }
        if (smartIdException instanceof UserRefusedException) {
            errorMap.put("errorMessage", "User cancelled Smart-ID request");
        }
        return errorMap;
    }

}
