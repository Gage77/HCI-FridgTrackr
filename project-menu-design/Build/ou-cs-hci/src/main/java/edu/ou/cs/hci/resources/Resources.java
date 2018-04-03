//******************************************************************************
// Copyright (C) 2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  6 19:40:35 2018 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20180206 [weaver]:	Original file (for CS course homeworks).
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.hci.resources;

//import java.lang.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;

//******************************************************************************

/**
 * The <CODE>Resources</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Resources
{
	//**********************************************************************
	// Public Class Methods (Convenience Methods)
	//**********************************************************************

	// Slurps the lines in a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "docs/names.txt" to
	// slurp the lines in the file "edu/ou/cs/hci/resources/docs/names.txt".
	public static ArrayList<String>	getLines(String filename)
	{
		return getLines(getResource(filename));
	}

	// Loads the image in a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "icons/foo.png" to
	// load the PNG image in the file "edu/ou/cs/hci/resources/icons/foo.png".
	public static ImageIcon		getImage(String filename)
	{
		return new ImageIcon(getResource(filename));
	}

	//**********************************************************************
	// Public Class Methods (Class Resources)
	//**********************************************************************

	// Gets the URL of a resource file located relative to this class file.
	// Resource files can be in subdirectories. If so, specify the filename
	// with a relative path. For example, use filename "docs/names.txt" to
	// get the URL of the file "edu/ou/cs/hci/resources/docs/names.txt".
	public static final URL	getResource(String filename)
	{
		return Resources.class.getResource(filename);
	}

	//**********************************************************************
	// Public Class Methods (Line Slurpers)
	//**********************************************************************

	// Slurps the lines from the specified URL into an array of strings.
	public static ArrayList<String>	getLines(URL url)
	{
		ArrayList<String>		v = new ArrayList<String>();

		try
		{
			InputStream		is = url.openStream();
			InputStreamReader	isr = new InputStreamReader(is);
			BufferedReader		r = new BufferedReader(isr);

			appendLines(r, v);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return v;
	}

	// Appends lines from the specified reader to an array of strings.
	public static void	appendLines(Reader r, ArrayList<String> v)
		throws IOException
	{
		LineNumberReader	lr = new LineNumberReader(r);
		String				line;

		do
		{
			line = lr.readLine();

			if (line != null)
				v.add(line);
		}
		while (line != null);
	}
}

//******************************************************************************
