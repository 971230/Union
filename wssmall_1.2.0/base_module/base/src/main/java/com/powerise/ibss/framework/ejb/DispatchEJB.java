package com.powerise.ibss.framework.ejb;

import com.powerise.ibss.framework.DynamicDict;

import java.rmi.RemoteException;
import java.util.HashMap;


public interface DispatchEJB extends javax.ejb.EJBObject {
  public DynamicDict perform(DynamicDict p_dynamic_dict) throws RemoteException;
  public DynamicDict performWEBDynamicAction(DynamicDict p_dynamic_dict) throws RemoteException;
  public String performServlet(DynamicDict p_dynamic_dict) throws RemoteException;
   public String performServletDynamicAction(String p_input, HashMap p_staff_info) throws RemoteException;
}
