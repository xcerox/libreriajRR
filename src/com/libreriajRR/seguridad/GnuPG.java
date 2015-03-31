
package com.libreriajRR.seguridad;

/* License: GPL
 * Author: John Anderson
 * Description: A small class to encrypt and decrypt text using GnuPG.
 */

import java.io.*;

public class GnuPG
{
	private Process p;
	
	private String gpg_result;
	private String gpg_err;
	
	GnuPG()
	{
	}

	public void encrypt(String str, String rcpt)
	{
		System.out.print("Encrypting... ");
		
		try
		{
			p = Runtime.getRuntime().exec("gpg --armor --batch --encrypt -r " +
rcpt);
		}
		catch(IOException io)
		{
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN",
p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR",
p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();
		
		BufferedWriter out = new BufferedWriter(new
OutputStreamWriter(p.getOutputStream()));
		
		try
		{
			out.write(str);
			out.close();
		}
		catch(IOException io)
		{
		}
		
		try
		{
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		}
		catch(InterruptedException i)
		{
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();

		System.out.println("Done.");
	}

	public void decrypt(String str, String passphrase)
	{
		File f = null;
		
		try
		{
			f = File.createTempFile("gpg-decrypt", null);
			FileWriter fw = new FileWriter(f);
			fw.write(str);
			fw.flush();
		}
		catch(IOException io)
		{
		}
	
		System.out.print("Decrypting from: " + f.getAbsolutePath());
		
		try
		{
			p = Runtime.getRuntime().exec("gpg --passphrase-fd 0 --batch
--decrypt "
							+ f.getAbsolutePath());
		}
		catch(IOException io)
		{
			System.out.println("Error creating process.");
		}

		ProcessStreamReader psr_stdout = new ProcessStreamReader("STDIN",
p.getInputStream());
		ProcessStreamReader psr_stderr = new ProcessStreamReader("STDERR",
p.getErrorStream());

		psr_stdout.start();
		psr_stderr.start();
		
		BufferedWriter out = new BufferedWriter(new
OutputStreamWriter(p.getOutputStream()));
		
		try
		{	
			out.write(passphrase);
			out.close();
		}
		catch(IOException io)
		{
		}
	
		try
		{
			p.waitFor();

			psr_stdout.join();
			psr_stderr.join();
		}
		catch(InterruptedException i)
		{
		}

		gpg_result = psr_stdout.getString();
		gpg_err = psr_stdout.getString();
		
		System.out.println("Done.");
	}

	public String getResult()
	{
		return gpg_result;
	}

	public String getError()
	{
		return gpg_err;
	}
}


class ProcessStreamReader
extends Thread
{
	String name;
	StringBuffer stream;
	InputStreamReader in;

	final static int BUFFER_SIZE = 256;
	
	ProcessStreamReader(String name, InputStream in)
	{
		super();
		
		this.name = name;
		this.in = new InputStreamReader(in);

		this.stream = new StringBuffer();
	}

	public void run()
	{
		try
		{	
			int read;
			char[] c = new char[BUFFER_SIZE];
			
			while((read = in.read(c, 0, BUFFER_SIZE - 1)) > 0)
			{
				stream.append(c, 0, read);
				if(read < BUFFER_SIZE - 1) break;
			}
		}
		catch(IOException io) {}
	}

	String getString()
	{
		return stream.toString();
	}
}
