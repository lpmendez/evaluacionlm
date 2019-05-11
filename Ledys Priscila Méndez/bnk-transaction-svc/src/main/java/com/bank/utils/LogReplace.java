package com.bank.utils;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

public class LogReplace extends ConsoleAppender<ILoggingEvent>{

	@Override
	protected void append(ILoggingEvent eventObject) {
		LoggingEvent ev = (LoggingEvent) eventObject;
		
		Object[] data = ev.getArgumentArray();
		
		if(data != null) {
			for(int i=0; i < data.length; i++) {
				data[i] = data[i].toString().replace("\n", "")
						.replace("\r", "")
						.replace("%0d", "")
						.replace("%0D", "")
						.replace("%0a", "")
						.replace("%0A", "");
			}						
			
		}
		super.append(ev);
	}

}