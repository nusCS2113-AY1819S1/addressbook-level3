package seedu.addressbook.communications;

/*This class enables socket communication over the internet with another addressbook client, through a server*/

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClientSocket {
    public static void main(String args[])
    {
        OutputStream oos = null;
        Socket client = null;
        Scanner scanner = null;
        InputStream is = null;
        try {
            client = new Socket(InetAddress.getByName("localhost"), 2200);
            oos = client.getOutputStream();
            scanner = new Scanner(System.in);
            is =   client.getInputStream();
            while(true)
            {
                if(scanner.hasNext())
                {
                    oos.write(scanner.nextLine().getBytes());
                    oos.flush();
                }
                int read = 0;
                byte b[] = new byte[1024];
                if((read = is.read(b)) > 0)
                {
                    System.out.println(new String(b));
                }
            }
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if(oos != null)
            {
                try
                {
                    oos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(client!=null)
            {
                try {
                    client.close();
                }
                catch(IOException e)
                {}
            }
            if(scanner !=null)
            {
                scanner.close();
            }
        }
    }
}
