//******************************************************************************
// Copyright (C) 2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  6 19:40:35 2018 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20180206 [weaver]:	Original file (for CS course homeworks).
// 20180414 [weaver]:	Added openURLInSystemBrowser() method.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.hci.resources;

//import java.lang.*;
import java.io.*;
import java.lang.reflect.Method;
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

	//**********************************************************************
	// Public Class Methods (URL Handlers)
	//**********************************************************************

	// Attempts to open the specified URL in the user's web browser. This
	// method uses reflection and special knowledge of the Java implementation
	// on each OS to make platform-specific system calls. Not very reliable.
	public static void	openURLInSystemBrowser(URL url)
	{
		String	surl;

		if (url.getProtocol().equals("file"))
			surl = ("file:///" + url.toString().substring(6));
		else
			surl = url.toString();

		// Following code is from http://www.centerkey.com/java/browser/
		String	osName = System.getProperty("os.name");

		try
		{
			if (osName.startsWith("Mac OS"))
			{
				Class	fileMgr = Class.forName("com.apple.eio.FileManager");
				Method	openURL = fileMgr.getDeclaredMethod("openURL", new Class[] {String.class});

				openURL.invoke(null, new Object[] {surl});
			}
			else if (osName.startsWith("Windows"))
			{
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + surl);
			}
			else	// Assume Unix or Linux
			{
				String		browser = null;
				String[]	browsers = {"firefox", "opera", "konqueror",
										"epiphany", "mozilla", "netscape"};

				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0)
						browser = browsers[count];

				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					Runtime.getRuntime().exec(new String[] {browser, surl});
			}
		}
		catch (Exception ex)
		{
			// Ignore failures
			//JOptionPane.showMessageDialog(null, "Error attempting to launch web browser:\n" + ex.getLocalizedMessage());
		}
	}
}

//******************************************************************************
