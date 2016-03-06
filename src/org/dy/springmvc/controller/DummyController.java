package org.dy.springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dy.springmvc.service.DummyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "dummy")
public class DummyController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(DummyController.class);

	@Autowired
	private DummyService dummyService;

	private static final String DUMY_PAGE = "/dummy/dummy.jsp";

	@RequestMapping(value = "showDummyPage.do")
	@ResponseBody
	public ModelAndView showDumyPage(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("showDumyPage...");
		ModelAndView mav = getModuleAndView(params, request);
		mav.setViewName(DUMY_PAGE);
		return mav;
	}

	@RequestMapping(value = "getDummyData.do")
	@ResponseBody
	public ModelAndView getDummyData(@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getDumyData...");
		ModelAndView mav = getModuleAndView(params, request);
		try {
			mav.addObject("dumyData", dummyService.dummyService());
		} catch (Exception e) {
			logger.error("showDumyPage error.", e);
		}
		return mav;
	}
}
