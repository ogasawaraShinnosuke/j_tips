package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TranMastSearch {

    // ID、Hash
    private static String _input_path = "dammy.txt";
    // Hash（元）
    private static String _check_path = "dammy.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        set_path();
        try (
                BufferedReader in = new BufferedReader(new FileReader(
                        new File(_input_path)));
                BufferedReader check = new BufferedReader(new FileReader(
                        new File(_check_path))))
        {
            Map<String, Long> map = addMap(in);
            List<String> list = addList(check);
            if (map == null || map.isEmpty() || list == null || list.isEmpty())
                throw new FileNotFoundException();
            for (String str : list) {
                Long id = map.get(str);
                if (id == null) {
                    System.out.println(String.format("ERROR HASH [%s]", str));
                }
            }
        } finally {}
    }

    private static List<String> addList(BufferedReader check) throws IOException {
        List<String> list = new ArrayList<String>();
        String line;
        while ((line = check.readLine()) != null) {
            if (StringUtils.isBlank(line))
                continue;
            list.add(line);
        }
        return list;
    }

    private static Map<String, Long> addMap(BufferedReader in) throws IOException {
        Map<String, Long> map = new HashMap<String, Long>();
        String line;
        while ((line = in.readLine()) != null) {
            if (StringUtils.isBlank(line))
                continue;
            String[] lines = line.split("\t");
            if (lines == null || lines.length != 2)
                continue;
            map.put(lines[1], Long.parseLong(lines[0]));
        }
        return map;
    }

    private static void set_path() {
        _input_path = "../data/input";
        _check_path = "../data/check";
    }

}
