/*
 * @(#)DeleteOnExitHook.java	1.4 06/06/20
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package java.io;

import java.util.*;
import java.io.File;

/**
 * This class holds a set of filenames to be deleted on VM exit through a shutdown hook.
 * A set is used both to prevent double-insertion of the same file as well as offer 
 * quick removal.
 */

class DeleteOnExitHook {
    private static DeleteOnExitHook instance = null;

    private static LinkedHashSet<String> files = new LinkedHashSet<String>();

    static DeleteOnExitHook hook() {
	if (instance == null)
	    instance = new DeleteOnExitHook();

	return instance;
    }

    private DeleteOnExitHook() {}

    static void add(String file) {
	synchronized(files) {
	    if(files == null)
		throw new IllegalStateException("Shutdown in progress");

	    files.add(file);
	}
    }

    void run() {
	LinkedHashSet<String> theFiles;

	synchronized (files) {
	    theFiles = files;
	    files = null;
	}

	ArrayList<String> toBeDeleted = new ArrayList<String>(theFiles);

	// reverse the list to maintain previous jdk deletion order.
	// Last in first deleted.
	Collections.reverse(toBeDeleted);
	for (String filename : toBeDeleted) {
	    (new File(filename)).delete();
	}
    }
}

