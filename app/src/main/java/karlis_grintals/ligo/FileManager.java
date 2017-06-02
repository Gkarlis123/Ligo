package karlis_grintals.ligo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by karlis on 01/06/2017.
 */

public class FileManager extends AppCompatActivity {

    AppDatabaseAdapter helper;

    public FileManager (Context context) {

        AppDatabaseAdapter helper = new AppDatabaseAdapter(context);

        try {
            InputStream  file = context.getResources().openRawResource(R.raw.section_data);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(file, "UTF-8"));

            StringBuffer buffer = new StringBuffer();
            String read;
            String section = "";
            String taskSection = "uzdevumi";
            Map<String, String> sections = new HashMap<String, String>();
            List<String> tasks = new ArrayList<String>();
            Boolean taskSectionTrigger = false;

            while ((read = in.readLine()) != null) {

                if (read.contains("<")) {

                    if (taskSection.equals(read.substring(1,read.length() - 1))) {
                        taskSectionTrigger = true;
                        continue;
                    }

                    if (section.equals(read.substring(1,read.length() - 1))) {
                        sections.put(section, buffer.toString());
                        continue;
                    } else {
                        section = read.substring(1,read.length() - 1);
                        buffer.delete(0, buffer.length());
                    }
                    continue;
                }

                if (taskSectionTrigger) {
                    tasks.add(read.toString());
                } else {
                    buffer.append(read +"\n");
                }
            }

            in.close();

            for (Map.Entry<String, String> entry : sections.entrySet()) {
                helper.insertData(entry.getKey(), entry.getValue());
            }

            for (String task : tasks) {
                helper.insertTaskData(task);
            }
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
