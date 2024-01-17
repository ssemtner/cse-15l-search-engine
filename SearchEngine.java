import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;

class Handler implements URLHandler {
    private List<String> strings;

    public Handler() {
        strings = new ArrayList<>();
    }

    public String handleRequest(URI url) {
        String path = url.getPath();
        String query = url.getQuery();

        String param = null;
        if (query != null) {
            try {
                param = query.split("=")[1];
            } catch (ArrayIndexOutOfBoundsException e) {}
        }

        if (path.equals("/")) {
            String result = new String();

            for (String str : strings) {
                result += str + "\n";
            }
            
            return result;
        } else if (path.equals("/add")) {
            if (param == null) {
                return "Invalid string in query\n";
            }
            strings.add(param);
            return String.format("Added %s to strings\n", param);
        } else if (path.equals("/search")) {
            if (param == null) {
                return "Invalid string in query\n";
            }
            String result = new String();

            for (String str : strings) {
                if (str.contains(param)) {
                    result += str + "\n";
                }
            }

            return result;
        }

        return "404 Not Found\n";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
