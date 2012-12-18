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


package com.mchange.v2.csv.junit;

import junit.framework.TestCase;
import com.mchange.v2.csv.FastCsvUtils;

public class FastCsvUtilsJUnitTestCase extends TestCase
{
    final static String[] SIMPLE = {"these", "are", "some", "simple", "strings"};
    final static String[] HARDER = {"th,ese", "a\"re", "some", "\"harder\"", "s,t,r,i,n,g,s"};
    final static String[] MULTILINE = {"th\nese", "a\r\nre", "just", "w\"\rei\nrd\"", "s,t,r,i,n,g,s"};
    final static String[] BLANKS = {"this","array","contains","","","","three","blanks"};

    private void assertAllEqual(String msg, Object[] expected, Object[] found)
    {
	assertEquals(msg + " [array length mismatch]", expected.length, found.length);
	for (int i = 0, len = expected.length; i < len; ++i)
	    assertEquals(msg, expected[i],found[i]);
    }

    private void printArray(String[] chk)
    {
	for (int i = 0, len = chk.length; i < len; ++i)
	    System.err.println(chk[i]);
    }

    public void testSplitLine()
    {
	try
	    {
		String[] cannedParse1 = FastCsvUtils.splitRecord("these,are,some,simple,strings");
		assertAllEqual("SIMPLE (1): Canned parse should match array.", SIMPLE, cannedParse1);

		String[] cannedParse2 = FastCsvUtils.splitRecord("\"these\",\"are\",\"some\",\"simple\",\"strings");
		assertAllEqual("SIMPLE (2): Canned parse with some quoted words should match array.", SIMPLE, cannedParse2);

		String[] cannedParse3 = FastCsvUtils.splitRecord("\"these\"  ,  are\t, \"some\"\t,\"simple\",\"strings");
		assertAllEqual("SIMPLE (3): Unquoted whitespace at beginning or end shouldn't matter.", SIMPLE, cannedParse3);

		String cp4str = "\"th,ese\",\"a\"\"re\",some,\"\"\"harder\"\"\",\"s,t,r,i,n,g,s\"";
		String[] cannedParse4 = FastCsvUtils.splitRecord(cp4str);
		assertAllEqual("HARDER (4): Canned parse with some embedded commas and quotes should match array.", HARDER, cannedParse4);

		String cp5str = "     \t \"th\nese\"\t, \"a\r\nre\",  just\t,\"w\"\"\rei\nrd\"\"\",\"s,t,r,i,n,g,s\"          ";
		String[] cannedParse5 = FastCsvUtils.splitRecord(cp5str);
		assertAllEqual("MULTILINE (5): Canned parse with ignorable whitespace and  some embedded commas, quotes, and newlines should match array.", MULTILINE, cannedParse5);

		String cp6str = "this,array, \"contains\",,   ,\"\", \tthree, \"blanks\"";
		String[] cannedParse6 = FastCsvUtils.splitRecord(cp6str);
		assertAllEqual("BLANKS (6): Canned parse with a variety of empty fields.", BLANKS, cannedParse6);

	    }
	catch (Exception e)
	    {
		e.printStackTrace();
		fail( e.getMessage() );
	    }
    }

}
