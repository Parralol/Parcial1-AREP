package parcial.sp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class HttpServer {
    
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://localhost:35000/computar?";
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        String query ="";
        String path = "";
        String[] decom = {};
        try {
            serverSocket = new ServerSocket(4200);
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
                System.out.print(e.getMessage());
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

            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT+"computar="+query);
            
            //The following invocation perform the connection implicitly before getting the code
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                
                StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                if(!in.ready())break;
            }
            in.close();
            outputLine = response.toString();
            System.out.println(outputLine);
            outputLine=html(outputLine);
            
            out.println(outputLine);

        }
        
        }
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
                "        <form action=\"/computar\">\r\n" + //
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
                "                xhttp.open(\"GET\", \"/computar?comando=\"+nameVar);\r\n" + //
                "                xhttp.send();\r\n" + //
                "            }\r\n" + //
                "        </script>\r\n" + //
                "\r\n" + //
                "        h1>Form with GET</h1>\r\n" + //
                "        <form action=\"/computar\">\r\n" + //
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
                "                xhttp.open(\"GET\", \"/computar?comando=\"+nameVar);\r\n" + //
                "                xhttp.send();\r\n" + //
                "            }\r\n" + //
                "        </script>\r\n" + //
                "    </body>\r\n" + //
                "</html>"+ res;

        return xd;
    }
    public String response(){

       
        return "";
    }
        
}

