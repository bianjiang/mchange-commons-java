/*
 * Distributed as part of mchange-commons-java v.0.2.3.3
 *
 * Copyright (C) 2012 Machinery For Change, Inc.
 *
 * Author: Steve Waldman <swaldman@mchange.com>
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 2.1, as 
 * published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; see the file LICENSE.  If not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */


package com.mchange.util.impl;

import java.util.Hashtable;
import com.mchange.util.ObjectCache;

public abstract class NoGCObjectCache implements ObjectCache
{
  Hashtable store = new Hashtable();
  
  public Object find(Object key) throws Exception
    {
      Object out = store.get(key);
      if (out == null || isDirty(key, out))
	{
	  out = createFromKey(key);
	  store.put(key, out);
	}
      return out;
    }

  protected boolean isDirty(Object key, Object cached)
    {return false;}

  protected abstract Object createFromKey(Object key) throws Exception;
}
