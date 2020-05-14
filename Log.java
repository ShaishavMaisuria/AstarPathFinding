 
public final class Log
{
	private static final boolean DEBUG_ON = true;
	
	public static void debug(String message)
	{
		if (DEBUG_ON)
		{
			System.out.printf("[DEBUG] %s\n",message);
		}
	}

}
