package us.codecraft.webmagic.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author code4crafter@gmail.com
 * @since 0.4.0
 */
public class ResultItemsCollectorPipeline implements CollectorPipeline<ResultItems> {

    private List<ResultItems> collector = new ArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        synchronized(this) {
            collector.add(resultItems);
        }
    }

    @Override
    public List<ResultItems> getCollected() {
        return collector;
    }
}
