package ru.itis.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.security.defails.UserDetailsImpl;


import java.util.HashMap;

@Controller
public class ProfileController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public ModelAndView view(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) return new ModelAndView("redirect:/signIn");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("user", userDetails.getUser());
        return new ModelAndView("profile", hashMap);

    }

}
