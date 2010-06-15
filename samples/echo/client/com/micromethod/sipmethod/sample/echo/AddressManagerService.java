
package com.micromethod.sipmethod.sample.echo;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "AddressManagerService", targetNamespace = "http://echo.sample.sipmethod.micromethod.com/", wsdlLocation = "http://localhost:8080/echo/services/manager?wsdl")
public class AddressManagerService
    extends Service
{

    private final static URL ADDRESSMANAGERSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/echo/services/manager?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ADDRESSMANAGERSERVICE_WSDL_LOCATION = url;
    }

    public AddressManagerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AddressManagerService() {
        super(ADDRESSMANAGERSERVICE_WSDL_LOCATION, new QName("http://echo.sample.sipmethod.micromethod.com/", "AddressManagerService"));
    }

    /**
     * 
     * @return
     *     returns AddressManager
     */
    @WebEndpoint(name = "AddressManagerPort")
    public AddressManager getAddressManagerPort() {
        return (AddressManager)super.getPort(new QName("http://echo.sample.sipmethod.micromethod.com/", "AddressManagerPort"), AddressManager.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AddressManager
     */
    @WebEndpoint(name = "AddressManagerPort")
    public AddressManager getAddressManagerPort(WebServiceFeature... features) {
        return (AddressManager)super.getPort(new QName("http://echo.sample.sipmethod.micromethod.com/", "AddressManagerPort"), AddressManager.class, features);
    }

}