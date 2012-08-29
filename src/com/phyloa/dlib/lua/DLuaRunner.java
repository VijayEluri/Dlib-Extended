package com.phyloa.dlib.lua;

import java.util.HashMap;
import java.util.LinkedList;

import javax.script.ScriptException;

public class DLuaRunner
{
	public boolean debug = false;
	public LinkedList<String> queue = new LinkedList<String>();
	public HashMap<String, DLua> scripts = new HashMap<String, DLua>();
	
	public DLuaRunner()
	{
		new Thread( new Runnable() {
			public void run() 
			{
				while( true )
				{
					try {
						Thread.sleep( 15 );
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if( queue.size() > 0 )
					{
						try {
							if( debug )
							{
								System.out.println( "DLuaRunner: run" + queue.peek() );
							}
							scripts.get( queue.removeFirst() ).run();
						} catch (ScriptException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} 
		} ).start();
	}
	
	public void add( String name, DLua script )
	{
		scripts.put( name, script );
	}
	
	public void add( String name, String script )
	{
		add( name, new DLua( script ) );
	}
	
	public void run( String name )
	{
		queue.addLast( name );
	}
	
	public DLua get( String name )
	{
		return scripts.get( name );
	}
}
