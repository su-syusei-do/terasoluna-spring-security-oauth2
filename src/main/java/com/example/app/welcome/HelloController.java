package com.example.app.welcome;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.oauth2.client.authentication.*;

import org.apache.commons.lang3.builder.*;
import org.springframework.security.core.context.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.client.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory
            .getLogger(HelloController.class);
    
    private final OAuth2AuthorizedClientService clientService;
    
    public HelloController(OAuth2AuthorizedClientSerivce clientService) {
        this.clientService = clientService;
    }

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        model.addAttribute("serverTime", formattedDate);

        return "welcome/home";
    }

    @RequestMapping(value = "/secured", method = {RequestMethod.GET, RequestMethod.POST})
    public String secured(Locale locale, Model model, OAuth2LoginAuthenticationToken token) {
        logger.info("/secured {}.", locale);

        model.addAttribute("token", ToStringBuilder.reflectionToString(token, ToStringStyle.MULTI_LINE_STYLE));

        SecurityContext context = SecurityContextHolder.getContext();
        model.addAttribute("context", ToStringBuilder.reflectionToString(context, ToStringStyle.MULTI_LINE_STYLE));
        
        if (clientService != null) {
            OAuth2AuthorizedClient authClient = clientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
            String tokenValue = authClient.getAccessToken().getTokenValue();
            String[] chunks = tokenValue.split("\\.");
            
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            
            model.addAttribute("accessToken", payload);
        }
        return "welcome/secured";
    }
}
