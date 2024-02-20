package parcial.sp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Compute {
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        String query ="";
        String method = "";
        String path = "";
        String[] decom = {};
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4200");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstline = true;
            while((inputLine=in.readLine())!=null){
                System.out.println(inputLine);
                if(firstline){
                     decom = inputLine.split(" ");
                     method = decom[0];
                     String[] via = getPathAndQuery(decom[1]);
                     System.out.println(Arrays.toString(via));
                     if(via.length >1){
                        query = via[1];
                     }
                     path = via[0];
                     System.out.println(query +"----------" + path);
                     firstline=false;
                }
                if(!in.ready())break;
            }
            outputLine= "";
            System.out.println(Arrays.toString(decom));
            if(method.equals("GET")){
                String res = Double.toString((Double)manageQuery(query));
                System.out.println(res);
                outputLine = html(res);
            }else{
                outputLine = (String) manageQuery(query);
                System.out.println(outputLine);
                outputLine ="HTTP/1.1 200 OK\r\n"+
                "<!DOCTYPE html>\r\n" + //
                "<html>\r\n" + //
                "    <head>\r\n" + //
                "        <title>Form Example</title>\r\n" + //
                "        <meta charset=\"UTF-8\">\r\n" + //
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                "    </head>\r\n" + //
                "    <body>\r\n" + //
                "        <h1>Form with GET</h1>\r\n" + //
                "        <form action=\"/hello\">\r\n" + //
                "            <label for=\"name\">Name:</label><br>\r\n" + //
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\r\n" + //
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\r\n" + //
                "        </form> \r\n" + //
                "        <div id=\"getrespmsg\"></div>\r\n" + //
                "\r\n" +"xd";
                
            }
            
            out.println(outputLine);
            out.close();
            in.close();

        }
        
        serverSocket.close();
    }

    /**
     * Handles and processes all the query info
     * @param query
     * @return 
     */
    private static Object manageQuery(String query) {
        String name = query.split("\\(")[0];
        Double data = Double.parseDouble(query.split("\\(")[1].split("\\)")[0]);
        System.out.println(data + "<-----------");
        Object res = null;
        Class xd = Math.class;
        Method a;
        try {
            a = xd.getMethod(name, Double.TYPE);
            res = a.invoke(null, data);
            System.out.println(a.getName());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return res;
    }

    /**
     * returns the path and query
     * @param inv
     * @return
     */
    public static String[] getPathAndQuery(String inv){
        String[] res1 = inv.split("\\?");
        if(res1.length >1){
            String[] con = res1[1].split("=");
            String[] res = {res1[0], con[1]};
            res1 = res;
        }
        return res1;
    }

    /**
     * gets the html page
     * @param res
     * @return
     */
    public static String html(String res){
        String xd =
                "HTTP/1.1 200 OK\r\n"+ //
                "<!DOCTYPE html>\r\n" + //
                "<html>\r\n" + //
                "    <head>\r\n" + //
                "        <title>Form Example</title>\r\n" + //
                "        <meta charset=\"UTF-8\">\r\n" + //
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                "    </head>\r\n" + //
                "    <body>\r\n" + //
                "        <h1>Form with GET</h1>\r\n" + //
                "        <form action=\"/hello\">\r\n" + //
                "            <label for=\"name\">Name:</label><br>\r\n" + //
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\r\n" + //
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\r\n" + //
                "        </form> \r\n" + //
                "        <div id=\"getrespmsg\"></div>\r\n" + //
                "\r\n" + //
                "        <script>\r\n" + //
                "            function loadGetMsg() {\r\n" + //
                "                let nameVar = document.getElementById(\"name\").value;\r\n" + //
                "                const xhttp = new XMLHttpRequest();\r\n" + //
                "                xhttp.onload = function() {\r\n" + //
                "                    document.getElementById(\"getrespmsg\").innerHTML =\r\n" + //
                "                    this.responseText;\r\n" + //
                "                }\r\n" + //
                "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\r\n" + //
                "                xhttp.send();\r\n" + //
                "            }\r\n" + //
                "        </script>\r\n" + //
                "\r\n" + //
                "        <h1>Ingresa tu comando</h1>\r\n" + //
                "        <form action=\"/computar\">\r\n" + //
                "            <label for=\"postname\">Name:</label><br>\r\n" + //
                "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\r\n" + //
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\r\n" + //
                "        </form>\r\n" + //
                "        \r\n" + //
                "        <div id=\"postrespmsg\"></div>\r\n" + //
                "        \r\n" + //
                "        <script>\r\n" + //
                "            function loadPostMsg(name){\r\n" + //
                "                let url = \"/computar?comando=\" + name.value;\r\n" + //
                "\r\n" + //
                "                fetch (url, {method: 'POST'})\r\n" + //
                "                    .then(x => x.text())\r\n" + //
                "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\r\n" + //
                "            }\r\n" + //
                "        </script>\r\n" + //
                "    </body>\r\n" + //
                "</html>"+ res;

        return xd;
    }
}
