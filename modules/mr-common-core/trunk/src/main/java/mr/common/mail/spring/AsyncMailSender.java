package mr.common.mail.spring;

import java.io.InputStream;
import java.util.Properties;

import javax.activation.FileTypeMap;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;


/**
 * Wrapper de {@link org.springframework.mail.javamail.JavaMailSenderImpl JavaMailSenderImpl},
 * que envía mails de forma asincrónica, creando un hilo para cada submit.<br/>
 * El objeto <i>taskExecutor -> threadGroup</i> maneja las excepciones
 * {@link org.springframework.mail.MailException MailException} que pudieran producirse
 * (ver {@link ThreadGroup} y {@link org.springframework.core.task.TaskExecutor TaskExecutor}).<br/>
 * Puede usarse {@link mr.common.exception.LogErrorThreadGroupHandler LogErrorThreadGroupHandler},
 * que loguea las excepciones como <i>ERROR</i> con log4j.<br/>
 * Ejemplo de configuración en Spring:
 * <pre>
 * 	&lt;bean id="mailSender" class="mr.common.mail.spring.AsyncMailSender"&gt;
 * 		&lt;property name="taskExecutor" ref="mailPoolTasksExecutor" /&gt;
 * 		&lt;property name="host" value="${mail.host}" /&gt;
 * 		&lt;property name="defaultEncoding" value="UTF-8" /&gt;
 * 		&lt;!-- Uncomment if you need to authenticate with your SMTP Server --&gt;
 * 		&lt;property name="username" value="${mail.username}" /&gt;
 * 		&lt;property name="password" value="${mail.password}" /&gt;
 * 		&lt;property name="javaMailProperties"&gt;
 * 			&lt;props&gt;
 * 				&lt;!--  Si se activa una cuenta con SSL (ej. Gmail) usar los siguientes campos  --&gt;
 * 				&lt;prop key="mail.smtp.auth"&gt;true&lt;/prop&gt;
 * 				&lt;prop key="mail.smtp.starttls.enable"&gt;true&lt;/prop&gt; 
 * 				&lt;prop key="mail.smtp.port"&gt;${mail.smtp.port}&lt;/prop&gt;
 * 				&lt;prop key="mail.smtp.host"&gt;${mail.host}&lt;/prop&gt;
 * 			&lt;/props&gt;
 * 		&lt;/property&gt;
 * 	&lt;/bean&gt;
 * 
 * 	&lt;!-- Thread pool definition for tasks to be executed asynchronously. --&gt;
 * 	&lt;bean id="mailPoolTasksExecutor"
 * 		class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor"
 * 		p:corePoolSize="5" p:maxPoolSize="10" p:queueCapacity="100"
 * 		p:waitForTasksToCompleteOnShutdown="true"&gt;
 * 
 * 			&lt;property name="threadGroup" ref="logErrorMailHandler" /&gt;
 * 	&lt;/bean&gt;
 * 
 * 	&lt;bean id="logErrorMailHandler" class="mr.common.exception.LogErrorThreadGroupHandler" /&gt;
 * </pre>
 * 
 * @author Mariano Ruiz
 */
public class AsyncMailSender implements JavaMailSender {

	private static final Log logger = LogFactory.getLog(AsyncMailSender.class);

	private JavaMailSenderImpl mailSender;

	private TaskExecutor taskExecutor;

	public AsyncMailSender() {
		mailSender = new JavaMailSenderImpl();
	}


	public void send(SimpleMailMessage simpleMailMessage) throws MailException {
		taskExecutor.execute(new AsyncMailTask(simpleMailMessage));
	}

	public void send(SimpleMailMessage[] simpleMailMessages)
			throws MailException {
		taskExecutor.execute(new AsyncMailTask(simpleMailMessages));
	}

	public void send(MimeMessage mimeMessage) throws MailException {
		taskExecutor.execute(new AsyncMailTask(mimeMessage));
		
	}

	public void send(MimeMessage[] mimeMessages) throws MailException {
		taskExecutor.execute(new AsyncMailTask(mimeMessages));
	}

	public void send(MimeMessagePreparator mimeMessagePreparator)
			throws MailException {
		taskExecutor.execute(new AsyncMailTask(mimeMessagePreparator));
	}

	public void send(MimeMessagePreparator[] mimeMessagesPreparator)
			throws MailException {
		taskExecutor.execute(new AsyncMailTask(mimeMessagesPreparator));
	}

	public MimeMessage createMimeMessage() {
		return mailSender.createMimeMessage();
	}

	public MimeMessage createMimeMessage(InputStream inputStream)
			throws MailException {
		return mailSender.createMimeMessage(inputStream);
	}


	private class AsyncMailTask implements Runnable {

		private SimpleMailMessage[] messages = null;
		private MimeMessage[] mimeMessages = null;
		private MimeMessagePreparator[] mimeMessagesPreparator = null;

		private AsyncMailTask(SimpleMailMessage message) {
			this.messages = new SimpleMailMessage[] {message};
		}
		private AsyncMailTask(SimpleMailMessage[] messages) {
			this.messages = messages;
		}

		private AsyncMailTask(MimeMessage message) {
			this.mimeMessages = new MimeMessage[] {message};
		}
		private AsyncMailTask(MimeMessage[] messages) {
			this.mimeMessages = messages;
		}

		private AsyncMailTask(MimeMessagePreparator message) {
			this.mimeMessagesPreparator = new MimeMessagePreparator[] {message};
		}
		private AsyncMailTask(MimeMessagePreparator[] messages) {
			this.mimeMessagesPreparator = messages;
		}

		public void run() {
			if(messages!=null) {
				for(SimpleMailMessage m : messages) {
					mailSender.send(m);
				}
			} else if(mimeMessages!=null) {
				for(MimeMessage m : mimeMessages) {
					mailSender.send(m);
				}
			} else {
				for(MimeMessagePreparator m : mimeMessagesPreparator) {
					mailSender.send(m);
				}
			}
			logger.debug("mail sended");
		}
	}


	/*
	 * Getters and Setters.
	 */
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	public void setJavaMailProperties(Properties javaMailProperties) {
		mailSender.setJavaMailProperties(javaMailProperties);
	}
	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	public void setSession(Session session) {
		mailSender.setSession(session);
	}
	public void setProtocol(String protocol) {
		mailSender.setProtocol(protocol);
	}
	public void setHost(String host) {
		mailSender.setHost(host);
	}
	public void setPort(int port) {
		mailSender.setPort(port);
	}
	public void setUsername(String username) {
		mailSender.setUsername(username);
	}
	public void setPassword(String password) {
		mailSender.setPassword(password);
	}
	public void setDefaultEncoding(String defaultEncoding) {
		mailSender.setDefaultEncoding(defaultEncoding);
	}
	public void setDefaultFileTypeMap(FileTypeMap defaultFileTypeMap) {
		mailSender.setDefaultFileTypeMap(defaultFileTypeMap);
	}
	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}
	public Properties getJavaMailProperties() {
		return mailSender.getJavaMailProperties();
	}
	public Session getSession() {
		return mailSender.getSession();
	}
	public String getProtocol() {
		return mailSender.getProtocol();
	}
	public String getHost() {
		return mailSender.getHost();
	}
	public int getPort() {
		return mailSender.getPort();
	}
	public String getUsername() {
		return mailSender.getUsername();
	}
	public String getPassword() {
		return mailSender.getPassword();
	}
	public String getDefaultEncoding() {
		return mailSender.getDefaultEncoding();
	}
	public FileTypeMap getDefaultFileTypeMap() {
		return mailSender.getDefaultFileTypeMap();
	}
}
