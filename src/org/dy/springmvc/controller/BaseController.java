package org.dy.springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dy.springmvc.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {
	protected ModelAndView getModuleAndView(Map<String, String> params,
			HttpServletRequest request) {
		ModelAndView mav = null;
		if (Constants.RESULT_TYPE_JSON.equalsIgnoreCase(params
				.get(Constants.RESULT_TYPE))) {
			mav = new ModelAndView(Constants.MODEL_VIEW_TYPE_JSON_VIEW);
		} else {
			mav = new ModelAndView();
		}
		return mav;
	}
}
