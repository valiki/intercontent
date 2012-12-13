package net.sunnycore.intercontent.paging;

/**
 * An interface with basic paging methods
 * 
 * @author vshukaila
 * 
 */
public interface IPageable {
    /**
     * goes to the next page
     */
    public void next() throws Throwable;

    /**
     * goes to the last page
     */
    public void last() throws Throwable;

    /**
     * goes to the previous page
     */
    public void previous() throws Throwable;

    /**
     * goes to the first page
     */
    public void first() throws Throwable;

    /**
     * show whether the current page is first
     * 
     * @return
     */
    public boolean isFirst();

    /**
     * show whether the current page is last
     * 
     * @return
     */
    public boolean isLast();

    /**
     * Show whether the current page has next
     * 
     * @return
     */
    public boolean isNext();

    /**
     * show whether the current page has previous
     * 
     * @return
     */
    public boolean isPrevious();
}
