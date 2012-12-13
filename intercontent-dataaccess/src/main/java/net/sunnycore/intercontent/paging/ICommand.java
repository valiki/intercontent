package net.sunnycore.intercontent.paging;

/**
 * Just plain Command
 * 
 * @author vshukaila
 * 
 */
public interface ICommand {
    public void execute(Object o) throws Throwable;
}
