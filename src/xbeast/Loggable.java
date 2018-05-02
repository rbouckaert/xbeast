package xbeast;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import dr.inference.loggers.LogColumn;

/**
 * interface for items that can be logged through a Logger *
 */
public interface Loggable extends Identifiable {

    /**
     * write header information, e.g. labels of a parameter,
     * or Nexus tree preamble
     *
     * @param out log stream
     */
    default void init(PrintStream out) {
    	try {
	    	for (LogColumn c : getColumns()) {
	    		out.print(c.getLabel() + "\t");
	    	}
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: init(PrintStream) is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
    	} 
    }

    /**
     * log this sample for current state to PrintStream,
     * e.g. value of a parameter, list of parameters or Newick tree
     *
     * @param sample chain sample number
     * @param out     log stream
     */
    default void log(long sample, PrintStream out) {
    	try {
	    	for (LogColumn c : getColumns()) {
	    		out.print(c.getFormatted() + "\t");
	    	}
		} catch (StackOverflowError e) {
			System.err.println("Programmer error: log(PrintStream) is not implemented in class " + this.getClass().getName()+ "!");
			throw e;
    	} 
    }
    /**
    
     * close log. An end of log message can be left (as in End; for Nexus trees)
     *
     * @param out log stream
     */
    default void close(PrintStream out) {
    	// nothing to do
    }
    
    
	default LogColumn[] getColumns() {
		if (!(this instanceof beast.core.Loggable)) {
			throw new IllegalArgumentException("Programmer error: This object should implement the  getColumns() method or should implement beast.core.Loggable");			
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = null;
		try {
			ps = new PrintStream(baos, true, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		init(ps);
		String [] headers = new String(baos.toByteArray(), StandardCharsets.UTF_8).split("\t");	
		
		LogColumn[] cols = new LogColumn[headers.length];		
		for (int i = 0; i < headers.length; i++) {
			cols[i] = new LogColumnB2(headers[i], headers[i].length(), i, (beast.core.Loggable) this);
		}
		return cols;
	}
	
	class LogColumnB2 implements LogColumn  {
		String label;
		int minimumWidth;
		int index;
		beast.core.Loggable loggable;
		
		LogColumnB2(String label, int minimumWidth, int index, beast.core.Loggable loggable) {
			this.label = label;
			this.minimumWidth = minimumWidth;
			this.index = index;
			this.loggable = loggable;
		}
		
		@Override
		public void setLabel(String label) {
			this.label = label;					
		}

		@Override
		public String getLabel() {					
			return label;
		}

		@Override
		public void setMinimumWidth(int minimumWidth) {
			this.minimumWidth = minimumWidth;
		}

		@Override
		public int getMinimumWidth() {
			return minimumWidth;
		}

		@Override
		public String getFormatted() {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = null;
			try {
				ps = new PrintStream(baos, true, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loggable.log(0l, ps);
			String [] values = new String(baos.toByteArray(), StandardCharsets.UTF_8).split("\t");
			return values[index];
		}				
	};

}
