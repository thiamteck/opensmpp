package org.smpp.smscsim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smpp.debug.Debug;

/**
 * Implementation of <code>Debug</code> interface which uses SLF4J
 * logger
 *
 * @see Debug
 * @see org.smpp.SmppObject
 *
 * @author Sverker Abrahamsson, LimeTransit AB, thiamteck
 * @version $Revision: 1.1 $
 */

public class Slf4jLoggerDebug implements Debug {
	private Logger logger;
	private boolean active = false;
	private int indent = 0;

	public Slf4jLoggerDebug(String category) {
		// get logger instance
		logger = LoggerFactory.getLogger(category);
	}

	public Slf4jLoggerDebug(Logger logger) {
		this.logger = logger;
	}

	public void enter(int group, Object from, String name) {
		enter(from, name);
	}

	public void enter(Object from, String name) {
		if (active ) {
			logger.debug(getDelimiter(true, from, name));
			indent++;
		}
	}

	public void write(int group, String msg) {
		write(msg);
	}

	public void write(String msg) {
		if (active ) {
			logger.debug(getIndent() + " " + msg);
		}
	}

	public void exit(int group, Object from) {
		exit(from);
	}

	public void exit(Object from) {
		if (active) {
			indent--;
			if (indent < 0) {
				// it's your fault :-)
				indent = 0;
			}
			logger.debug(getDelimiter(false, from, ""));
		}
	}

	public void activate() {
		active = true;
	}
	public void activate(int group) {
	}
	public void deactivate() {
		active = false;
	}
	public void deactivate(int group) {
	}

	public boolean active(int group) {
		return true;
	}

	private String getDelimiter(boolean start, Object from, String name) {
		String indentStr = getIndent();
		if (start) {
			indentStr += "-> ";
		} else {
			indentStr += "<- ";
		}
		return indentStr + from.toString() + (name == "" ? "" : " " + name);
	}

	private String getIndent() {
		String result = new String("");
		for (int i = 0; i < indent; i++) {
			result += "  ";
		}
		return result;
	}
}