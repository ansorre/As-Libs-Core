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

package me.as.lib.core.lang;


import java.lang.ref.*;


public class SmartWeakReference<R> extends WeakReference<R>
{

 public SmartWeakReference(R referent)
 {
  super(referent);
 }


 public SmartWeakReference(R referent, ReferenceQueue<? super R> q)
 {
  super(referent, q);
 }


 public boolean equals(Object obj)
 {
  boolean res=(this==obj);

  if (!res)
  {
   res=(obj==null && get()==null);

   if (!res)
   {
    if (obj instanceof Reference)
    {
     res=ObjectExtras.areEqual(get(), ((Reference)obj).get());
    }
    else
    {
     res=ObjectExtras.areEqual(get(), obj);
    }
   }
  }

  return res;
 }


}
