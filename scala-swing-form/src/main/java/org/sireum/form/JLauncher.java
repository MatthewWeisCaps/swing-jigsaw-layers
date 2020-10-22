package org.sireum.form;

public class JLauncher {

    public static void main(String[] args) {
        if (args.length > 0) {
            new JForm(args[0]);
        } else {
            new JForm("Untitled. (No name passed in args)");
        }
    }

}
