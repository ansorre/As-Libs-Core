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

package me.as.lib.core.extra;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public final class JavaScriptExtras
{
 // singleton
 private JavaScriptExtras(){}

 // . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .


 private static Boolean canDisableDeprecation=null;


 @SuppressWarnings("removal")
 public static ScriptEngine newJavaScriptEngine()
 {
  // all this is because Nashorn has been deprecated!
  synchronized (JavaScriptExtras.class)
  {
   if (canDisableDeprecation==null)
   {
    try
    {
     canDisableDeprecation=true;
     return newJavaScriptEngine();
    }
    catch (Throwable tr)
    {
     canDisableDeprecation=false;
    }
   }

   if (canDisableDeprecation)
    return new jdk.nashorn.api.scripting.NashornScriptEngineFactory().getScriptEngine("--no-deprecation-warning");
   else
    return new ScriptEngineManager().getEngineByName("nashorn");
  }
 }


/*
 public static void main(String args[])
 {
  try
  {
   newJavaScriptEngine().eval("print('Hei!')");
   newJavaScriptEngine().eval("print('Hello, World!')");
  }
  catch (Throwable tr)
  {
   throw new RuntimeException(tr);
  }
 }
*/


}
