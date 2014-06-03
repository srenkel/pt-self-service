package com.capgemini.pt.core.data;

import java.io.File;

public interface IFileManager {

	public String storeFile(File file);

	public String symlinkFileToModule(File file);
}
