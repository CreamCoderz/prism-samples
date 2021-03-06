package com.voxeo.sipmethod.sample.xmppclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;


import com.voxeo.servlet.xmpp.XmppFactory;
import com.voxeo.servlet.xmpp.XmppServlet;
import com.voxeo.servlet.xmpp.XmppSession;

/**
 * this servlet make a xmpp client by using xmpp servlet technology.
 * 
 * @author zhuwillie
 */
public class XMPPClientHTTPServlet extends HttpServlet {
  private static final long serialVersionUID = 6122533723019935575L;

  private XmppFactory _xmppFactory;

  private Map<String, XmppSession> _sessions;

  @Override
  public void init() throws ServletException {
    super.init();
    // Get a reference to the XMPPFactory.
    _xmppFactory = (XmppFactory) getServletContext().getAttribute(XmppServlet.XMPP_FACTORY);

    _sessions = new ConcurrentHashMap<String, XmppSession>();
    getServletContext().setAttribute("SessionMap", _sessions);
  }

  /**
   * Delegate to doPost
   */
  @Override
  public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
      IOException {
    doPost(request, response);
  }

  /**
   * 
   */
  @Override
  public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
      IOException {
    // you can get these info from request, here just use fixed value.
    // this account password:testxmppclient_1.
    String myXMPPAccount = "testxmppclient@gmail.com";
    // this account password:testxmppclient2_1
    String messageTo = "testxmppclient2@gmail.com";

    String message = "hello, this is XMPP servlet client.";

    // save this session in a map, so you can use this session to send
    // message in later HTTP requests.
    XmppSession clientSession = null;
    synchronized (myXMPPAccount.intern()) {
      clientSession = _sessions.get(myXMPPAccount);
      // if session is not created, create the session.
      if (clientSession == null) {
        clientSession = _xmppFactory.createSession(_xmppFactory.createJID("gmail.com"), "testxmppclient",
            "testxmppclient_1", null);
        // wait until session features negotiaged.
        synchronized (clientSession) {
          try {
            clientSession.wait(30000);
          }
          catch (InterruptedException e) {
            // ignore
          }
        }
        // send initial presence per RFC3921
        clientSession.createPresence(null, null, null).send();
      }
    }

    // send message.
    Element body = null;
    try {
      body = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createElement("body");
    }
    catch (DOMException e) {
      e.printStackTrace();
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    body.setTextContent(message);
    clientSession.createMessage(messageTo, (String)null, body).send();

    response.setContentType("text/html");
    final PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<body>");
    out.println("<head>");

    final String title = "xmppclient";
    out.println("<title>" + title + "</title>");
    out.println("</head>");
    out.println("<body bgcolor=\"white\">");

    out.println("<br><br>" + "Sent message to:" + "<br>");

    out.println(messageTo);
    out.print("<br>");

    out.println("</body>");
    out.println("</html>");
  }
}
