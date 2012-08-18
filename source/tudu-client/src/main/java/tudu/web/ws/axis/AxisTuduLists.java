/**
 * AxisTuduLists.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package tudu.web.ws.axis;

public interface AxisTuduLists extends java.rmi.Remote {
    public tudu.web.ws.bean.WsTodoList[] getAllTodoLists() throws java.rmi.RemoteException;
    public tudu.web.ws.bean.WsTodo[] getTodosByTodoList(java.lang.String in0) throws java.rmi.RemoteException;
}
