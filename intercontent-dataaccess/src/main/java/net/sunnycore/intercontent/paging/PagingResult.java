package net.sunnycore.intercontent.paging;

import java.io.Serializable;
import java.util.List;

import net.sunnycore.intercontent.domain.Persistent;

/**
 * A DTO object that holds the result of the paging <br/>
 * TODO: maybe move next first and so on methods to pagingConfig
 * 
 * @author vshukaila
 */
public class PagingResult<T extends Persistent> implements Serializable,IPageable {
    /**
     * 
     */
    private static final long serialVersionUID = -666428318660913594L;
    /**
     * A list containing paging result
     */
    private List<T> result;
    /**
     * A list containing the load config
     */
    private PagingConfig<T> loadConfig;
    /**
     * A number containing full number of the objects in database
     */
    private long fullSize;

    /**
     * return number of elements on the current page
     * 
     * @return
     */
    public int getOnPage() {
        return (result != null) ? result.size() : 0;
    }

    public int getNumberOfPages() {
        return (int) Math.ceil((double) fullSize / (double) loadConfig.getMaxResult());
    }

    public List<T> getResult() {
        return result;
    }

    public PagingConfig<T> getLoadConfig() {
        return loadConfig;
    }

    public long getFullSize() {
        return fullSize;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public void setLoadConfig(PagingConfig<T> loadConfig) {
        this.loadConfig = loadConfig;
    }

    public void setFullSize(long fullSize) {
        this.fullSize = fullSize;
    }

    public void first() throws Throwable {
        loadConfig.setFirstResult(0);
        loadConfig.execLoadCommands();
    }

    public void last() throws Throwable {
        int firstResult = getLastPageFirstResult();
        loadConfig.setFirstResult(firstResult);
        loadConfig.execLoadCommands();
    }

    /**
     * return the number of the first result that should be for the last page
     * 
     * @return
     */
    private int getLastPageFirstResult() {
        int maxResult = loadConfig.getMaxResult();
        int firstResult = 0;
        if (fullSize > maxResult) {
            firstResult = (int)(fullSize - fullSize % maxResult);
        }
        return firstResult;
    }

    public void next() throws Throwable {
        if (!isLast()) {
            loadConfig.setFirstResult(loadConfig.getFirstResult() + loadConfig.getMaxResult());
        }
        loadConfig.execLoadCommands();
    }

    public void previous() throws Throwable {
        if (!isFirst()) {
            loadConfig.setFirstResult(loadConfig.getFirstResult() - loadConfig.getMaxResult());
        }
        loadConfig.execLoadCommands();
    }

    public boolean isNext() {
        long firstResult = loadConfig.getFirstResult();
        if (firstResult < getLastPageFirstResult()) {
            return true;
        }
        return false;
    }

    public boolean isPrevious() {
        long firstResult = loadConfig.getFirstResult();
        if (firstResult > 0) {
            return true;
        }
        return false;
    }

    public boolean isFirst() {
        long firstResult = loadConfig.getFirstResult();
        if (firstResult == 0) {
            return true;
        }
        return false;
    }

    public boolean isLast() {
        long lastPageFirstResult = getLastPageFirstResult();
        if (loadConfig.getFirstResult() == lastPageFirstResult) {
            return true;
        }
        return false;
    }

    public boolean isAfterLast() {
        if (loadConfig.getFirstResult() <= fullSize) {
            return false;
        }
        return true;
    }
}
