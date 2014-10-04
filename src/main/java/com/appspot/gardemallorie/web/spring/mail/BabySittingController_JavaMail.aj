package com.appspot.gardemallorie.web.spring.mail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.appspot.gardemallorie.domain.BabySitter;
import com.appspot.gardemallorie.domain.BabySitting;
import com.appspot.gardemallorie.web.BabySittingController;

privileged aspect BabySittingController_JavaMail {
    
	declare precedence: com.appspot.gardemallorie.web.BabySittingController_Roo_Controller;

	@Autowired
	private transient JavaMailSender BabySittingController.javaMailSender;
	
	@Autowired
	@Qualifier("createBabySittingMailMessage")
	private transient SimpleMailMessage BabySittingController.createMailMessage;
	
	@Autowired
	@Qualifier("updateBabySittingMailMessage")
	private transient SimpleMailMessage BabySittingController.updateMailMessage;
	
	String around(BabySittingController babySittingController, BabySitting babySitting, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest):
        (
    		execution( public String BabySittingController.create(BabySitting, BindingResult, Model, HttpServletRequest) ) ||
    		execution( public String BabySittingController.update(BabySitting, BindingResult, Model, HttpServletRequest) )
		)
		&& target(babySittingController)
        && args(babySitting, bindingResult, uiModel, httpServletRequest)
    {
		
    	Set<BabySitter> babySitters = new HashSet<BabySitter>();
    	Locale locale = httpServletRequest.getLocale();
		SimpleMailMessage templateMailMessage = babySittingController.createMailMessage;
    	List<String> to = new ArrayList<String>();

    	if (babySitting.getId() != null) {
    		
    		templateMailMessage = babySittingController.updateMailMessage;

    		BabySitting actualBabySitting = BabySitting.findBabySitting(babySitting.getId());
        	babySitters.add(actualBabySitting.getBabySitter());
        	babySitters.add(actualBabySitting.getBack());
        	babySitters.add(actualBabySitting.getGo());
    	}

		String result = proceed(babySittingController, babySitting, bindingResult, uiModel, httpServletRequest);

		babySitters.add(babySitting.getBabySitter());
    	babySitters.add(babySitting.getBack());
    	babySitters.add(babySitting.getGo());

    	for (BabySitter b : babySitters) {
    		if (b != null && b.isNotification() && b.getEmail() != null) {
    			to.add(b.getEmail());
    		}
    	}

    	if (to.size() > 0) {
    		
/*	        SimpleMailMessage mailMessage = new SimpleMailMessage(templateMailMessage);
	        mailMessage.setTo(to.toArray(new String[to.size()]));
	        StringBuilder text = new StringBuilder();
	        text.append("Date :\t\t\t").append(new SimpleDateFormat("EEE dd/MM/yyyy", locale).format(babySitting.getDay()));
	        text.append("\r\nBaby-sitter :\t\t").append(babySitting.getBabySitter().getFirstName());
	        text.append("\r\nDébut prévu :\t");
	        if (babySitting.getPlannedBeginning() != null) {
	        	text.append(new SimpleDateFormat("HH:mm", locale).format(babySitting.getPlannedBeginning()));
	        }
	        text.append("\r\nAller :\t\t\t").append(babySitting.getGo().getFirstName());
	        text.append("\r\nFin prévue :\t\t");
	        if (babySitting.getPlannedEnd() != null) {
	        	text.append(new SimpleDateFormat("HH:mm", locale).format(babySitting.getPlannedEnd()));
	        }
	        text.append("\r\nRetour :\t\t").append(babySitting.getBack().getFirstName());
	        text.append("\r\nCommentaire :\t").append(babySitting.getComment());
	        text.append("\r\nLieu :\t\t\t").append(babySitting.getLocation().getName());
	        mailMessage.setText(text.toString());*/
			babySittingController.javaMailSender.send(new MimeMessagePreparatorImpl(babySitting, locale, templateMailMessage, to));
    	}

    	return result;
	}
	
	static class MimeMessagePreparatorImpl implements MimeMessagePreparator {
		
		private BabySitting babySitting;
		private Locale locale;
		private SimpleMailMessage templateMailMessage;
		private List<String> to;
		
		public MimeMessagePreparatorImpl(BabySitting babySitting, Locale locale, SimpleMailMessage templateMailMessage, List<String> to) {
			super();
			this.babySitting = babySitting;
			this.locale = locale;
			this.templateMailMessage = templateMailMessage;
			this.to = to;
		}

		@Override
		public void prepare(MimeMessage mimeMessage) throws Exception {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
    		mimeMessageHelper.setCc(templateMailMessage.getCc());
    		mimeMessageHelper.setFrom(templateMailMessage.getFrom());
    		mimeMessageHelper.setSubject(templateMailMessage.getSubject());
    		String text = String.format(locale, templateMailMessage.getText(),
    				babySitting.getId(),
    				babySitting.getDay(),
    				babySitting.getBabySitter().getFirstName(),
    				babySitting.getPlannedBeginning(),
    				babySitting.getGo().getFirstName(),
    				babySitting.getPlannedEnd(),
    				babySitting.getBack().getFirstName(),
    				babySitting.getComment(),
    				babySitting.getLocation().getName());
    		mimeMessageHelper.setText(text, true);
    		mimeMessageHelper.setTo(to.toArray(new String[to.size()]));
		}
		
	}

}
