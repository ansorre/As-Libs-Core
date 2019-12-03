/*
 * Copyright 2019 Antonio Sorrentini
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package me.as.lib.core.event;


import java.util.*;

import static me.as.lib.core.lang.ExceptionExtras.systemErrDeepCauseStackTrace;


/*

 // Listeners management

 protected BasicListenersManager listenersManager=new BasicListenersManager();

 public void add_xxx_Listener(_xxx_Listener listener)
 {listenersManager.addListener(listener);}

 public void remove_xxx_Listener(_xxx_Listener listener)
 {listenersManager.removeListener(listener);}

 Firer firer=new Firer()
 {public void foreachAction(EventListener listener, Object param)
  {((_xxx_Listener)listener)._Xxx_EventOccurred((_xxx_Event)param);}};

 public void fire_xxx_EventOccurred(_xxx_Event e)
 {listenersManager.foreachListener(firer, e);}

 // END - Listeners management

*/


public class BasicListenersManager
{
 protected LinkedList<EventListener> listeners=new LinkedList<>();

 public synchronized void addListener(EventListener listener)
 {
  if (!listeners.contains(listener)) listeners.add(listener);
 }

 public synchronized void removeListener(EventListener listener)
 {
  listeners.remove(listener);
 }


 public synchronized void removeAllListeners()
 {
  listeners.clear();
 }


 public synchronized boolean areThereListeners()
 {
  return (listeners.size()>0);
 }

 public synchronized int getListenersCount()
 {
  return listeners.size();
 }

 public synchronized Iterator getListeners()
 {
  return listeners.iterator();
 }

 @SuppressWarnings("unchecked")
 public synchronized List<EventListener> getListenersList()
 {
  return (List<EventListener>)listeners.clone();
 }


 public synchronized EventListener[] getListenersArray()
 {
  return listeners.toArray(new EventListener[]{});
 }






 // returns the same number that getListenersCount() would
 public int foreachListener(Firer firer, Object param)
 {
  LinkedList al;

  synchronized (this)
  {
   // we need a copy because a listener could remove from
   // the list during the hanfling of the event!
   al=(LinkedList)listeners.clone();
  }

  int t, len=al.size();

  if (len>0)
  {
   for (t=0;t<len;t++)
   {
    try
    {
     firer.foreachAction((EventListener)al.get(t), param);
    }
    catch (Throwable tr)
    {
     System.err.println("EXCEPTION WHILE FIRING EVENT IN BasicListenersManager:");
     systemErrDeepCauseStackTrace(tr);
    }
   }
  }

  return len;
 }

 // returns the answer from listenerThatMustAnswer
 public Object foreachListener(AnsweredFirer firer, Object param, EventListener listenerThatMustAnswer)
 {
  LinkedList al;

  synchronized (this)
  {
   // we need a copy because a listener could remove from
   // the list during the hanfling of the event!
   al=(LinkedList)listeners.clone();
  }

  Object res=null;
  int t, len=al.size();

  if (len>0)
  {
   EventListener currentFiredListener;
   Object tmpRes;

   for (t=0;t<len;t++)
   {
    currentFiredListener=(EventListener)al.get(t);
    tmpRes=firer.foreachAction(currentFiredListener, param);
    if (listenerThatMustAnswer!=null && currentFiredListener.equals(listenerThatMustAnswer))
    {
     res=tmpRes;
    }
   }
  }

  return res;
 }



}
