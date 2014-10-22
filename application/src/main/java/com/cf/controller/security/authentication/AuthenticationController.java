package com.cf.controller.security.authentication;

import com.cf.controller.core.spring.mvc.MimeTypes;
import com.cf.utils.CustomMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private CustomMessageSource messageSource;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authManager;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MimeTypes.JSON, produces = MimeTypes.JSON)
    //@RolesAllowed("IS_AUTHENTICATED_FULLY")
    public AuthResult login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getLogin(),
                loginRequest.getPassword());
        //Create a session if the request doesn't have one and save login (username)
        request.getSession().setAttribute("login",loginRequest.getLogin());
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authManager.authenticate(token);

        //SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        return new AuthResult(HttpStatus.OK.value(), "Authenticated");
    }

    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MimeTypes.JSON)
    public AuthResult logout() {
        // remove session
        SecurityContextHolder.getContext().setAuthentication(null);
        return new AuthResult(HttpStatus.OK.value(), "Logged Out");
    }


    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public AuthResult loginFailedUsernameNotFound(HttpServletRequest request) {
        //Remove session
        SecurityContextHolder.getContext().setAuthentication(null);
        return new AuthResult(HttpStatus.UNAUTHORIZED.value(), messageSource.getMessage("login.validation.credentials.login", new Object[] {request.getSession().getAttribute("login")}, Locale.getDefault()));
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public AuthResult loginFailedWrongPassword() {
        //Remove session
        SecurityContextHolder.getContext().setAuthentication(null);
        return new AuthResult(HttpStatus.UNAUTHORIZED.value(), messageSource.getMessage("login.validation.credentials.password", null, Locale.getDefault()));
    }

}
