/*
 * package com.anu.mongo.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.web.error.ErrorAttributeOptions; import
 * org.springframework.boot.web.servlet.error.ErrorAttributes; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.servlet.ModelAndView; import
 * org.springframework.web.context.request.WebRequest;
 * 
 * import java.util.Map;
 * 
 * @Controller public class CustomErrorController {
 * 
 * private final ErrorAttributes errorAttributes;
 * 
 * @Autowired public CustomErrorController(ErrorAttributes errorAttributes) {
 * this.errorAttributes = errorAttributes; }
 * 
 * @RequestMapping("/custom-error") public ModelAndView
 * customHandleError(WebRequest request) { Map<String, Object>
 * errorAttributesMap = errorAttributes.getErrorAttributes(request,
 * ErrorAttributeOptions.defaults()); HttpStatus status =
 * HttpStatus.valueOf((int) errorAttributesMap.get("status")); ModelAndView
 * modelAndView = new ModelAndView("error", "errorAttributes",
 * errorAttributesMap); modelAndView.setStatus(status); return modelAndView; } }
 */