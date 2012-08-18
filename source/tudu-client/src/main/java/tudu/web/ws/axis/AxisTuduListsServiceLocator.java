/**
 * AxisTuduListsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package tudu.web.ws.axis;

public class AxisTuduListsServiceLocator extends org.apache.axis.client.Service implements tudu.web.ws.axis.AxisTuduListsService {

    public AxisTuduListsServiceLocator() {
    }


    public AxisTuduListsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AxisTuduListsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TuduListsWebService
    private java.lang.String TuduListsWebService_address = "http://127.0.0.1:8080/tudu/ws/axis/TuduListsWebService";

    public java.lang.String getTuduListsWebServiceAddress() {
        return TuduListsWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TuduListsWebServiceWSDDServiceName = "TuduListsWebService";

    public java.lang.String getTuduListsWebServiceWSDDServiceName() {
        return TuduListsWebServiceWSDDServiceName;
    }

    public void setTuduListsWebServiceWSDDServiceName(java.lang.String name) {
        TuduListsWebServiceWSDDServiceName = name;
    }

    public tudu.web.ws.axis.AxisTuduLists getTuduListsWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TuduListsWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTuduListsWebService(endpoint);
    }

    public tudu.web.ws.axis.AxisTuduLists getTuduListsWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            tudu.web.ws.axis.TuduListsWebServiceSoapBindingStub _stub = new tudu.web.ws.axis.TuduListsWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTuduListsWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTuduListsWebServiceEndpointAddress(java.lang.String address) {
        TuduListsWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (tudu.web.ws.axis.AxisTuduLists.class.isAssignableFrom(serviceEndpointInterface)) {
                tudu.web.ws.axis.TuduListsWebServiceSoapBindingStub _stub = new tudu.web.ws.axis.TuduListsWebServiceSoapBindingStub(new java.net.URL(TuduListsWebService_address), this);
                _stub.setPortName(getTuduListsWebServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TuduListsWebService".equals(inputPortName)) {
            return getTuduListsWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://axis.ws.web.tudu", "AxisTuduListsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://axis.ws.web.tudu", "TuduListsWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TuduListsWebService".equals(portName)) {
            setTuduListsWebServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
