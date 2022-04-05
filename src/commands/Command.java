package commands;

import exeptions.NoValidArgumentException;

public interface Command<T> {
    T execute(String[] argument);
    String getName();
    String getDescription();
    String getUsage();
    boolean validateArgument(String[] arg) throws NoValidArgumentException;

}
