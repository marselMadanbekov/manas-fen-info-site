package com.example.manasfen.controllers.advices;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping()
public class CustomErrorHandler implements ErrorController {

    @RequestMapping("/access-denied")
    public String accessDeniedException() {
        return "error/access-denied";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,
                              @RequestParam(required = false) String message1,
                              Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute("javax.servlet.error.message");
        int code = 0;
        String message = "";
        String submessage = "";

        if (status != null) {
            code = Integer.parseInt(status.toString());
            if (code == HttpStatus.NOT_FOUND.value()) {
                message = "Страница не найдена!";
                submessage = "Запрашиваемая вами страница не найдена, убедитесь в правильности адреса и попробуйте еще раз.";
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                message = "Ошибка сервера!";
                submessage = "При обработке запроса возникли неполадки.";
            } else {
                message = "Неизвестная ошибка!";
            }
        } else {
            message = message1;
        }
        model.addAttribute("message", message);
        model.addAttribute("submessage", submessage);
        model.addAttribute("status", status);
        return "error/error";
    }
}
