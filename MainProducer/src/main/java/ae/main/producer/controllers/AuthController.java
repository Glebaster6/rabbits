package ae.main.producer.controllers;

import ae.main.producer.models.UserData;
import ae.main.producer.services.authentication.AuthenticationService;
import main.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private AuthenticationService service;

    @GetMapping("/login")
    public String login(@ModelAttribute("model") ModelMap model, Authentication authentication,
                        @RequestParam Optional<String> error) {
        if (authentication != null) {
            return "redirect:/";
        }
        model.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Authentication authentication) {
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }


    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null) {
            UserData user = service.getUserByAuthentication(authentication);
            switch(user.getRole()) {
                case "ADMIN":{
                    return "redirect:/admin/main";
                }
                case "EVALUATOR":{
                    return "redirect:/evaluation/" + user.getFacilityId();
                }
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/sign_up")
    public String getSignUpPage(){
        return "sign_up";
    }

    @PostMapping("sign_up")
    public String registerNewFacility(RegistrationDto registrationDto){
        System.out.println(registrationDto.getFacilityName());
        service.register(registrationDto);
        return "redirect:/login";
    }
}
