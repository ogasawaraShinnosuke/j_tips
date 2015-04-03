package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ShiftUrl {
    private void parse() throws
            UnsupportedEncodingException,
            FileNotFoundException,
            IOException
    {
        try (
                BufferedReader in = new BufferedReader(new FileReader(
                        new File(_input_path)));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                        new File(_output_path)), "UTF-8"))
                )
        { output(in, out); } finally {}
    }

    private void output(BufferedReader in, BufferedWriter out) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            if (StringUtils.isBlank(line)) continue;
            line = URL_PATTERN.matcher(line).replaceFirst("$1$2");
            if (JPN_PATTERN.matcher(line).find()) continue;
            line = line.trim();
            String[] unCheck = line.split("\t");
            if (unCheck.length != 2) continue;
            if (!unCheck[1].matches(URL_REGEX)) continue;
            out.write(line);
            out.newLine();
        }
        out.flush();
    }

    private static String _input_path = "/tmp/input";
    private static String _output_path = "tmp/output";
    private static final String URL_REGEX = "http(s)?://[a-zA-Z0-9-=_.?/%#]+";
    private static final Pattern URL_PATTERN = Pattern.compile(String.format("(\t.*)(%s).*$", URL_REGEX));
    private static final Pattern JPN_PATTERN = Pattern.compile("[ぁ-んァ-ヶ一-龠々ー]");

    public static void main(String[] args) throws IOException {
        new ShiftUrl(args).parse();
    }

    private ShiftUrl(String[] args) { init(args); }

    private static void init(String[] args) {
        if (args == null || args.length == 0)
            return;
        _input_path = args[0];
        _output_path = args[1];
    }
}
