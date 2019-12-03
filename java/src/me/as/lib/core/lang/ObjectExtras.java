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


import java.util.Map;

import static me.as.lib.core.lang.ArrayExtras.select;


public class ObjectExtras
{
 // singleton
 private ObjectExtras(){}




 private static boolean basic_areEqual(Object obj1, Object obj2)
 {
  boolean res=(obj1==null && obj2==null);

  if (!res && (obj1!=null && obj2!=null))
  {
   res=(obj1==obj2);
   if (!res) res=obj1.equals(obj2);
  }

  return res;
 }



 public static boolean areEqual(Object a, Object b)
 {
  boolean res=basic_areEqual(a, b);

  if (!res && (a instanceof Map) && (b instanceof Map))
  {
   Map am=(Map)a;
   Map bm=(Map)b;

   if (am.size()==bm.size())
   {
    res=true;

    for (Object o : am.keySet())
    {
     if (!areEqual(am.get(o), bm.get(o)))
     {
      res=false;
      break;
     }
    }
   }

   return res;
  }

  if (!res)
  {
   boolean uncomputable=false;

   if (a!=null && b!=null)
   {
    Class c=a.getClass();
    String cn=c.getName();

    if (StringExtras.areEqual(cn, b.getClass().getName()))
    {
     if (cn.startsWith("["))
     {
      switch (select(Types.classes, c))
      {
       case Types._bytes   :res=ArrayExtras.areEqual((byte[])a, (byte[])b);break;
       case Types._shorts  :res=ArrayExtras.areEqual((short[])a, (short[])b);break;
       case Types._ints    :res=ArrayExtras.areEqual((int[])a, (int[])b);break;
       case Types._longs   :res=ArrayExtras.areEqual((long[])a, (long[])b);break;
       case Types._chars   :res=ArrayExtras.areEqual((char[])a, (char[])b);break;
       case Types._floats  :res=ArrayExtras.areEqual((float[])a, (float[])b);break;
       case Types._doubles :res=ArrayExtras.areEqual((double[])a, (double[])b);break;
       case Types._booleans:res=ArrayExtras.areEqual((boolean[])a, (boolean[])b);break;
       case Types._strings :res=StringExtras.areEqual((String[])a, (String[])b);break;
       default             :
        {
         if (cn.startsWith("[[")) uncomputable=true;
         else
         {
          int t, len=ArrayExtras.length(a);

          if (len==ArrayExtras.length(b))
          {
           Object aa[]=(Object[])a;
           Object bb[]=(Object[])b;

           res=true;

           for (t=0;t<len && res;t++)
           {
            res=areEqual(aa[t], bb[t]);
           }
          }
         }
        }
      }
     }
    }

    if (uncomputable)
    {
     throw new RuntimeException("ERROR: unable to compute areEqual for objects of class '"+cn+"'");
    }
   }
  }

  return res;
 }





}




