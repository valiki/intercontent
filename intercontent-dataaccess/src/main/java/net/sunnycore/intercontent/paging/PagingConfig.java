package net.sunnycore.intercontent.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sunnycore.intercontent.domain.Persistent;

/**
 * A DTO class used for paging in dao
 * 
 * @author vshukaila
 * 
 */
public class PagingConfig<T extends Persistent> implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public enum OrderDirection {
        ASC, DESC, NONE
    }

    /**
     * the index of the first result on the page
     */
    private int firstResult = 0;
    /**
     * the number of results on page<br/>
     * default value is 20
     */
    private int maxResult = 20;
    /**
     * the order direction of the result<br/>
     * default value is NONE
     */
    private OrderDirection orderDirection = OrderDirection.NONE;
    /**
     * the property that will be used for ordering<br/>
     * default is ID
     */
    private String orderProperty = "id";
    /**
     * an object that will be used for filtering results
     */
    private T filter;

    private PagingResult<T> pageResult;
    
    private List<ICommand> loadCommands = new ArrayList<ICommand>();

    protected void execLoadCommands() throws Throwable {
        for (ICommand command : loadCommands) {
            command.execute(this);
        }
    }

    public long getPagePosition() {
        return (firstResult / getMaxResult()) + 1;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public void setOrderDirection(OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
    }

    public void setOrderDirectionString(String orderDirection){
    	try {
			this.orderDirection = OrderDirection.valueOf(orderDirection);
		} catch (Exception e) {
			//do nothing
		}
    }
    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }
    
    public void addLoadCommand(ICommand command){
        this.loadCommands.add(command);
    }
    
    public boolean removeLoadCommand(ICommand command){
        return this.loadCommands.remove(command);
    }

    public PagingResult<T> getPageResult() {
        return pageResult;
    }

    public void setPageResult(PagingResult<T> pageResult) {
        this.pageResult = pageResult;
    }

	public List<ICommand> getLoadCommands() {
		return loadCommands;
	}

	public void setLoadCommands(List<ICommand> loadCommands) {
		this.loadCommands = loadCommands;
	}
}
