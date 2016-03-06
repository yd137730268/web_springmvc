package org.dy.springmvc.util;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

public class JsonView extends AbstractView {
	private final Logger logger = LoggerFactory.getLogger(JsonView.class);

	@Override
	protected void renderMergedOutputModel(Map<String, Object> arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		JSONObject jsonResult = new JSONObject();
		for (Map.Entry<String, Object> entry : arg0.entrySet()) {
			String key = entry.getKey();
			jsonResult.put(key, arg0.get(key));
		}
		PrintWriter out = arg2.getWriter();
		logger.info("json string : " + jsonResult.toString());
		out.print(jsonResult.toString());
	}

}
