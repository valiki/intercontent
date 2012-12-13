package net.sunnycore.intercontent.presentation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * throws and exception to test error pages
 * 
 * @author vshukaila
 * 
 */
@Controller
public class ErrorGeneratingController {

	private Logger logger = Logger.getLogger(ErrorGeneratingController.class);
	
	@RequestMapping(value = "/hello.do", method = RequestMethod.GET)
    public String hello() {
        return "t_main";
    }
	
    @RequestMapping(value = "/exception.do", method = RequestMethod.GET)
    public void throwException() throws Exception {
        throw new Exception("Test Exception");
    }

    @RequestMapping(value = "/runtime-exception.do", method = RequestMethod.GET)
    public void throwRuntimeException() {
        throw new RuntimeException();
    }
    
}
