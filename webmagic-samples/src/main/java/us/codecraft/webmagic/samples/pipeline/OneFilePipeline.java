package us.codecraft.webmagic.samples.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author code4crafer@gmail.com
 */
public class OneFilePipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private PrintWriter printWriter;

    public OneFilePipeline() throws IOException {
        this("/data/webmagic/");
    }

    public OneFilePipeline(String path) throws IOException {
        setPath(path);
        printWriter = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(path))));
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        synchronized(this) {
            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    printWriter.println(entry.getKey() + ":");
                    for (Object o : value) {
                        printWriter.println(o);
                    }
                } else {
                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
                }
            }
            printWriter.flush();
        }
    }
}
