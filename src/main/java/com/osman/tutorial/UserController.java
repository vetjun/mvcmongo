package com.osman.tutorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osman.dao.UserRepository;
import com.osman.model.User;
import com.osman.rest.MultipleUserResponse;
import com.osman.rest.RestUserResponse;
import com.osman.rest.SingleUserResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    private UserRepository userRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Default Home REST page. The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "status";
	}
	/*This Controller maps for getting all users for initial table and refreshing table*/
	@RequestMapping(value="/issuers", method=RequestMethod.GET)
	@ResponseBody
	public MultipleUserResponse getAllIssuers() {
		logger.info("Inside getAllIssuers() method...");

		List<User> allIssuers = userRepository.getAllIssuers();
		MultipleUserResponse extResp = new MultipleUserResponse(true, allIssuers);
		
		return extResp;
	}
	
	@RequestMapping(value="/issuer/{ticker}", method=RequestMethod.GET)
	@ResponseBody
	public SingleUserResponse getIssuerByTicker(@PathVariable("id") String ticker) {
		User myIssuer = userRepository.getIssuerByTicker(ticker);
		
		if (myIssuer != null) {
			logger.info("Inside getIssuerByTicker, returned: " + myIssuer.toString());
		} else {
			logger.info("Inside getIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
		}
		
		SingleUserResponse extResp = new SingleUserResponse(true, myIssuer);
		return extResp; 
	}
    /*This controller deletes User with user id.*/
	@RequestMapping(value="/issuer/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public RestUserResponse deleteIssuerByTicker(@PathVariable("id") String ticker) {
		RestUserResponse extResp;
		
		User myIssuer = userRepository.deleteIssuer(ticker);
		
		if (myIssuer != null) {
			logger.info("Inside deleteIssuerByTicker, deleted: " + myIssuer.toString());
			extResp = new RestUserResponse(true, "Successfully deleted Issuer: " + myIssuer.toString());
		} else {
			logger.info("Inside deleteIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
			extResp = new RestUserResponse(false, "Failed to delete ticker: " + ticker);
		}
		
		return extResp;
	}
	/*This Controller updates User with user id and Autowired User class */
	@RequestMapping(value="/issuer/update/{id}", method=RequestMethod.POST)
	@ResponseBody
	public RestUserResponse updateIssuerByTicker(@ModelAttribute("issuer") User issuer,@PathVariable("id") String ticker) {
		RestUserResponse extResp;
		
		User myIssuer = userRepository.updateIssuer(ticker, issuer);
		
		if (myIssuer != null) {
			logger.info("Inside updateIssuerByTicker, updated: " + myIssuer.toString());
			extResp = new RestUserResponse(true, "Successfully updated Issuer: " + myIssuer.toString());
		} else {
			logger.info("Inside updateIssuerByTicker, ticker: " + issuer.getId() + ", NOT FOUND!");
			extResp = new RestUserResponse(false, "Failed to update ticker: " + issuer.getId());
		}

		return extResp;
	}
/*This controller adds User with Autowired User class and captcha response*/
	@RequestMapping(value="/issuer/addIssuer", method=RequestMethod.POST)
	@ResponseBody
	public RestUserResponse addIssuer(HttpServletRequest request,@ModelAttribute("issuer") User issuer) {
		RestUserResponse extResp = null;
		String secretKey="6LeXqAwUAAAAACxxdxqoFNhMvwHlcpEX-Z8nrWSg";
		String captcha=request.getParameter("g-recaptcha-response");
		boolean captcharesult=false;
		String error="";
		try {
			if (issuer.getName() != null && issuer.getName().length() > 0 && issuer.getSurname() != null  && issuer.getSurname().length() > 0 && request.getParameter("g-recaptcha-response") !=""){
				JSONObject json = readJsonFromUrl("https://www.google.com/recaptcha/api/siteverify?secret="+secretKey+"&response="+captcha);
				System.out.println(json.get("success"));
				captcharesult=(Boolean) json.get("success");
				if(captcharesult == true){
					logger.info("Inside addIssuer, adding: " + issuer.toString());
					userRepository.addIssuer(issuer);
					extResp = new RestUserResponse(true, "Successfully added User: " + issuer.getName());
				}
				else{	if(captcharesult != true){error+="<p style='color:blue'>Please complete your captcha action again.</p>";}}
			}
			else{
				
				if(issuer.getName()==null || issuer.getName().length() == 0){error+="<p style='color:red'>Please enter your name.</p>";}
				if(issuer.getSurname()==null || issuer.getSurname().length() == 0){error+="<p style='color:red'>Please enter your Surname.</p>";}
				if(request.getParameter("g-recaptcha-response") ==""){error+="<p style='color:red'>Please complete your captcha action.</p>";}
			
				logger.info("Failed to insert...");
				extResp = new RestUserResponse(false, error);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return extResp;
		
	}
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	 public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
}

